package com.blackboard.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackboard.dao.LoginlogDao;
import com.blackboard.entity.Loginlog;
import com.blackboard.utils.JsonResult;
import com.blackboard.utils.UnifiedAuthentication;

import net.sf.json.JSONObject;

@Controller
public class IndexController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoginlogDao loginlogDao;

	/**
	 * 跳转到主页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toindex")
	private String index(HttpServletRequest request) {

		String token = (String) request.getParameter("token");
		// 企业ID
		String enterDeptId = (String) request.getParameter("enterDeptId");
		// 联系人ID
		String EUserID = (String) request.getParameter("EUserID");

		logger.info("=============获取信息token:" + token);
		logger.info("=============获取信息enterDeptId:" + enterDeptId);
		logger.info("=============获取信息EUserID:" + EUserID);

		// 获取访问全地址
		String url = "";

		url = request.getScheme() + "://" + request.getServerName()

				+ ":" + request.getServerPort()

				+ request.getServletPath();

		if (request.getQueryString() != null) {

			url += "?" + request.getQueryString();

		}

		System.out.println(url);

		// 判断token有没有认证过

		UnifiedAuthentication unifiedAuthentication = new UnifiedAuthentication();
		String ss = "";
		String msisdn = "";
		Integer count = loginlogDao.findToken(token);
		if (count == 0) {
			try {
				ss = unifiedAuthentication.validateToken(token);
				logger.info("=======获取的参数：" + ss);
				JSONObject jsonObject = JSONObject.fromObject(ss);
				msisdn = jsonObject.getJSONObject("body").getString("msisdn");
				logger.info("========登陆手机号:" + msisdn);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			msisdn = loginlogDao.findUserId(token);
		}
		
		
		logger.info("============绑定数据开始============");
		// 绑定数据
		logger.info("============绑定token:"+token);
		logger.info("============绑定enterDeptId:"+enterDeptId);
		logger.info("============绑定EUserID:"+EUserID);
		logger.info("============绑定msisdn:"+msisdn);
		HttpSession session = request.getSession();
		session.setAttribute("token", token);
		session.setAttribute("enterDeptId", enterDeptId);
		session.setAttribute("EUserID", EUserID);
		session.setAttribute("mobile", msisdn);
		request.setAttribute("enterDeptId", enterDeptId);
		request.setAttribute("msisdn", msisdn);
		
		logger.info("============绑定数据完毕============");
		
		return "index";
	}

	/**
	 * 判断有没有访问过黑板报
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/isLogin", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult isLogin(HttpServletRequest request) {
		String mobile = (String) request.getSession().getAttribute("mobile");
		String token = (String) request.getSession().getAttribute("token");

		Integer count = 0;
		if (mobile != null) {
			count = loginlogDao.findLog(mobile);
		}

		logger.info("=============判断有没有访问过黑板报：用户ID为" + mobile);
		logger.info("=============有没有访问过?0是没访问过:" + count);

		// 添加访问记录
		Loginlog loginlog = new Loginlog();
		loginlog.setCreateTime(new Date());
		loginlog.setUseId(mobile);
		loginlog.setToken(token);
		loginlogDao.saveLog(loginlog);

		logger.info("===========用户登陆:" + mobile);
		if (count > 0) {
			return JsonResult.ok().put("isLogin", 1);
		} else {
			return JsonResult.ok().put("isLogin", 0);
		}

	}
}
