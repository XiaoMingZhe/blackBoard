package com.blackboard.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Loginlog {

	private String logId;// 登陆日志ID
	private String useId;// 用户id
	@JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
	private Date createTime; // 登陆时间
	private String token;// token

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
