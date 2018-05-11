package com.blackboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackboard.entity.Like;
import com.blackboard.service.LikeService;
import com.blackboard.utils.JsonResult;

@Controller
@RequestMapping("/Like")
public class LikeController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LikeService likeService;
	
	/**
	 * 点赞黑板报
	 * @param request
	 * @param blackboardId
	 * @return
	 */
	@RequestMapping(value = "/LikeOfBlackBoard", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult BlackBoardLike(HttpServletRequest request,@RequestParam("blackboardId") String blackboardId,@RequestParam("likeUser") String likeUser){
		logger.info("=======点赞黑板报:"+blackboardId+"==========");
		return Like(request,blackboardId,0,likeUser);
	};
	
	/**
	 * 点赞评论
	 * @param request
	 * @param commentId
	 * @return
	 */
	@RequestMapping(value = "/LikeOfComment", method = RequestMethod.GET)
	@ResponseBody
	private JsonResult CommentLike(HttpServletRequest request,@RequestParam("commentId") String commentId,@RequestParam("likeUser") String likeUser){
		logger.info("=======点赞评论:"+commentId+"==========");
		return Like(request,commentId,1,likeUser);
	};
	
	/**
	 * 点赞
	 * @param request
	 * @param beLikedId
	 * @param type
	 * @return
	 */
	private JsonResult Like(HttpServletRequest request,String beLikedId,Integer type,String likeUser){
		try {
			// 手机号
			String mobile = (String) request.getSession().getAttribute("mobile");
			Like like = new Like();
			like.setBeLikedId(beLikedId);
			like.setType(type);
			like.setLikeUser(likeUser);
			if ((mobile == null || mobile.trim().length() <= 0)) {
				like.setLikeUseid("13432879269");
			} else {
				like.setLikeUseid(mobile);
			}
			likeService.Like(like);
			return JsonResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("点赞失败。");
		}
	}
}
