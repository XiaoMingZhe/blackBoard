package com.blackboard.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.blackboard.dto.CommentDto;
import com.blackboard.entity.Comment;


public interface CommentDao {
	
	/**
	 * 添加评论(黑板报评论)
	 * @param comment      评论对象
	 */
	void addComment(Comment comment);
	
	/**
	 * 回复评论
	 * @param comment
	 */
	void reply(Comment comment);
	
	/**
	 * 回复评论下的回复
	 * @param comment
	 */
	void replyReply(Comment comment);
	
	/**
	 * 获取评论下的回复
	 * @return
	 */
	List<CommentDto> getreply(Map<String, Object> map);
	
	/**
	 * 展示当前黑板报所有评论
	 * @param parmMap    
	 */
	List<CommentDto> getAllComments(Map<String, Object> parmMap);

	/**
	 * 删除当前黑板报所有评论
	 * @param parmMap    
	 */
	void deleteComments(Map<String, Object> parmMap);

	/**
	 * 获取单条评论信息
	 * @param commentId
	 * @return
	 */
	Comment selectCommentById(String commentId);
	
	/**
	 * 获取黑板报评论数
	 * @param blackboardId
	 * @return
	 */
	Integer selectCount(String blackboardId);
	
	/**
	 * 删除单条评论
	 * @param commentId
	 */
	void deleteOneComments(String commentId);
	
	/**
	 * 删除回复
	 * @param ID
	 */
	void delectReply(String ID);
	
	/**
	 * 修改已读状态
	 * @param mobile
	 */
	void updateRead(@Param("mobile") String mobile,@Param("enterpriseId") String enterpriseId);
	
	/**
	 * 获取单条评论信息(包括点赞数，是否点赞)
	 * @param commentId
	 * @return
	 */
	CommentDto selectById(Map<String,Object> map);
} 
