package com.blackboard.service;

import java.util.Map;

public interface OaMsgPushService {

	void pushMsg(Map<String,Object> msg) throws Exception ;
}
