package com.blackboard.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackboard.dao.CommentDao;
import com.blackboard.dto.CommentDto;
import com.blackboard.entity.Comment;
import com.blackboard.service.CommentService;
import com.blackboard.utils.GainUuid;
import com.blackboard.utils.JsonResult;
import com.blackboard.utils.RelativeDateFormat;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;

	/**
	 * 添加评论(黑板报评论)
	 */
	@Override
	public Comment addComment(Comment comment) {
		comment.setCommentId(GainUuid.getUUID());
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
	 * 评论下回复回复
	 */
	@Override
	public Comment replyReply(Comment comment) {
		comment.setCommentId(GainUuid.getUUID());
		Comment com = new Comment();
		try {
			commentDao.replyReply(comment);
			 com = commentDao.selectCommentById(comment.getCommentId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return com;
	}

	/**
	 * 回复评论
	 */
	@Override
	public Comment reply(Comment comment) {
		comment.setCommentId(GainUuid.getUUID());
		Comment com = new Comment();
		
		try {
			commentDao.reply(comment);
			com = commentDao.selectCommentById(comment.getCommentId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return com;
	}


	/**
	 * 获取当前黑板报所有评论
	 */
	@Override
	public List<CommentDto> getAllComments(String blackboardId,String mobile) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blackboardId", blackboardId);
		map.put("mobile", mobile);
		List<CommentDto> comments = commentDao.getAllComments(map);
		dateChange(comments);
		for(CommentDto c :comments){
			String commentId = c.getCommentId();
			map.put("commentId", commentId);
			List<CommentDto> list = commentDao.getreply(map);
			dateChange(list);
			c.setReplyList(list);
		}
		return comments;
	}

	/**
	 * 获取回复详情
	 */
	@Override
	public Map<String,Object> getReplys(String commentId,String mobile){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentId", commentId);
		map.put("mobile", mobile);
		map.put("getreply", "getreply");
		
		CommentDto cDto = commentDao.selectById(map);
		cDto.setTime(RelativeDateFormat.format(cDto.getCommentTime()));
		List<CommentDto> list = commentDao.getreply(map);
		dateChange(list);
		Map<String,Object> returnMap = new HashMap<>();
		
		
		// 判断是不是本人，评论能不能删除
		List<Map<String, Object>> comments = new ArrayList<>();
		for (CommentDto c : list) {
			Map<String, Object> commentMap = new HashMap<>();
			commentMap.put("comment", c);
			if (c.getCommenterId().equals(mobile)) {
				commentMap.put("canDelete", 1);
			} else {
				commentMap.put("canDelete", 0);
			}
			comments.add(commentMap);
		}
		returnMap.put("comment", cDto);
		returnMap.put("reply", comments);
		System.out.println(comments);
		return returnMap;
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


	@Override
	public JsonResult deleteOneComment(String commentId, String commenterId) {
		Comment comment = commentDao.selectCommentById(commentId);
		System.out.println(comment);
		System.out.println(commentId);
		if(comment == null || !comment.getCommenterId().equals(commenterId)){
			return JsonResult.error("删除评论失败");
		}
		commentDao.deleteOneComments(commentId);
		return JsonResult.ok();
	}
	
	
	@Override
	public JsonResult delectReply(String commentId, String commenterId) {
		Comment comment = commentDao.selectCommentById(commentId);
		System.out.println(comment);
		System.out.println(commentId);
		if(comment == null || !comment.getCommenterId().equals(commenterId)){
			return JsonResult.error("删除回复失败");
		}
		commentDao.delectReply(commentId);
		return JsonResult.ok();
	}

	/**
	 * 时间转换
	 * @param list
	 * @return
	 */
	private List<CommentDto> dateChange(List<CommentDto> list) {
		for (CommentDto c : list) {
			try {
				c.setTime(RelativeDateFormat.format(c.getCommentTime()));
				System.out.println(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;

	}

}
