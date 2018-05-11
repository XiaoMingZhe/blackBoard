package com.blackboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackboard.service.MsgReturnService;
import com.blackboard.utils.JsonResult;

@Controller
@RequestMapping("/MsgReturn")
public class ReturnExamineController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MsgReturnService msgReturnService;
	
	@RequestMapping(value = "/result" , method = RequestMethod.POST)
	@ResponseBody
	private JsonResult result(HttpServletRequest request){
		logger.info("========消息审批消息返回======");
		try {
			String msgid = request.getParameter("msgid");
			String result = request.getParameter("result");
			logger.info("msgid:"+msgid);
			logger.info("result:"+result);
			msgReturnService.MsgReturn(msgid, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("failed!",e);
			return JsonResult.ok().put("date", "").put("status", 200).put("errormsg", "程序异常");
		}
		return JsonResult.ok().put("date", "").put("status", 200).put("errormsg", "");
	}
}
