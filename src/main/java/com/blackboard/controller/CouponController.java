package com.blackboard.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackboard.dao.LoginlogDao;
import com.blackboard.entity.Loginlog;
import com.blackboard.service.CouponService;
import com.blackboard.utils.JsonResult;
import com.blackboard.utils.UnifiedAuthentication;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "coupon")
public class CouponController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LoginlogDao loginlogDao;
	@Autowired
	private CouponService couponService;

	@RequestMapping(value = "/toCouponindex")
	@ResponseBody
	private JsonResult index(HttpServletRequest request, HttpServletResponse response) {
		String token = (String) request.getParameter("token");
		// 企业ID
		String enterDeptId = (String) request.getParameter("enterDeptId");
		// 获取访问全地址
		String url = "";
		url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getServletPath();
		if (request.getQueryString() != null) {
			url += "?" + request.getQueryString();
		}

		logger.info("访问地址为:" + url);
		// 判断token有没有认证过
		UnifiedAuthentication unifiedAuthentication = new UnifiedAuthentication();
		String ss = "";
		String msisdn = "";
		Integer count = loginlogDao.findToken(token);
		if (count == 0) {
			try {
				ss = unifiedAuthentication.validateToken(token);
				logger.info("=============获取的参数：" + ss);
				JSONObject jsonObject = JSONObject.fromObject(ss);
				msisdn = jsonObject.getJSONObject("body").getString("msisdn");
				logger.info("=============登陆手机号:" + msisdn);
				// 添加访问记录
				Loginlog loginlog = new Loginlog();
				loginlog.setCreateTime(new Date());
				loginlog.setUseId(msisdn);
				loginlog.setToken(token);
				loginlog.setEnterpriseId(enterDeptId);
				loginlogDao.saveLog(loginlog);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			msisdn = loginlogDao.findUserId(token);
		}
		logger.info("========================================");
		return JsonResult.ok().put("mobile", msisdn);
		// String uri = "http://jasontan.cn/";
		// String param ="";
		// String result = sendGet(uri,param);
		//
		// logger.info("=============绑定数据开始=============");
		// // 绑定数据
		// logger.info("=============绑定token:" + token);
		// logger.info("=============绑定enterDeptId:" + enterDeptId);
		// logger.info("=============绑定msisdn:" + msisdn);
		// HttpSession session = request.getSession();
		// session.setAttribute("token", token);
		// session.setAttribute("enterDeptId", enterDeptId);
		// session.setAttribute("mobile", msisdn);
		// logger.info("=============绑定数据完毕=============");
		// System.out.println(result);
		// return result;
	}

	/**
	 * 获取优惠卷
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/GetCoupon", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult GetCoupon(HttpServletRequest request) {
		// 获取信息
		// 手机号
		String phone = (String) request.getParameter("mobile");
		logger.info("当前登录手机号:" + phone);
		if (phone == null || phone.trim().length() <= 0) {
			return JsonResult.error("获取用户信息失败，请重新进入");
		}

		try {
			JsonResult jsonResult = couponService.GetCoupon(phone);
			return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("服务器内部异常");
		}
	}

	@RequestMapping(value = "/saveCoupon", method = RequestMethod.POST)
	@ResponseBody
	private JsonResult saveCoupon(HttpServletRequest request, @RequestBody Map<String, Object> CouponListMap) {
		List<Map<String, Object>> CouponList = (List<Map<String, Object>>) CouponListMap.get("CouponList");
		List<String> list = new ArrayList<>();
		for (Map<String, Object> m : CouponList) {
			list.add((String) m.get("coupon"));
		}
		couponService.saveCoupon(list);
		return JsonResult.ok();
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

}
