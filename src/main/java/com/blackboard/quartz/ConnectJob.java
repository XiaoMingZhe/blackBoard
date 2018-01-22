package com.blackboard.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackboard.dao.BlackboardDao;


@Component("connectJob") 
public class ConnectJob  {

	@Autowired
	private BlackboardDao blackboardDao;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void getdual(){
		logger.info("============连接数据库防止连接超时:连接开始==============");
		//连接数据库防止连接超时
		blackboardDao.getdual();
		logger.info("============连接数据库防止连接超时:连接结束==============");
	}

}
