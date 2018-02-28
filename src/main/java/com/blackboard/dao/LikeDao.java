package com.blackboard.dao;

import java.util.Map;

import com.blackboard.entity.Like;

public interface LikeDao {

	//插入点赞信息
	void addLike(Like like);
	
	//修改点赞信息
	void updateLike(Like like);
	
	//查找状态
	Map<String,Object> findStatus (Like like);
}
