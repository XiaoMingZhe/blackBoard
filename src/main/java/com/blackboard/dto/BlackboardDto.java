package com.blackboard.dto;

import java.util.Date;
import java.util.List;

import com.blackboard.entity.Blackboard;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BlackboardDto {

	private String blackboardId;// 黑板报ID
	private String enterpriseId;// 企业ID
	private String title;// 黑表报标题
	private String createBy;// 创建人姓名
	private String createMobile;// 创建人电话
	// @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
	private String createTime; // 创建时间
	private Integer commentCount;// 评论总数
	private String content; // 文字内容

	public String getBlackboardId() {
		return blackboardId;
	}

	public void setBlackboardId(String blackboardId) {
		this.blackboardId = blackboardId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateMobile() {
		return createMobile;
	}

	public void setCreateMobile(String createMobile) {
		this.createMobile = createMobile;
	}

	@Override
	public String toString() {
		return "BlackboardDto [blackboardId=" + blackboardId + ", enterpriseId=" + enterpriseId + ", title=" + title
				+ ", createBy=" + createBy + ", createMobile=" + createMobile + ", createTime=" + createTime
				+ ", commentCount=" + commentCount + ", content=" + content + "]";
	}

}
