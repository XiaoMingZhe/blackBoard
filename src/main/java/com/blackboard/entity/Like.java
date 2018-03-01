package com.blackboard.entity;

import java.util.Date;

/**
 * 点赞
 * @author xmz
 *
 */
public class Like {

	private Integer likeId;//点赞ID
	private String beLikedId;//被点赞信息ID
	private String likeUseid;//点赞人ID
	private String likeUser;//点赞人名称
	private Integer status;//状态，0为点赞，1为取消点赞(默认为0，逻辑删除)
	private Integer type;//点赞类型(0为黑板报,1为评论)
	private Integer read ;//是否已读 0 未读 1 已读
	private Date createTime;//点赞时间

	public String getLikeUser() {
		return likeUser;
	}

	public void setLikeUser(String likeUser) {
		this.likeUser = likeUser;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getLikeId() {
		return likeId;
	}
	
	public void setLikeId(Integer likeId) {
		this.likeId = likeId;
	}

	public String getBeLikedId() {
		return beLikedId;
	}

	public void setBeLikedId(String beLikedId) {
		this.beLikedId = beLikedId;
	}

	public String getLikeUseid() {
		return likeUseid;
	}

	public void setLikeUseid(String likeUseid) {
		this.likeUseid = likeUseid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
