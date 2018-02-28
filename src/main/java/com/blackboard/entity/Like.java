package com.blackboard.entity;

/**
 * 点赞
 * @author xmz
 *
 */
public class Like {

	private Integer likeId;//点赞ID
	private String beLikedId;//被点赞信息ID
	private String likeUseid;//点赞人ID
	private Integer status;//状态，0为点赞，1为取消点赞(默认为0，逻辑删除)
	private Integer type;//点赞类型(0为黑板报,1为评论)

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
