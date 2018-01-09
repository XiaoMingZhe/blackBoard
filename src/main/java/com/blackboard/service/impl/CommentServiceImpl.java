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
		String commentcontent = comment.getCommentContent();
		System.out.println(commentcontent);
		if(commentcontent.indexOf("\\")!=-1){
			System.out.println("转换");
			String commentcontent2 = commentcontent.replaceAll("\\", ".");
			comment.setCommentContent(commentcontent2);
		};
		Comment com = new Comment();
		try {
			commentDao.addComment(comment);
			 com = commentDao.selectCommentById(comment.getCommentId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return com;
	}

	
	/**
	 * 展示当前黑板报所有评论
	 * @param enterpriseId    企业ID
	 * @param blackBoardId    黑板报ID
	 * @return List<Comment>  评论列表
	 */
	@Override
	public List<Comment> getAllComments(String blackboardId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blackboardId", blackboardId);
		
		List<Comment> comments = commentDao.getAllComments(map);
		for(Comment c :comments){
			String content = c.getCommentContent();
			if(content.indexOf(".")!=-1){
				String commentcontent2 = content.replaceAll(".", "\\");
				c.setCommentContent(commentcontent2);
			};
		}
		
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
