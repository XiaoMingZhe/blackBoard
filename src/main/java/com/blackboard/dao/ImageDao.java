package com.blackboard.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.blackboard.entity.Image;

public interface ImageDao {
	
	void saveImage(@Param("imageList")List<Image> imageList);

	List<Image> getBlackboardImage(Map<String, Object> parmMap);
	
	void deleteBlackboardImage(Map<String, Object> parmMap);
	
	void updateImage(Map<String,Object> map);
	
	void deleteImage(String imageId);
	
}
