package com.blackboard.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackboard.dao.BlackboardDao;
import com.blackboard.dao.CommentDao;
import com.blackboard.dao.SystemMessageDao;
import com.blackboard.entity.Blackboard;
import com.blackboard.entity.Comment;
import com.blackboard.entity.SystemMessage;
import com.blackboard.service.MsgReturnService;
import com.blackboard.utils.MsgPushThread;

@Service
public class MsgReturnServiceImpl implements MsgReturnService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BlackboardDao blackboardDao;
	@Autowired
	private SystemMessageDao systemMessageDao;
	@Autowired
	private CommentDao commentDao;

	@Override
	public String MsgReturn(String msgid, String result) {
		String remark = "";

		switch (result) {
		case "200":
			if(msgid.indexOf("blackboard")!=-1){
				String blackboardId = msgid.substring(10);
				Blackboard bb = blackboardDao.selectBlackboardById(blackboardId);
				if(bb.getPushList()!=null && bb.getPushList().length()>0){
				Map<String, Object> MsgPush = new HashMap<>();
				MsgPush.put("Title", bb.getTitle());
				MsgPush.put("Connent", "来自："+bb.getCreateBy());
				MsgPush.put("blackboardId", bb.getBlackboardId());
				String[] bblist = bb.getPushList().split(",");
				List<String> list = new ArrayList<>();
				for(String b :bblist){
					list.add(b);
				}
				MsgPush.put("mobile", list);
				Thread thread = new MsgPushThread(MsgPush);
				thread.start();
				}
			}
			return "ok";
		case "300":
			remark = "其他";
			break;
		case "310":	
			remark = "接口字段非法";
			break;
		case "320":	
			remark = "用户信用不足";
			break;
		case "330":	
			remark = "非法用户行为";
			break;
		case "340":	
			remark = "内容涉政";
			break;
		case "350":	
			remark = "内容涉黑";
			break;
		case "360":	
			remark = "内容涉黄";
			break;
		case "370":	
			remark = "涉及诈骗";
			break;
		case "380":	
			remark = "涉及广告";
			break;
		case "400":	
			remark = "等待审核";
			break;
		default:
			break;
		}
		
		if(msgid.indexOf("blackboard")!=-1){
			String blackboardid = msgid.substring(10);
			logger.info("=============blackboardid:"+blackboardid+"=============");
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
			logger.info("=============commentId:"+commentId+"=============");
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
