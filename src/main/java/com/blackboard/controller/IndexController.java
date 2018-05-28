package com.blackboard.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackboard.dao.BlackboardDao;
import com.blackboard.dao.LoginlogDao;
import com.blackboard.dto.RcsToken;
import com.blackboard.entity.Loginlog;
import com.blackboard.task.CheckUserTask;
import com.blackboard.utils.JsonResult;
import com.blackboard.utils.UnifiedAuthentication;

import net.sf.json.JSONObject;

@Controller
public class IndexController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LoginlogDao loginlogDao;
	@Autowired
	private BlackboardDao blackboardDao;

	/**
	 * 检验用户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkUser", method = RequestMethod.POST)
	@ResponseBody
	private JsonResult checkUser(HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = null;
		String mobile = "";
		try {
			mobile = (String) request.getSession().getAttribute("mobile");
			String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");
			long time = System.currentTimeMillis();
			do {
				if (mobile == null || enterDeptId == null) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
					mobile = (String) request.getSession().getAttribute("mobile");
					enterDeptId = (String) request.getSession().getAttribute("enterDeptId");
				}
			} while ((mobile == null || enterDeptId == null) && System.currentTimeMillis() - time <= 2000);
			jsonResult = JsonResult.ok();
		} catch (Exception e) {
			logger.error("校验用户数据", e);
			return JsonResult.error("用户数据获取失败!");
		}
		Cookie cookie = new Cookie("mobile", mobile.trim());
		try {
			URLEncoder.encode("cookie的value值", "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cookie.setMaxAge(24 * 60 * 60);
		response.addCookie(cookie);
		return jsonResult;
	}

	/**
	 * 跳转到主页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toindex")
	private String info(HttpServletRequest request, HttpServletResponse response) {
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
		url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getServletPath();
		if (request.getQueryString() != null) {
			url += "?" + request.getQueryString();
		}
		System.out.println(url);

		RcsToken rcsToken = new RcsToken();
		rcsToken.setToken(token);
		rcsToken.setEnterId(enterDeptId);
		HttpSession session = request.getSession();

		CheckUserTask checkUserTask = new CheckUserTask(rcsToken, session);
		checkUserTask.run();
		return "index";
	}

	/**
	 * 消息推送跳转接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/push")
	public String push(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute("mobile", "");
		session.setAttribute("enterDeptId", "");
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
		logger.info("=============判断有没有访问过黑板报=============");
		String mobile = (String) request.getSession().getAttribute("mobile");
		String token = (String) request.getSession().getAttribute("token");
		String enterpriseId = (String) request.getSession().getAttribute("enterDeptId");

		Map<String, Object> map = new HashMap<>();
		map.put("enterpriseId", enterpriseId);
		map.put("EUserID", mobile);
		map.put("token", token);
		map.put("enterpriseId", enterpriseId);
		long blackboardCount = blackboardDao.getALLBlackboardCount(map);

		Integer count = 0;
		if (mobile != null) {
			count = loginlogDao.findLog(map);
		}
		logger.info("=============用户ID为:" + mobile);
		logger.info("=============有没有访问过?0是没访问过:" + count);

		// 添加访问记录
		Loginlog loginlog = new Loginlog();
		loginlog.setCreateTime(new Date());
		loginlog.setUseId(mobile);
		loginlog.setToken(token);
		loginlog.setEnterpriseId(enterpriseId);
		loginlogDao.saveLog(loginlog);
		logger.info("=============用户登陆:" + mobile);
		if (count > 0 || blackboardCount > 0) {
			return JsonResult.ok().put("isLogin", 1);
		} else {
			return JsonResult.ok().put("isLogin", 0);
		}
	}

	/**
	 * 获取当前登陆用户手机号码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMoblie", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult getMobile(HttpServletRequest request) {
		String moblie = (String) request.getSession().getAttribute("mobile");
		return JsonResult.ok().put("moblie", moblie);
	}

	/**
	 * 获取当前登陆用户企业ID
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getEnterpriseId", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult getEnterpriseId(HttpServletRequest request) {
		String enterpriseId = (String) request.getSession().getAttribute("enterDeptId");
		return JsonResult.ok().put("enterpriseId", enterpriseId);
	}
}
