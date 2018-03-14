package com.blackboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Remind {

	private String title;// 标题或图片
	private String userName;// 用户名
	private String blackboardId;//黑板报ID
	private String content;//内容
	@JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
	private String creatTime;// 时间
	private String moblie;// 用户手机号
	private String remindID;// 提醒对应的信息ID
	private Integer type;// 对应的信息的类型(0评论，1点赞)
	private Integer likeType;// 若是点赞 点赞的类型 (0黑板报，1评论 99不是点赞)

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBlackboardId() {
		return blackboardId;
	}

	public void setBlackboardId(String blackboardId) {
		this.blackboardId = blackboardId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getMoblie() {
		return moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}

	public String getRemindID() {
		return remindID;
	}

	public void setRemindID(String remindID) {
		this.remindID = remindID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLikeType() {
		return likeType;
	}

	public void setLikeType(Integer likeType) {
		this.likeType = likeType;
	}

}
