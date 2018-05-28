package com.blackboard.task;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackboard.dto.RcsToken;
import com.blackboard.service.BlackboardService;
import com.blackboard.utils.SpringContextUtil;

public class CheckUserTask implements Runnable {

	public static final Logger LOGGER = LoggerFactory.getLogger(CheckUserTask.class);
	
	private RcsToken rcsToken;
	
	private HttpSession session;
	
	private BlackboardService blackboardService; 
	
	public CheckUserTask(RcsToken rcsToken,HttpSession session) {
		this.rcsToken = rcsToken;
		this.session = session;
		this.blackboardService = (BlackboardService) SpringContextUtil.getBean("blackboardServiceImpl");
	}
	@Override
	public void run() {
		try {
			blackboardService.CheckToken(rcsToken, session);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
