package com.blackboard.dao;

import com.blackboard.entity.Loginlog;

public interface LoginlogDao {

	//保存登陆信息
	void saveLog(Loginlog loginlog);
	
	//查找登陆日志
	Integer findLog(String EUserID);
	
	//查找token
	Integer findToken(String token);
	
	//查找手机号
	String findUserId(String token);
}
