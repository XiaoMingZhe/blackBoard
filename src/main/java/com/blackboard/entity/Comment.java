package com.blackboard.entity;

import java.util.Date;

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

	private Date commentTime;// 评论时间

	private String enterpriseId; // 企业ID

	public String getCommenterId() {
		return commenterId;
	}

	public void setCommenterId(String commenterId) {
		this.commenterId = commenterId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

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

	public String getCommenter() {
		return commenterId;
	}

	public void setCommenter(String commenter) {
		this.commenterId = commenter;
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

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", blackboardId=" + blackboardId + ", replyId=" + replyId
				+ ", replyName=" + replyName + ", commenterName=" + commenterName + ", commenterId=" + commenterId
				+ ", commentContent=" + commentContent + ", commentTime=" + commentTime + ", enterpriseId="
				+ enterpriseId + "]";
	}

}
