package com.blackboard.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackboard.dao.LikeDao;
import com.blackboard.entity.Like;
import com.blackboard.service.LikeService;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	private LikeDao likeDao;
	
	@Override
	public void Like(Like like) {
		Map<String,Object> statusMap = likeDao.findStatus(like);
		//判断之前有没有点赞
		//如果没找到，新增一条点赞信息
		if(statusMap == null){
			likeDao.addLike(like);
		} else{
			Integer likeId = (Integer)statusMap.get("likeId");
			Integer status = (Integer)statusMap.get("status");
			like.setLikeId(likeId);
			like.setStatus(status==0?1:0);
			likeDao.updateLike(like);
		}
	}
}
