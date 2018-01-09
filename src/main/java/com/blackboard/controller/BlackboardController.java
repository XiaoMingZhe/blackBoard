package com.blackboard.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackboard.dto.BlackboardDto;
import com.blackboard.dto.User;
import com.blackboard.entity.Blackboard;
import com.blackboard.service.BlackboardService;
import com.blackboard.utils.JsonResult;

@Controller
@RequestMapping("/blackboard")
public class BlackboardController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BlackboardService blackboardService;

	/**
	 * 创建黑板报
	 * 
	 * @param blackboardDto
	 *            黑板报对象
	 * @param images
	 *            图片对象
	 * @return blackboardId 黑板报ID
	 */
	@RequestMapping(value = "/createBlackboard", method = RequestMethod.POST)
	@ResponseBody
	private JsonResult createBlackboard(@RequestBody Blackboard blackboard, HttpServletRequest request) {

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

		// blackboard.setEnterpriseId(enterDeptId);
		// blackboard.setCreateMobile(mobile);
		// blackboard.setCreateById(EUserID);
		// 为了测试
		if ((enterDeptId == null || enterDeptId.trim().length() <= 0) && (mobile == null || mobile.trim().length() <= 0)) {
			blackboard.setEnterpriseId("517090");
			blackboard.setCreateMobile("13432879269");
			blackboard.setCreateById("123456789");
		} else {
			blackboard.setEnterpriseId(enterDeptId);
			blackboard.setCreateMobile(mobile);
			blackboard.setCreateById(EUserID);
		}

		logger.info("=============黑板报:" + blackboard);
		blackboardService.createBlackboard(blackboard);

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
		// 测试使用
		if (enterDeptId == null) {
			enterDeptId = "517090";
		}

		if (enterDeptId == null || enterDeptId.length() <= 0 || pageNumber == null) {

			return JsonResult.error("请求参数非法");
		}

		Map<String, Object> map = blackboardService.getAllBlackboard(enterDeptId, pageNumber);

		logger.info("=============企业所有黑板报" + map);

		return JsonResult.ok().put("blackboardList", map.get("list")).put("page", map.get("page"));
	}

	public String getAllBlackboard(String enterpriseId, String blcaklterId, String beBlcaklterIdString) {
		return null;
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

		if ((enterDeptId == null || enterDeptId.trim().length() <= 0) && (mobile == null || mobile.trim().length() <= 0)) {
			enterDeptId = "517090";
			mobile = "13432879269";
		}

		String currentUser = mobile;
		logger.info("==============获取单条黑板报详情:ID为" + blackboardId);
		logger.info("==============企业ID为" + blackboardId);
		logger.info("==============用户ID为" + blackboardId);

		if (enterDeptId == null || enterDeptId.length() <= 0 || blackboardId == null || blackboardId.length() <= 0
				|| currentUser == null || currentUser.length() <= 0) {

			return JsonResult.error("请求参数非法");
		}
		logger.info("==============详情判断参数完毕:ID为" + blackboardId);
		logger.info("==============获取单条黑板报信息:企业ID" + enterDeptId);
		JsonResult result = blackboardService.getBlackboardById(blackboardId, enterDeptId);

		BlackboardDto bb = (BlackboardDto) result.get("blackboard");

		logger.info("==============返回详情数据:" + bb);

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

		// 企业ID
		String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");
		// 用户ID
		String mobile = (String) request.getSession().getAttribute("mobile");

		logger.info("==================获取个人的所有黑板报:企业ID为:" + enterDeptId + "电话号码为" + mobile);

		if ((enterDeptId == null || enterDeptId.trim().length() <= 0) && (mobile == null || mobile.trim().length() <= 0)) {
			enterDeptId = "517090";
			mobile = "13432879269";
		}
		logger.info("==================设置个人的所有黑板报:企业ID为:" + enterDeptId + "电话号码为" + mobile);
		if (enterDeptId == null || enterDeptId.length() <= 0 || mobile == null || mobile.length() <= 0
				|| pageNumber == null) {

			return JsonResult.error("请求参数非法");
		}
		logger.info("==================获取个人的所有黑板报:" + mobile);
		Map<String, Object> map = blackboardService.getPersonalBlackboard(enterDeptId, mobile, pageNumber);
		logger.info("==================返回的数据:" + map);
		return JsonResult.ok().put("personalList", map.get("list")).put("page", map.get("page"));
	}

	/**
	 * 删除黑板报
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
	 * 修改黑板报
	 * 
	 * @param blackboard
	 *            修改的黑板报对象
	 * @param images
	 *            图片文件对象 return flag 是否更新成功（true/false）
	 */
	@RequestMapping(value = "/updateBlackboard", method = RequestMethod.POST)
	@ResponseBody
	private JsonResult updateBlackboard(@RequestBody Blackboard blackboard, HttpServletRequest request) {
		// 企业ID
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
		Boolean flag = blackboardService.updateBlackboard(blackboard);

		return JsonResult.ok().put("flag", flag);
	}

}
