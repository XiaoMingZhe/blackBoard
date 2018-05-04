package com.blackboard.controller;

import java.util.HashMap;
import java.util.List;
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
import org.w3c.dom.stylesheets.MediaList;

import com.blackboard.dto.SystemMessageDto;
import com.blackboard.service.SystemMessageService;
import com.blackboard.utils.JsonResult;

@Controller
@RequestMapping("/SystemMessage")
public class SystemMessageController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SystemMessageService systemMessageService;

	/**
	 * 删除系统消息
	 * 
	 * @param request
	 * @param MessageId
	 * @return
	 */
	@RequestMapping(value = "/deleteSystemMessage", method = RequestMethod.POST)
	@ResponseBody
	private JsonResult deleteSystemMessage(HttpServletRequest request, @RequestBody Map<String,Object> MessageId) {
		List<Map<String,Integer>> MessageIdList = (List<Map<String,Integer>>) MessageId.get("MessageId");
		if(MessageIdList == null){
			return JsonResult.error("消息列表为空");
		}
		logger.info("===============删除系统消息:"+MessageIdList+"===============");
		systemMessageService.deleteSystemMessage(MessageIdList);
		return JsonResult.ok();
	}

	/**
	 * 查询系统消息
	 * 
	 * @param request
	 * @param MessageId
	 * @return
	 */
	@RequestMapping(value = "/selectSystemMessages", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult selectSystemMessages(HttpServletRequest request) {
		// 企业ID
		String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");
		// 用户ID
		String mobile = (String) request.getSession().getAttribute("mobile");
		
		if ((enterDeptId == null || enterDeptId.trim().length() <= 0)
				&& (mobile == null || mobile.trim().length() <= 0)) {
			enterDeptId = "517090";
			mobile = "13928909312";
		}
		logger.info("===============查询系统消息===============");
		Map<String, String> map = new HashMap<>();
		map.put("userId", mobile);
		map.put("enterpriseId", enterDeptId);
		List<SystemMessageDto> returnList = systemMessageService.selectSystemMessages(map);
		logger.info("===============返回系统消息===============");
		return JsonResult.ok().put("list", returnList);
	}
}
