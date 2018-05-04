package com.blackboard.dto;

public class SystemMessageDto {
	private Integer messageId;// 系统消息ID
	private String message;// 系统消息
	private String createTime;// 创建时间
	private Integer isRead;// 是否已读0未读 1已读

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

}
