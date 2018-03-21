package com.blackboard.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackboard.dao.BlackboardDao;
import com.blackboard.dto.BlackboardDto;
import com.blackboard.dto.CheckAttack;
import com.blackboard.dto.CreateBlackboardDto;
import com.blackboard.dto.Remind;
import com.blackboard.entity.Blackboard;
import com.blackboard.service.BlackboardService;
import com.blackboard.utils.CheckAttackUtil;
import com.blackboard.utils.JsonResult;

@Controller
@RequestMapping("/blackboard")
public class BlackboardController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private CheckAttackUtil checkAttackUtil = new CheckAttackUtil();

	@Autowired
	private BlackboardService blackboardService;

	@Autowired
	private BlackboardDao blackboardDao;
	

	/**
	 * 保存草稿
	 * 
	 * @param createBlackboardDto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveDraft", method = RequestMethod.POST)
	@ResponseBody
	private JsonResult saveDraft(@RequestBody CreateBlackboardDto createBlackboardDto, HttpServletRequest request) {
		createBlackboardDto.getBlackboard().setType(1);
		return saveBlackboard(createBlackboardDto, request);
	}

	/**
	 * 保存黑板报
	 * 
	 * @param createBlackboardDto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/createBlackboard", method = RequestMethod.POST)
	@ResponseBody
	private JsonResult createBlackboard(@RequestBody CreateBlackboardDto createBlackboardDto,
			HttpServletRequest request) {
		createBlackboardDto.getBlackboard().setType(0);
		return saveBlackboard(createBlackboardDto, request);
	}

	/**
	 * 保存黑板报或草稿
	 * 
	 * @param createBlackboardDto
	 * @param request
	 * @return
	 */
	private JsonResult saveBlackboard(@RequestBody CreateBlackboardDto createBlackboardDto,
			HttpServletRequest request) {

		System.out.println(createBlackboardDto);
		Blackboard blackboard = createBlackboardDto.getBlackboard();
		CheckAttack checkAttack = createBlackboardDto.getCheckAttack();

		if (!checkAttackUtil.checkattack(request, checkAttack)) {
			return JsonResult.error("参数有误");
		}

		// 获取信息
		// 企业ID
		String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");
		// 用户ID
		String EUserID = (String) request.getSession().getAttribute("EUserID");
		// 手机号
		String mobile = (String) request.getSession().getAttribute("mobile");

		logger.info("=============获取信息enterDeptId" + enterDeptId);
		logger.info("=============获取信息EUserID" + EUserID);
		logger.info("=============获取信息mobile" + mobile);
		logger.info("=============黑板报内容" + blackboard);

		if (blackboard == null || blackboard.getTitle().trim() == null || blackboard.getTitle().trim().length() <= 0
				|| blackboard.getContent().trim() == null || blackboard.getContent().trim().length() <= 0) {

			return JsonResult.error("请求参数非法");
		}

		// 为了测试
		if ((enterDeptId == null || enterDeptId.trim().length() <= 0)
				&& (mobile == null || mobile.trim().length() <= 0)) {
			blackboard.setEnterpriseId("517090");
			blackboard.setCreateMobile("13432879269");
			blackboard.setCreateById("123456789");
		} else {
			blackboard.setEnterpriseId(enterDeptId);
			blackboard.setCreateMobile(mobile);
			blackboard.setCreateById(EUserID);
		}

		logger.info("=============黑板报:" + blackboard);
		blackboardService.createBlackboard(blackboard,createBlackboardDto.getVisibleRange());

		return JsonResult.ok();
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
	@RequestMapping(value = "/getAllBlackboardList", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult getAllBlackboard(HttpServletRequest request, @RequestParam("pageNumber") Integer pageNumber) {

		// 企业ID
		String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");
		// 用户ID
		String mobile = (String) request.getSession().getAttribute("mobile");
		
		// 测试使用
		if ((enterDeptId == null || enterDeptId.trim().length() <= 0)
				&& (mobile == null || mobile.trim().length() <= 0)) {
			enterDeptId = "517090";
			mobile = "13432879269";
		}

		if (enterDeptId == null || enterDeptId.length() <= 0 || mobile == null || mobile.length() <= 0
				|| pageNumber == null) {

			return JsonResult.error("请求参数非法");
		}

		Map<String, Object> map = blackboardService.getAllBlackboard(enterDeptId, pageNumber,mobile);

		logger.info("=============获取企业所有黑板报成功==============");

		// 获取所有黑板报ID集合,存到session
		Map<String, Object> selectID = new HashMap<String, Object>();
		selectID.put("enterDeptId", enterDeptId);
		selectID.put("type", 0);
		List<String> IDlist = blackboardDao.selectIDList(selectID);
		request.getSession().setAttribute("IDlist", IDlist);
		System.out.println(IDlist);

		return JsonResult.ok().put("blackboardList", map.get("list"))
							  .put("page", map.get("page"))
							  .put("remindCount", map.get("remindCount"))
							  .put("mobile", map.get("moblie"));
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
	@RequestMapping(value = "/getBlackboardDetails", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult getBlackboard(HttpServletRequest request, @RequestParam("blackboardId") String blackboardId) {

		// 企业ID
		String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");
		// 用户ID
		String mobile = (String) request.getSession().getAttribute("mobile");

		if ((enterDeptId == null || enterDeptId.trim().length() <= 0)
				&& (mobile == null || mobile.trim().length() <= 0)) {
			enterDeptId = "517090";
			mobile = "13432879269";
		}

		String currentUser = mobile;
		logger.info("==============获取单条黑板报详情:ID为" + blackboardId);
		logger.info("==============企业ID为" + enterDeptId);
		logger.info("==============用户ID为" + mobile);

		if (enterDeptId == null || enterDeptId.length() <= 0 || blackboardId == null || blackboardId.length() <= 0
				|| currentUser == null || currentUser.length() <= 0) {

			return JsonResult.error("请求参数非法");
		}
		logger.info("==============详情判断参数完毕:ID为" + blackboardId);
		logger.info("==============获取单条黑板报信息:企业ID" + enterDeptId);
		JsonResult result = blackboardService.getBlackboardById(blackboardId, enterDeptId, mobile);

		// 获取上一条 下一条黑板报ID
		List<String> IDlist = (List<String>) request.getSession().getAttribute("IDlist");
		int index = 0;

		if(IDlist != null && IDlist.size()>0){
			for (int i = 0; i < IDlist.size(); i++) {
				String listBlackboardId = IDlist.get(i);
				if (listBlackboardId.equals(blackboardId)) {
					index = i;
					break;
				}
			}
		}


		String lastBlackboardID = "";
		String nextBlackboardID = "";
		if (index - 1 > 0) {
			lastBlackboardID = IDlist.get(index - 1);
		}

		if(IDlist != null && IDlist.size()>0){
			if (index + 1 < IDlist.size()) {
				nextBlackboardID = IDlist.get(index + 1);
			}
		}
		

		result.put("lastBlackboardID", lastBlackboardID);
		result.put("nextBlackboardID", nextBlackboardID);

		BlackboardDto bb = (BlackboardDto) result.get("blackboard");

		logger.info("==============获取单条黑板报成功================");

		if (bb.getCreateMobile().equals(mobile)) {
			return result.put("canrevise", 1);
		} else {
			return result.put("canrevise", 0);
		}
	}

	
	/**
	 * 查询企业个人所有黑板报
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param createBy
	 *            黑板报所属用户
	 * @param pageNumber
	 *            页码（第几页）
	 * @return List<Blackboard> 个人发布的所有黑板报记录
	 */
	@RequestMapping(value = "/getPersonalBlackboard", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult getPersonalBlackboard(HttpServletRequest request,
			@RequestParam("pageNumber") Integer pageNumber) {
		JsonResult jsonResult = getBlackboard(request, pageNumber, 0);
		return jsonResult;
	}
	 
	/**
	 * 查询企业个人所有草稿
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param createBy
	 *            黑板报所属用户
	 * @param pageNumber
	 *            页码（第几页）
	 * @return List<Blackboard> 个人发布的所有黑板报记录
	 */
	@RequestMapping(value = "/getDraft", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult getDraft(HttpServletRequest request,
			@RequestParam("pageNumber") Integer pageNumber) {
		JsonResult jsonResult = getBlackboard(request, pageNumber, 1);
		return jsonResult;
	}
	
	
	/**
	 * 查询个人黑板报或草稿
	 * @param request
	 * @param pageNumber
	 * @param type
	 * @return
	 */
	private JsonResult getBlackboard(HttpServletRequest request,
			 Integer pageNumber,Integer type) {

		// 企业ID
		String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");
		// 用户ID
		String mobile = (String) request.getSession().getAttribute("mobile");

		logger.info("==================获取个人的所有黑板报:企业ID为:" + enterDeptId + "电话号码为" + mobile);

		if ((enterDeptId == null || enterDeptId.trim().length() <= 0)
				&& (mobile == null || mobile.trim().length() <= 0)) {
			enterDeptId = "517090";
			mobile = "13432879269";
		}
		logger.info("==================设置个人的所有黑板报:企业ID为:" + enterDeptId + "电话号码为" + mobile);
		if (enterDeptId == null || enterDeptId.length() <= 0 || mobile == null || mobile.length() <= 0
				|| pageNumber == null) {

			return JsonResult.error("请求参数非法");
		}
		logger.info("==================获取个人的所有黑板报:" + mobile);
		Map<String, Object> map = blackboardService.getPersonalBlackboard(enterDeptId, mobile, pageNumber,type);
		logger.info("==================返回的数据:" + map);

		// 获取所有黑板报ID集合,存到session
		Map<String, Object> selectID = new HashMap<String, Object>();
		selectID.put("enterDeptId", enterDeptId);
		selectID.put("mobile", mobile);
		List<String> IDlist = blackboardDao.selectIDList(selectID);
		request.getSession().setAttribute("IDlist", IDlist);

		return JsonResult.ok().put("personalList", map.get("list")).put("page", map.get("page"));
	}

	
	
	
	/**
	 * 获取他人黑板报
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getAnotherPersonBlackboard", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult getAnotherPersonBlackboard(HttpServletRequest request, @RequestParam("mobile") String mobile,
			@RequestParam("pageNumber") Integer pageNumber) {

		String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");
		if (enterDeptId == "" || enterDeptId == null) {
			enterDeptId = "517090";
		}

		logger.info("==================获取他人的所有黑板报:企业ID为" + enterDeptId + "，电话号码为" + mobile);

		if (enterDeptId == null || enterDeptId.length() <= 0 || mobile == null || mobile.length() <= 0
				|| pageNumber == null) {
			return JsonResult.error("请求参数非法");
		}

		Map<String, Object> returnmap = blackboardService.getPersonalBlackboard(enterDeptId, mobile, pageNumber,0);

		logger.info("==================返回的数据:" + returnmap);

		// 获取所有黑板报ID集合,存到session
		Map<String, Object> selectID = new HashMap<String, Object>();
		selectID.put("enterDeptId", enterDeptId);
		selectID.put("mobile", mobile);
		List<String> IDlist = blackboardDao.selectIDList(selectID);
		request.getSession().setAttribute("IDlist", IDlist);

		return JsonResult.ok().put("personalList", returnmap.get("list")).put("page", returnmap.get("page"));
	}

	/**
	 * 删除单条黑板报
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param blackboardId
	 *            黑板报ID
	 */
	@RequestMapping(value = "/deleteBlackboard", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult deleteBlackboard(@RequestParam("blackboardId") String blackboardId, HttpServletRequest request) {
		// 企业ID
		String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");

		if (enterDeptId == null) {
			enterDeptId = "517090";
		}

		if (enterDeptId == null || enterDeptId.length() <= 0 || blackboardId == null || blackboardId.length() <= 0) {

			return JsonResult.error("请求参数非法");
		}
		logger.info("===============删除黑板报:" + blackboardId);
		blackboardService.delete(blackboardId, enterDeptId);

		return JsonResult.ok();
	}
	
	/**
	 * 批量删除黑板报
	 * @param blackboardIdList 黑板报ID集合
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deletrBlackboardList" , method = RequestMethod.POST)
	@ResponseBody
	private JsonResult deleteBlackboard(@RequestBody Map<String,Object> blackboardIdList ,HttpServletRequest request){
		
		System.out.println(blackboardIdList.size());
		if(blackboardIdList == null|| blackboardIdList.size()<=0){
			return JsonResult.error("请求参数非法");
		}
		blackboardService.deleteList((List<Map<String, Object>>) blackboardIdList.get("blackboardIdList"));
		
		return JsonResult.ok();
	}
	

	/**
	 * 修改黑板报
	 * 
	 * @param blackboard
	 *            修改的黑板报对象 return flag 是否更新成功（true/false）
	 */
	@RequestMapping(value = "/updateBlackboard", method = RequestMethod.POST)
	@ResponseBody
	private JsonResult updateBlackboard(@RequestBody CreateBlackboardDto createBlackboardDto, HttpServletRequest request) {
		// 企业ID
		
		Blackboard blackboard = createBlackboardDto.getBlackboard();
		String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");
		if (enterDeptId == null) {
			enterDeptId = "517090";
		}
		blackboard.setEnterpriseId(enterDeptId);

		if (blackboard == null || blackboard.getEnterpriseId() == null || blackboard.getEnterpriseId().length() <= 0
				|| blackboard.getBlackboardId() == null || blackboard.getBlackboardId().length() <= 0
				|| blackboard.getTitle() == null || blackboard.getTitle().length() <= 0) {

			return JsonResult.error("请求参数非法");
		}
		logger.info("================修改黑板报:" + blackboard.getBlackboardId());
		
		Boolean flag = blackboardService.updateBlackboard(blackboard,createBlackboardDto.getVisibleRange());

		return JsonResult.ok().put("flag", flag);
	}

	/**
	 * 获取消息提醒列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectRemind" , method = RequestMethod.GET)
	@ResponseBody
	private JsonResult selectRemind(HttpServletRequest request){
		// 企业ID
		String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");
		// 用户ID
		String mobile = (String) request.getSession().getAttribute("mobile");
		
		if ((enterDeptId == null || enterDeptId.trim().length() <= 0)
				&& (mobile == null || mobile.trim().length() <= 0)) {
			enterDeptId = "517090";
			mobile = "13432879269";
		}
		
		if (enterDeptId == null || enterDeptId.length() <= 0 || mobile == null || mobile.length() <= 0) {

			return JsonResult.error("请求参数非法");
		}
		
		List<Remind> remindlist = blackboardService.selectRemind(mobile, enterDeptId);
		
		return JsonResult.ok().put("remindlist", remindlist);
	}
	
	
	
	
	/**
	 * 判断是否重放攻击
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean checkattack(HttpServletRequest request, CheckAttack checkAttack) {
		CheckAttack checkAttackSession = (CheckAttack) request.getSession().getAttribute("checkAttack");

		if (checkAttackSession == null) {
			request.getSession().setAttribute("checkAttack", checkAttack);
			return true;
		}

		String sign = checkAttack.getSign();
		String timestamp = checkAttack.getTimestamp();
		String nonce = checkAttack.getNonce();

		// MDS加密
		byte[] bytes = null;
		try {
			bytes = (timestamp + nonce).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String checksign = DigestUtils.md5DigestAsHex(bytes);

		// 判断参数有没有被中途篡改
		if (!sign.equals(checksign)) {
			return false;
		}

		// 判断有没有超时3分钟内
		Long times = new Date().getTime();
		if (times - Long.parseLong(timestamp) > 180) {
			return false;
		}

		// 判断随机数是否用过
		if (nonce.equals(checkAttackSession.getNonce())) {
			return false;
		}

		// 判断通过，覆盖新的验证
		request.getSession().setAttribute("checkAttack", checkAttack);
		return true;
	}

}
