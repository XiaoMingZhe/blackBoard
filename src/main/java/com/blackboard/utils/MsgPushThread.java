package com.blackboard.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackboard.service.OaMsgPushService;
import com.blackboard.service.impl.OaMsgPushImpl;

public class MsgPushThread extends Thread {

	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Map<String, Object> MsgPush;

	public MsgPushThread(Map<String, Object> MsgPush) {
		this.MsgPush = MsgPush;
	}

	@Override
	public void run() {
		try {
			OaMsgPushService oaMsgPushService = new OaMsgPushImpl();
			oaMsgPushService.pushMsg(MsgPush);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("消息推送发送失败");
		}
	}

	
}
