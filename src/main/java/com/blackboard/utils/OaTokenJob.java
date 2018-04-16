package com.blackboard.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * TOKEN
 * @author xmz
 *
 */
@Component
public class OaTokenJob {

	private static final String APP_ID = PropertiesUtils.getProperties("APP_ID");
	private static final String APP_PASSWORD =PropertiesUtils.getProperties("APP_PASSWORD");
	private static final String GRANT_TYPE = PropertiesUtils.getProperties("GRANT_TYPE");
	private static final String OA_MSG_TOKEN_URL = PropertiesUtils.getProperties("OA_MSG_TOKEN_URL");
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 获取消息推送token
	 * @return
	 * @throws Exception
	 */
	public String getMsgToken() throws Exception {
		JSONObject jsonObject = null;
		//拼装请求参数对象
		JSONObject params = new JSONObject();
		params.put("app_id", APP_ID);
		params.put("app_password", APP_PASSWORD);
		params.put("grant_type", GRANT_TYPE);
	
		//请求参数转成JSON
		logger.info("token请求参数json:  "+params);
		JSONObject respJson =HttpHelper.doPost(OA_MSG_TOKEN_URL, params);
		logger.info("token返回json:  "+respJson.toJSONString());
		jsonObject = respJson;
		String access_token = jsonObject.getString("access_token");
		return access_token;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("获取OAtoken============================");
		new OaTokenJob().getMsgToken();
		LocalCache localCache = LocalCache.createLocalCache();
		System.out.println(localCache.get("access_token"));
	}
	
}
