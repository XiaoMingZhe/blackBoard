package com.blackboard.dto;

import java.util.List;

import com.blackboard.entity.Comment;

public class CommentDto extends Comment {

	private Integer likeCount;// 点赞数
	
	private Integer replyCount;//回复数

	private String time;//
	
	private Integer isLike;//是否点赞 0没点赞 1点赞
	
	public Integer getIsLike() {
		return isLike;
	}

	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private List<CommentDto> replyList;//评论下的回复
	
	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public List<CommentDto> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<CommentDto> replyList) {
		this.replyList = replyList;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

}
