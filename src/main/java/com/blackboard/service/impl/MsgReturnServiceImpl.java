package com.blackboard.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackboard.dao.BlackboardDao;
import com.blackboard.service.MsgReturnService;

@Service
public class MsgReturnServiceImpl implements MsgReturnService {
	
	@Autowired
	private BlackboardDao blackboardDao;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String MsgReturn(String msgid, String result) {
		String remark = "";
		if("200".equals(result)){
			return "ok";
		}
		if("300".equals(result)){
			remark = "其他";
		}
		if("310".equals(result)){
			remark = "接口字段非法";
		}
		if("320".equals(result)){
			remark = "用户信用不足";
		}
		if("330".equals(result)){
			remark = "非法用户行为";
		}
		if("340".equals(result)){
			remark = "内容涉政";
		}
		if("350".equals(result)){
			remark = "内容涉黑";
		}
		if("360".equals(result)){
			remark = "内容涉黄";
		}
		if("370".equals(result)){
			remark = "诈骗";
		}
		if("380".equals(result)){
			remark = "广告";
		}
		if("400".equals(result)){
			remark = "等待审核";
		}
		
		String blackboardid = msgid.substring(10);
		logger.info("============blackboardid:"+blackboardid+"========");
		Map<String, Object> map = new HashMap<>();
		map.put("blackBoardId", blackboardid);
		map.put("remark", remark);
		blackboardDao.updateremark(map);
		
		return "ok";
	}

}
