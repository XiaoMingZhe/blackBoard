package com.blackboard.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackboard.dao.CommentDao;
import com.blackboard.entity.Comment;
import com.blackboard.service.CommentService;
import com.blackboard.utils.GainUuid;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;

	
	/**
	 * 添加评论
	 * @param comment      评论对象
	 * @return commentId   评论ID
	 */
	@Override
	public Comment addComment(Comment comment) {
		comment.setCommentId(GainUuid.getUUID());
		comment.setCommentTime(new Date());
		
		commentDao.addComment(comment);
		Comment com = commentDao.selectCommentById(comment.getCommentId());
		
		return com;
	}

	
	/**
	 * 展示当前黑板报所有评论
	 * @param enterpriseId    企业ID
	 * @param blackBoardId    黑板报ID
	 * @return List<Comment>  评论列表
	 */
	@Override
	public List<Comment> getAllComments(String enterpriseId,String blackboardId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enterpriseId", enterpriseId);
		map.put("blackboardId", blackboardId);
		
		List<Comment> comments = commentDao.getAllComments(map);
		
		return comments;
	}

	
	/**
	 * 删除当前黑板报所有评论
	 * @param enterpriseId    企业ID
	 * @param blackBoardId    黑板报ID
	 */
	@Override
	public void deleteComments(String enterpriseId, String blackboardId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enterpriseId", enterpriseId);
		map.put("blackboardId", blackboardId);
		
		commentDao.deleteComments(map);
	}

}
