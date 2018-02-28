package com.blackboard.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 黑板报实体类
 * 
 * @author luzhenxing
 *
 */

public class Blackboard {

	private String blackboardId; // 黑板报ID
	private String enterpriseId; // 企业ID
	private String title; // 黑表报标题
	private String content; // 文字内容
	private String createBy; // 创建人姓名
	@JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
	private Date createTime; // 创建时间
	private String updateBy; // 更改人ID
	private Date updateTime; // 更改时间
	private String createById;// 创建用户ID
	private String createMobile;// 创建用户手机号
	private Integer pageViews;//浏览数
	private Integer type;//类型(0为黑板报，1为草稿)

	
	public Integer getPageViews() {
		return pageViews;
	}

	public void setPageViews(Integer pageViews) {
		this.pageViews = pageViews;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}

	public String getCreateMobile() {
		return createMobile;
	}

	public void setCreateMobile(String createMobile) {
		this.createMobile = createMobile;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Blackboard [blackboardId=" + blackboardId + ", enterpriseId=" + enterpriseId + ", title=" + title
				+ ", content=" + content + ", createBy=" + createBy + ", createTime=" + createTime + ", updateBy="
				+ updateBy + ", updateTime=" + updateTime + ", createById=" + createById + ", createMobile="
				+ createMobile + "]";
	}

}
