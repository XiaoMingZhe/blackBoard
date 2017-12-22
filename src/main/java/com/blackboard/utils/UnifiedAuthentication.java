package com.blackboard.utils;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class UnifiedAuthentication {

	private static Logger LOGGER = LoggerFactory.getLogger(UnifiedAuthentication.class);
	// 用于请求杭研接口的参数，从统一认证申请过来的
	public static final String SOURCE_ID = "95598e0e0039431db012610a26316ab7";// 需要申请
	public static final String APP_TYPE = "5";
	public static final String APP_ID = "";// 需要申请
	public static final String VERSION = "1.0";

	/**
	 * 杭研接口 验证token的接口
	 * 
	 * @param token
	 * @return
	 */
	public String validateToken(String token) {
		// 组装请求的参数
		JSONObject header = new JSONObject();
		// header
		header.put("version", VERSION);
		UUID uuid = UUID.randomUUID();
		header.put("msgid", uuid.toString());
		header.put("systemtime", DateUtils.getSystemTime());
		header.put("apptype", APP_TYPE);
		header.put("sourceid", SOURCE_ID);
		header.put("extparam", "300");
		// body
		JSONObject body = new JSONObject();
		body.put("token", token);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("header", header);
		jsonObj.put("body", body);

		// 3.发送请求获取结果
		String res = MyHttpClientUtils.doPostJsonParam(ConstantForApp.VALIDATE_TOKEN_URL, jsonObj.toString());
		LOGGER.info("请求的地址：" + ConstantForApp.VALIDATE_TOKEN_URL + "---》请求的参数:" + (jsonObj.toString()));
		return res;
	}
}
