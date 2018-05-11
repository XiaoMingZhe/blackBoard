package com.blackboard.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blackboard.dao.BlackboardDao;
import com.blackboard.dao.CommentDao;
import com.blackboard.dao.ImageDao;
import com.blackboard.dao.LikeDao;
import com.blackboard.dao.SystemMessageDao;
import com.blackboard.dto.BlackboardDto;
import com.blackboard.dto.CommentDto;
import com.blackboard.dto.Remind;
import com.blackboard.entity.Blackboard;
import com.blackboard.entity.Like;
import com.blackboard.service.BlackboardService;
import com.blackboard.service.CommentService;
import com.blackboard.service.ImageService;
import com.blackboard.utils.GainUuid;
import com.blackboard.utils.Hex16;
import com.blackboard.utils.JsonResult;
import com.blackboard.utils.MsgPushThread;
import com.blackboard.utils.PropertiesUtils;
import com.blackboard.utils.RelativeDateFormat;
import com.blackboard.utils.WebServiceThread;
import com.blackboard.utils.WebServicesClient;
import com.vdurmont.emoji.EmojiParser;

@Service
@Transactional
public class BlackboardServiceImpl implements BlackboardService {

	private static final Integer PAGE_SIZE = 10;
	private static final String KEY = PropertiesUtils.getProperties("KEY");
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BlackboardDao blackboardDao;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private LikeDao likeDao;
	@Autowired
	private SystemMessageDao systemMessageDao;
	@Autowired
	private ImageService imageService;

	/**
	 * 创建黑板报
	 * 
	 * @param blackboard
	 *            黑板报对象
	 * @return blackboardId 黑板报ID
	 */
	@Override
	public void createBlackboard(Blackboard blackboard,List<Map<String,Object>> visibleRange,List<String> imageIdList) {
		blackboard.setBlackboardId(GainUuid.getUUID());
		blackboard.setCreateTime(new Date());
		//emoji表情切换
		blackboard.setTitle(EmojiParser.parseToAliases(blackboard.getTitle()));
		blackboard.setContent(EmojiParser.parseToAliases(blackboard.getContent()));
		blackboardDao.createBlackboard(blackboard);
		
		if(visibleRange != null && visibleRange.size()>0){
			Map<String,Object> map = new HashMap<>();
			List<String> list = new ArrayList<>();
			for(Map<String,Object> m : visibleRange){
				list.add((String)m.get("mobile"));
			}
			map.put("list", list);
			map.put("blackBoardId", blackboard.getBlackboardId());
			blackboardDao.saveVisibleRange(map);
			
			Map<String, Object> MsgPush = new HashMap<>();
			MsgPush.put("Title", blackboard.getTitle());
			MsgPush.put("Connent", "来自："+blackboard.getCreateBy());
			MsgPush.put("blackboardId", blackboard.getBlackboardId());
			MsgPush.put("mobile", list);
			Thread thread = new MsgPushThread(MsgPush);
			thread.start();
		}
		
		// 判断有没有上传图片
		if(imageIdList != null && imageIdList.size()>0){
			Map<String,Object> imageMap = new HashMap<>();
			imageMap.put("blackBoardId", blackboard.getBlackboardId());
			imageMap.put("imageIds", imageIdList);
			blackboardDao.updateImageBlackBoardID(imageMap);
		}
		
		//安全送审
		Map<String, Object> conntentMap = new HashMap<>();
		conntentMap.put("mobile", blackboard.getCreateMobile());
		conntentMap.put("msgid","blackboard"+ blackboard.getBlackboardId());
		conntentMap.put("content", blackboard.getContent());
		Thread conntentThread = new WebServiceThread(conntentMap);
		conntentThread.start();
		
		//安全送审
		Map<String,Object> TitleMap = new HashMap<>();
		TitleMap.put("mobile", blackboard.getCreateMobile());
		TitleMap.put("msgid","blackboard"+ blackboard.getBlackboardId());
		TitleMap.put("content", blackboard.getTitle());
		Thread titleThread = new WebServiceThread(TitleMap);
		titleThread.start();
	}

	/**
	 * 查询企业所有黑板报
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param pageNumber
	 *            页码（第几页）
	 * @return blackboardList 黑板报列表对象
	 */
	@Override
	public Map<String, Object> getAllBlackboard(String enterpriseId, Integer pageNumber,String mobile) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("enterpriseId", enterpriseId);
		map.put("mobile", mobile);
		map.put("isread", "noread");
		
