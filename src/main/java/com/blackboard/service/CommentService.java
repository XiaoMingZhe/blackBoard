package com.blackboard.service;

import java.util.List;

import com.blackboard.dto.CommentDto;
import com.blackboard.entity.Comment;
import com.blackboard.utils.JsonResult;


public interface CommentService {
	
	/**
	 * 添加评论
	 * @param comment      评论对象
	 * @return commentId   评论ID
	 */
	Comment addComment(Comment comment);
	
	/**
	 * 展示当前黑板报所有评论
	 * @param enterpriseId    企业ID
	 * @param blackBoardId    黑板报ID
	 * @return List<Comment>  评论列表
	 */
	List<CommentDto> getAllComments(String blackBoardId);
	
	/**
	 * 删除当前黑板报所有评论
	 * @param enterpriseId    企业ID
	 * @param blackBoardId    黑板报ID
	 */
	void deleteComments(String enterpriseId,String blackBoardId);
	
	/**
	 * 删除单条评论
	 * @param commentId
	 * @param commenterId
	 */
	JsonResult deleteOneComment(String commentId,String commenterId);

}
