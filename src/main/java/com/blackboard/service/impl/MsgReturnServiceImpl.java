package com.blackboard.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackboard.dao.BlackboardDao;
import com.blackboard.dao.CommentDao;
import com.blackboard.dao.SystemMessageDao;
import com.blackboard.entity.Comment;
import com.blackboard.entity.SystemMessage;
import com.blackboard.service.MsgReturnService;

@Service
public class MsgReturnServiceImpl implements MsgReturnService {
	
	@Autowired
	private BlackboardDao blackboardDao;
	@Autowired
	private SystemMessageDao systemMessageDao;
	@Autowired
	private CommentDao commentDao;
	
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
			remark = "涉及诈骗";
		}
		if("380".equals(result)){
			remark = "涉及广告";
		}
		if("400".equals(result)){
			remark = "等待审核";
		}
		
		if(msgid.indexOf("blackboard")!=-1){
			String blackboardid = msgid.substring(10);
			logger.info("============blackboardid:"+blackboardid+"========");
			Map<String, Object> map = new HashMap<>();
			map.put("blackBoardId", blackboardid);
			map.put("remark", remark);
			blackboardDao.updateremark(map);
			Map<String,String> blackboardMap = blackboardDao.selectBlackBoardTitle(blackboardid);
			String message = "你发布的黑板报【"+blackboardMap.get("title")+"】"+remark+"已被系统删除,删除得黑板报将放在草稿列表,请遵循平台规范,文明发布信息,传播正能量,谢谢。";
			
			//赋值
			SystemMessage systemMessage  = new SystemMessage();
			systemMessage.setEnterprise_id(blackboardMap.get("enterpriseId"));
			systemMessage.setUserId(blackboardMap.get("userId"));
			systemMessage.setMessage(message);
			
			//保存系统信息
			systemMessageDao.saveSystemMessage(systemMessage);
			
		}

		if(msgid.indexOf("comment")!=-1){
			String commentId = msgid.substring(7);
			logger.info("============commentId:"+commentId+"========");
			commentDao.delectReply(commentId);
			commentDao.deleteOneComments(commentId);
			Comment comment = commentDao.selectCommentById(commentId);
			String message = "你发布的评论/回复【"+comment.getCommentContent()+"】"+remark+"已被系统删除,请遵循平台规范,文明发布信息,传播正能量,谢谢。";
			
			//赋值
			SystemMessage systemMessage  = new SystemMessage();
			systemMessage.setEnterprise_id(comment.getEnterpriseId());
			systemMessage.setUserId(comment.getCommenterId());
			systemMessage.setMessage(message);
			
			//保存系统信息
			systemMessageDao.saveSystemMessage(systemMessage);
		}
		
		return "ok";
	}

}
