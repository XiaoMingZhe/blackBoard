package com.blackboard.dto;

import com.blackboard.entity.Comment;

public class CommentDto extends Comment {

	private Integer likeCount;// 点赞数

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

}
