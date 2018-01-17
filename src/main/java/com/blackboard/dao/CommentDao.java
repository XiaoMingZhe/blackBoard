package com.blackboard.dao;

import java.util.List;
import java.util.Map;

import com.blackboard.entity.Comment;


public interface CommentDao {
	
	/**
	 * 添加评论
	 * @param comment      评论对象
	 */
	void addComment(Comment comment);
	
	
	/**
	 * 展示当前黑板报所有评论
	 * @param parmMap    
	 */
	List<Comment> getAllComments(Map<String, Object> parmMap);

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
} 
