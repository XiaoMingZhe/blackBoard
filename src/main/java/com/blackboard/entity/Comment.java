package com.blackboard.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 评论
 * 
 * @author Administrator
 *
 */
public class Comment {

	private String commentId;// 评论ID

	private String blackboardId;// 黑板报ID

	private String replyId;// 被评论人ID

	private String replyName;// 被评论人名字

	private String commenterName;// 评论人名字

	private String commenterId;// 评论人ID

	private String commentContent;// 评论内容
	@JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "GMT+8")
	private Date commentTime;// 评论时间

	private String enterpriseId; // 企业ID
	
	private Integer isread;//是否已读 0 未读 1 已读
	
	private String replyCommentId;//被回复评论ID
	
	private Integer status;//状态(0为正常,1为删除)
	
	private Integer replyStatus;//回复类型(0回复黑板报  1回复评论 2回复回复)

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getBlackboardId() {
		return blackboardId;
	}

	public void setBlackboardId(String blackboardId) {
		this.blackboardId = blackboardId;
	}

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	public String getCommenterName() {
		return commenterName;
	}

	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}

	public String getCommenterId() {
		return commenterId;
	}

	public void setCommenterId(String commenterId) {
		this.commenterId = commenterId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}

	public String getReplyCommentId() {
		return replyCommentId;
	}

	public void setReplyCommentId(String replyCommentId) {
		this.replyCommentId = replyCommentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(Integer replyStatus) {
		this.replyStatus = replyStatus;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", blackboardId=" + blackboardId + ", replyId=" + replyId
				+ ", replyName=" + replyName + ", commenterName=" + commenterName + ", commenterId=" + commenterId
				+ ", commentContent=" + commentContent + ", commentTime=" + commentTime + ", enterpriseId="
				+ enterpriseId + ", isread=" + isread + ", replyCommentId=" + replyCommentId + ", status=" + status
				+ ", replyStatus=" + replyStatus + "]";
	}
	

	
}
