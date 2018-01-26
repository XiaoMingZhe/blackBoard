package com.blackboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackboard.entity.Comment;
import com.blackboard.service.CommentService;
import com.blackboard.utils.JsonResult;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 添加评论
	 * 
	 * @param comment
	 *            评论对象
	 * @return commentId 评论ID
	 */
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	@ResponseBody
	private JsonResult addComment(@RequestBody Comment comment, HttpServletRequest request) {

		// 手机号
		String mobile = (String) request.getSession().getAttribute("mobile");
		// 企业ID
		String enterDeptId = (String) request.getSession().getAttribute("enterDeptId");

		if (enterDeptId == null && mobile == null) {
			enterDeptId = "517090";
			mobile = "13432879269";
		}
		comment.setEnterpriseId(enterDeptId);
		comment.setCommenterId(mobile);

		logger.info("================" + mobile + "评论" + comment.getBlackboardId());
		logger.info("================" + comment + "================"); 
		if (comment == null || comment.getEnterpriseId() == null || comment.getEnterpriseId().length() <= 0
				|| comment.getBlackboardId() == null || comment.getBlackboardId().length() <= 0
				|| comment.getCommentContent() == null || comment.getCommentContent().length() <= 0
				|| comment.getCommenter() == null || comment.getCommenter().length() <= 0) {
			return JsonResult.error("请求参数非法");
		}

		Comment com = commentService.addComment(comment);
		return JsonResult.ok().put("Comment", com);
	}

	/**
	 * 删除评论
	 * 
	 * @param request
	 * @param commentId
	 * @return
	 */
	@RequestMapping(value = "/deleteComment", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult deleteComment(HttpServletRequest request, String commentId) {
		// 手机号
		String mobile = (String) request.getSession().getAttribute("mobile");
		if(mobile == null){
			mobile = "13432879269";
		}
		if (commentId == null) {
			return JsonResult.error("请求参数非法");
		}
		
		JsonResult jsonResult = commentService.deleteOneComment(commentId, mobile);
		
		return jsonResult;
	}

}
