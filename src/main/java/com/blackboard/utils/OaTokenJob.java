package com.blackboard.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.blackboard.dto.PropertiesUtilesDto;

/**
 * TOKEN
 * @author xmz
 *
 */
@Component
public class OaTokenJob {

	private static final String APP_ID = PropertiesUtilesDto.getAppId();
	private static final String APP_PASSWORD = PropertiesUtilesDto.getAppPassword();
	private static final String GRANT_TYPE = PropertiesUtilesDto.getGrantType();
	private static final String OA_MSG_TOKEN_URL = PropertiesUtilesDto.getOaMsgTokenUr();
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 启动后1S执行一次，之后每50分钟执行一次 cron = "0 0/50 * * * ?"
	 * 测试每5分钟执行一次cron = "0 0/5 * * * ?"
	 * initialDelay 不支持cron表达式注册器
	 * @throws Exception token保存1个小时
	 */
	@Scheduled(initialDelay=500,fixedDelay=3000000)
	public void getMsgToken() throws Exception {
		System.out.println();
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
		if (null != jsonObject) {
			//保存在缓存中，方便取用
			LocalCache localCache = LocalCache.createLocalCache();
			localCache.put("access_token", jsonObject.getString("access_token"));
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("获取OAtoken============================");
		new OaTokenJob().getMsgToken();
		LocalCache localCache = LocalCache.createLocalCache();
		System.out.println(localCache.get("access_token"));
	}
	
}
