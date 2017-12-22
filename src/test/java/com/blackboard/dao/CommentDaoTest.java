package com.blackboard.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.blackboard.BaseTest;
import com.blackboard.dao.CommentDao;
import com.blackboard.entity.Comment;
import com.blackboard.service.CommentService;
import com.blackboard.utils.GainUuid;

public class CommentDaoTest extends BaseTest {
	
	@Autowired
	private CommentDao commentDao;

	
	
	@Test
	public void addComment() {
		Comment comment = new Comment();
		
		comment.setEnterpriseId("100");
		comment.setBlackboardId("06f9fd33305345d5b92d001e212ae331");
		comment.setCommentId(GainUuid.getUUID());
		comment.setCommentContent("test");
		comment.setReplyId("LZX");
		comment.setCommenter("lzx");
		comment.setCommentTime(new Date());
		
		System.err.println(comment);
		
		commentDao.addComment(comment);
		
		System.err.println(comment);
	}
	
	

	
	@Test
	public void getAllComments() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enterpriseId", "100");
		map.put("blackBoardId", "06f9fd33305345d5b92d001e212ae331");
		map.put("commentId", "2882fc20c74543738883480e781f4dae");
		
		List<Comment> comments = commentDao.getAllComments(map);
		
		for(Comment list:comments){
			System.out.println(list);
		}
	}
	
	

}
