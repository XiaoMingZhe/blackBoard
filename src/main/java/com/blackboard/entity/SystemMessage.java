package com.blackboard.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SystemMessage {

	private Integer messageId;//系统消息ID
	private String userId;//用户手机号码
	private String enterprise_id;//企业ID
	private String message;//系统消息
	@JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "GMT+8")
	private Date createTime;//创建时间
	private Integer isRead;//是否已读0未读 1已读
	
	public String getEnterprise_id() {
		return enterprise_id;
	}
	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	
	
}