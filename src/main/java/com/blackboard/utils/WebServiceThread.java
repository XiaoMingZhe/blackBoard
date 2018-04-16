package com.blackboard.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServiceThread extends Thread {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Map<String, Object> WebServiceMap;
	
	public WebServiceThread(Map<String, Object> WebServiceMap){
		this.WebServiceMap = WebServiceMap;
	}

	@Override
	public void run() {
		WebServicesClient web = new WebServicesClient();
		String result = web.getWeather(WebServiceMap);
		if(!"成功接收".equals(result.trim())){
			logger.error("发送消息送审失败："+result);
		}
	}

	
}
