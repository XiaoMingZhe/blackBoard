package com.blackboard.service;

import java.util.Map;

public interface OaMsgPushService {

	public void pushMsg(Map<String,Object> msg) throws Exception ;
}
