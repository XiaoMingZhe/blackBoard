package com.blackboard.service;

import java.util.List;
import java.util.Map;

import com.blackboard.dto.CommentDto;
import com.blackboard.entity.Comment;
import com.blackboard.utils.JsonResult;


public interface CommentService {
	
	/**
	 * 添加评论(黑板报评论)
	 * @param comment
	 * @return
	 */
	Comment addComment(Comment comment);
	
	/**
	 * 回复评论
	 * @param comment
	 * @return
	 */
	Comment reply(Comment comment);
	
	/**
	 * 回复回复
	 * @param comment
	 * @return
	 */
	Comment replyReply(Comment comment);
	
	/**
	 * 获取当前黑板报所有评论
	 * @param blackBoardId 黑板报ID
	 * @return
	 */
	List<CommentDto> getAllComments(String blackBoardId,String mobile);
	
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

	/**
	 * 删除单条回复
	 * @param commentId
	 * @param commenterId
	 */
	JsonResult delectReply(String commentId,String commenterId);
	
	/**
	 * 获取回复详情
	 * @param commentId
	 * @param mobile
	 * @return
	 */
	Map<String,Object> getReplys(String commentId,String mobile);
}
