package com.blackboard.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReadingRecord {
	private Integer id;
	private String blackboardId;
	private String userMobile;
	@JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
	private Date createTime; // 创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBlackboardId() {
		return blackboardId;
	}

	public void setBlackboardId(String blackboardId) {
		this.blackboardId = blackboardId;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