		// 设置分页页数
		map.put("first", (pageNumber - 1) * PAGE_SIZE);
		map.put("end", PAGE_SIZE);

		List<BlackboardDto> list = blackboardDao.getAllBlackboard(map);
		dateChangeForList(list);
		
		//模糊电话号码
		for(BlackboardDto b:list){
			b.setTitle(EmojiParser.parseToUnicode(b.getTitle()));
			b.setCreateMobile(Hex16.Encode(Hex16.Encode(b.getCreateMobile()+KEY)));
		}
		
		// 获取总条数，计算总页数
		Long count = blackboardDao.getALLBlackboardCount(map);
		long page = count / PAGE_SIZE;
		if (count % PAGE_SIZE != 0) {
			page++;
		}
		
		List<Map<String, Object>> RemindList = blackboardDao.selectRemind(map);
		Integer remindCount = RemindList.size();
		String moblie = "";
		if(remindCount>0){
			moblie = (String)RemindList.get(0).get("moblie");
		}
				
		Map<String, Object> backMap = new HashMap<>();
		backMap.put("list", list);
		backMap.put("page", page);
		backMap.put("remindCount", remindCount);
		backMap.put("moblie", moblie);
		return backMap;
	}

	/**
	 * 展示单条黑板报详情
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param blackboardId
	 *            黑板报ID
	 * @param currentUser
	 *            当前用户
	 * @return result 当前黑板报所有详情（评论、状态）
	 */
	@Override
	public JsonResult getBlackboardById(String blackboardId, String enterpriseId, String mobile) {
		// 设置参数
		Map<String, Object> map = new HashMap<>();
		map.put("enterpriseId", enterpriseId);
		map.put("blackboardId", blackboardId);
		map.put("mobile",mobile);

		// 增加浏览数
		blackboardDao.updatePageViews(blackboardId);

		// 获取单条黑板报信息
		BlackboardDto blackboarddto = blackboardDao.getBlackboardById(map);
		logger.info("==============获取单条黑板报信息:ID为" + blackboardId);
		dateChange(blackboarddto);
		//模糊电话号码
		blackboarddto.setCreateMobile(Hex16.Encode(Hex16.Encode(blackboarddto.getCreateMobile()+KEY)));
		blackboarddto.setTitle(EmojiParser.parseToUnicode(blackboarddto.getTitle()));
		blackboarddto.setContent(EmojiParser.parseToUnicode(blackboarddto.getContent()));
		
		//查看有没有点赞过
		Like like = new Like();
		like.setBeLikedId(blackboardId);
		like.setLikeUseid(mobile);
		Map<String, Object> statusMap = likeDao.findStatus(like);
		if(statusMap == null){
			blackboarddto.setIsLike(0);
		}else{
			Integer status = (Integer)statusMap.get("status");
			System.out.println(status);
			blackboarddto.setIsLike(status==0?1:0);
		}
		
		// 获取评论
		List<CommentDto> commentDto = commentService.getAllComments(blackboardId,mobile);
		logger.info("==============获取单条黑板报评论:ID为" + blackboardId);

		// 判断是不是本人，评论能不能删除
		List<Map<String, Object>> comments = new ArrayList<>();
		for (CommentDto c : commentDto) {
			Map<String, Object> commentMap = new HashMap<>();
			c.setCommentContent(EmojiParser.parseToUnicode(c.getCommentContent()));
			//模糊手机号
			if (c.getCommenterId().equals(Hex16.Encode(Hex16.Encode(mobile+KEY)))) {
				commentMap.put("canDelete", 1);
			} else {
				commentMap.put("canDelete", 0);
			}
			commentMap.put("comment", c);
			comments.add(commentMap);
		}
		
		List<String> visibleRangeList = blackboardDao.selectVisibleRangeList(blackboardId);
		Integer permission = 0;
		if(visibleRangeList!=null && visibleRangeList.size()>0){
			permission = 1;
		}
		logger.info("=============黑板报详情:" + blackboarddto);
		return JsonResult.ok().put("blackboard", blackboarddto).put("comments", comments).put("permission", permission);
	}

	/**
	 * 查询企业个人所有黑板报
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param createById
	 *            黑板报所属用户
	 * @param pageNumber
	 *            页码（第几页）
	 * @return List<Blackboard> 个人发布的所有黑板报记录
	 */
	@Override
	public Map<String, Object> getPersonalBlackboard(String enterpriseId, String createMobile, Integer pageNumber,Integer type) {
		Map<String, Object> map = new HashMap<>();
		map.put("enterpriseId", enterpriseId);
		
		// 封装分页信息
		map.put("createMobile", createMobile);
		map.put("first", (pageNumber - 1) * PAGE_SIZE);
		map.put("end", PAGE_SIZE);
		map.put("type", type);

		// 获取所有黑板报
		List<BlackboardDto> list = blackboardDao.getPersonalBlackboard(map);
		dateChangeForList(list);
		//模糊电话号码
		for(BlackboardDto b:list){
			b.setTitle(EmojiParser.parseToUnicode(b.getTitle()));
			b.setCreateMobile(Hex16.Encode(Hex16.Encode(b.getCreateMobile()+KEY)));
		}
		
		// 获取黑板报条数，计算分页总页数
		Long count = blackboardDao.getPersonalBlackboardCount(map);
		long page = 1;
		logger.info("==============黑板报条数:" + count);
		
		//获取系统消息未读条数
		Map<String, String> systemMap = new HashMap<>();
		systemMap.put("userId", createMobile);
		systemMap.put("enterpriseId", enterpriseId);
		Long SystemCount = systemMessageDao.slectSystemCount(systemMap);
		
		Map<String, Object> backMap = new HashMap<>();
		backMap.put("list", list);
		backMap.put("page", page);
		backMap.put("SystemCount", SystemCount);
		return backMap;
	}


	/**
	 * 查询他人所有黑板报
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param createById
	 *            黑板报所属用户
	 * @param pageNumber
	 *            页码（第几页）
	 * @return List<Blackboard> 个人发布的所有黑板报记录
	 */
	@Override
	public Map<String, Object> getOtherBlackboard(String enterpriseId, String createMobile, Integer pageNumber,String nowUser) {
		Map<String, Object> map = new HashMap<>();
		map.put("enterpriseId", enterpriseId);
		
		// 封装分页信息
		map.put("createMobile", createMobile);
		map.put("nowUser", nowUser);
		map.put("first", (pageNumber - 1) * PAGE_SIZE);
		map.put("end", PAGE_SIZE);
		map.put("type", 0);
		
		// 获取所有黑板报
		List<BlackboardDto> list = blackboardDao.getOtherBlackboard(map);
		dateChangeForList(list);
		
		//模糊电话号码
		for(BlackboardDto b:list){
			b.setTitle(EmojiParser.parseToUnicode(b.getTitle()));
			b.setCreateMobile(Hex16.Encode(Hex16.Encode(b.getCreateMobile()+KEY)));
		}
		
		// 获取黑板报条数，计算分页总页数
		Long count = blackboardDao.getPersonalBlackboardCount(map);
		long page = 1;
		logger.info("==============所有黑板报:" + list);
		logger.info("==============黑板报条数:" + count);
		Map<String, Object> backMap = new HashMap<>();
		backMap.put("list", list);
		backMap.put("page", page);
		return backMap;
	}
	
	/**
	 * 删除黑板报
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param blackboardId
	 *            黑板报ID
	 */
	@Override
	public void delete(String blackboardId, String enterpriseId,String serverPath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blackboardId", blackboardId);
		map.put("enterpriseId", enterpriseId);
		commentService.deleteComments(enterpriseId, blackboardId);
		blackboardDao.delete(map);
		blackboardDao.deleteVisibleRange(blackboardId);
		List<String> blackboardIdList = new ArrayList<>();
		blackboardIdList.add(blackboardId);
		imageService.deleteImageForBlackboard(blackboardIdList, serverPath);
	}

	/**
	 * 批量删除黑板报
	 */
	@Override
	public void deleteList(List<Map<String,Object>> maplist,String serverPath) {
		List<String> list = new ArrayList<>();
		for(Map<String,Object> m : maplist){
			list.add((String) m.get("blackboardId"));
		}
		blackboardDao.deleteList(list);
		blackboardDao.deleteVisibleRangeList(list);
		imageService.deleteImageForBlackboard(list, serverPath);
	}

	/**
	 * 修改黑板报
	 * 
	 * @param blackboard
	 *            修改的黑板报对象
	 */
	@Override
	public boolean updateBlackboard(Blackboard blackboard,List<Map<String,Object>> visibleRange) {
		blackboard.setUpdateTime(new Date());
		blackboard.setTitle(EmojiParser.parseToAliases(blackboard.getTitle()));
		blackboard.setContent(EmojiParser.parseToAliases(blackboard.getContent()));
		Boolean bFlag = blackboardDao.updateBlackboard(blackboard);
		blackboardDao.deleteVisibleRange(blackboard.getBlackboardId());
		
		if(visibleRange.size()>0){
			Map<String,Object> map = new HashMap<>();
			List<String> list = new ArrayList<>();
			for(Map<String,Object> m : visibleRange){
				list.add((String)m.get("mobile"));
			}
			map.put("list", list);
			map.put("blackBoardId", blackboard.getBlackboardId());
			blackboardDao.saveVisibleRange(map);
		}
		return bFlag;
	}

	/**
	 * 消息提醒
	 */
	@Override
	public List<Remind> selectRemind(String mobile, String enterpriseId) {
		Map<String, Object> map = new HashMap<>();
		map.put("mobile", mobile);
		map.put("enterpriseId", enterpriseId);
		String regx = "<img(.*?)>";

		List<Remind> returnList = new ArrayList<>();
		List<Map<String, Object>> list = blackboardDao.selectRemind(map);
		for (Map<String, Object> m : list) {
			Remind remind = new Remind();
			String title = EmojiParser.parseToUnicode((String) m.get("title"));
			String blackboardId = (String) m.get("blackboardId");
			String blackboardContent = (String)m.get("blackboardContent");
			String userName = (String)m.get("userName");
			String content = (String) m.get("content");
			if(content!=null){
				content = EmojiParser.parseToUnicode(content);
			}
			//模糊手机号
			String moblie = Hex16.Encode(Hex16.Encode((String) m.get("moblie")+KEY));
			Date creatTime = (Date) m.get("creatTime");
			String remindID = (String) m.get("remindID");
			Long type = (Long) m.get("type");
			Long likeType = (Long) m.get("likeType");
			String img = getString(blackboardContent, regx);
			if (img == null || img.length() <= 0) {
				remind.setTitle(title);
			} else {
				remind.setTitle(img);
			}
			remind.setUserName(userName);
			remind.setBlackboardId(blackboardId);
			remind.setContent(content);
			try {
				remind.setCreatTime(RelativeDateFormat.format(creatTime));
			} catch (Exception e) {
				e.printStackTrace();
			}
			remind.setMoblie(moblie);
			remind.setRemindID(remindID);
			remind.setType(type.intValue());
			remind.setLikeType(likeType.intValue());
			returnList.add(remind);
		}
		
		//修改已读状态
		commentDao.updateRead(mobile,enterpriseId);
		likeDao.updateRead(mobile);
		return returnList;
	}

	/**
	 * 获取查询的字符串 将匹配的字符串取出
	 */
	private String getString(String str, String regx) {
		String img = "";
		// 1.将正在表达式封装成对象Patten 类来实现
		Pattern pattern = Pattern.compile(regx);
		// 2.将字符串和正则表达式相关联
		Matcher matcher = pattern.matcher(str);
		// 3.String 对象中的matches 方法就是通过这个Matcher和pattern来实现的。
		System.out.println(matcher.matches());
		// 查找符合规则的子串
		while (matcher.find()) {
			// 获取 字符串
			img = matcher.group();
			break;
		}
		return img;
	}

	/**
	 * 时间转换(集合)
	 * 
	 * @param list
	 * @return
	 */
	public List<BlackboardDto> dateChangeForList(List<BlackboardDto> list) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (BlackboardDto b : list) {
			try {
				Date date = sdf.parse(b.getCreateTime());
				b.setCreateTime(RelativeDateFormat.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 时间转换单条
	 * 
	 * @param list
	 * @return
	 */
	public BlackboardDto dateChange(BlackboardDto b) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(b.getCreateTime());
			b.setCreateTime(RelativeDateFormat.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return b;
	}
}
