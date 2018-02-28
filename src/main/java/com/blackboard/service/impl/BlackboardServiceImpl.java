package com.blackboard.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blackboard.dao.BlackboardDao;
import com.blackboard.dao.CommentDao;
import com.blackboard.dto.BlackboardDto;
import com.blackboard.dto.CommentDto;
import com.blackboard.entity.Blackboard;
import com.blackboard.service.BlackboardService;
import com.blackboard.service.CommentService;
import com.blackboard.service.ImageService;
import com.blackboard.utils.GainUuid;
import com.blackboard.utils.JsonResult;
import com.blackboard.utils.RelativeDateFormat;

@Service
@Transactional
public class BlackboardServiceImpl implements BlackboardService {

	@Autowired
	private BlackboardDao blackboardDao;
	@Autowired
	private ImageService imageService;
	@Autowired
	private CommentService commentService;

	private static final Integer PAGE_SIZE = 10;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 创建黑板报
	 * 
	 * @param blackboard
	 *            黑板报对象
	 * @return blackboardId 黑板报ID
	 */
	@Override
	public void createBlackboard(Blackboard blackboard) {

		blackboard.setBlackboardId(GainUuid.getUUID());
		blackboard.setCreateTime(new Date());

		blackboardDao.createBlackboard(blackboard);

	}

	/**
	 * 创建黑板报
	 * 
	 * @param blackboard
	 *            黑板报对象
	 * @param images
	 *            图片对象
	 * @param serverPath
	 *            保存图片的服务器路劲
	 * @return blackboardId 黑板报ID
	 */
//	@Override
//	public String createBlackboard(Blackboard blackboard, MultipartFile[] images, String serverPath) {
//		// 设置黑板报ID
//		blackboard.setBlackboardId(GainUuid.getUUID());
//		// 设置黑板报日期
//		blackboard.setCreateTime(new Date());
//		// 创建黑板报
//		blackboardDao.createBlackboard(blackboard);
//
//		logger.info("===========创建黑板报service:" + blackboard);
//
//		return blackboard.getBlackboardId();
//	}

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
	public Map<String, Object> getAllBlackboard(String enterpriseId, Integer pageNumber) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("enterpriseId", enterpriseId);
		// 设置分页页数
		map.put("first", (pageNumber - 1) * PAGE_SIZE);
		map.put("end", PAGE_SIZE);

		List<BlackboardDto> list = blackboardDao.getAllBlackboard(map);
		// 获取总条数，计算总页数
		Long count = blackboardDao.getALLBlackboardCount(map);
		long page = count / PAGE_SIZE;
		if (count % PAGE_SIZE != 0) {
			page++;
		}
		
		Map<String, Object> backMap = new HashMap<>();
		backMap.put("list", list);
		backMap.put("page", page); 
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
	public JsonResult getBlackboardById(String blackboardId, String enterpriseId,String mobile) {
		// 设置参数
		Map<String, Object> map = new HashMap<>();
		map.put("enterpriseId", enterpriseId);
		map.put("blackboardId", blackboardId);

		//增加浏览数
		blackboardDao.updatePageViews(blackboardId);
		
		// 获取单条黑板报信息
		BlackboardDto blackboarddto = blackboardDao.getBlackboardById(map);
		logger.info("==============获取单条黑板报信息:ID为" + blackboardId);
		// 获取评论
		List<CommentDto> commentDto = commentService.getAllComments(blackboardId);
		logger.info("==============获取单条黑板报评论:ID为" + blackboardId);

		
		//判断是不是本人，评论能不能删除
		List<Map<String,Object>> comments = new ArrayList<>();
		for(CommentDto c :commentDto){
			Map<String,Object> commentMap = new HashMap<>();
			commentMap.put("comment", c);
			if(c.getCommenterId().equals(mobile)){
				commentMap.put("canDelete", 1);
			}else{
				commentMap.put("canDelete", 0);
			}
			comments.add(commentMap);
		}
		
		
		logger.info("=============黑板报详情:" + blackboarddto);
		logger.info("=============评论详情:" + comments);
		return JsonResult.ok().put("blackboard", blackboarddto).put("comments", comments);
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
	public Map<String, Object> getPersonalBlackboard(String enterpriseId, String createMobile, Integer pageNumber) {
		Map<String, Object> map = new HashMap<>();
		map.put("enterpriseId", enterpriseId);
		// 封装分页信息
		map.put("createMobile", createMobile);
		map.put("first", (pageNumber - 1) * PAGE_SIZE);
		map.put("end", PAGE_SIZE);

		// 获取所有黑板报
		List<BlackboardDto> list = blackboardDao.getPersonalBlackboard(map);
		// 获取黑板报条数，计算分页总页数
		Long count = blackboardDao.getPersonalBlackboardCount(map);
		long page = count / PAGE_SIZE;
		if (count % PAGE_SIZE != 0) {
			page++;
		}
		
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
	public void delete(String blackboardId, String enterpriseId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blackboardId", blackboardId);
		map.put("enterpriseId", enterpriseId);

		imageService.deleteBlackboardImage(enterpriseId, blackboardId);

		commentService.deleteComments(enterpriseId, blackboardId);

		blackboardDao.delete(map);

	}

	/**
	 * 修改黑板报
	 * 
	 * @param blackboard
	 *            修改的黑板报对象
	 * @param images
	 *            图片文件对象
	 * @param serverPath
	 *            保存图片的服务器路劲 return flag 是否更新成功（true/false）
	 */
	@Override
	public boolean updateBlackboard(Blackboard blackboard) {

		blackboard.setUpdateTime(new Date());

		Boolean bFlag = blackboardDao.updateBlackboard(blackboard);
		return bFlag;
	}

	public List<Blackboard> dateChange(List<Blackboard> list) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Blackboard b : list) {
			String time = sdf.format(b.getCreateTime());
			System.out.println(time);
			try {
				b.setCreateTime(sdf.parse(time));
				System.out.println(b);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return list;

	}
}
