package com.blackboard.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.blackboard.entity.Image;

public interface ImageDao {
	
	void saveImage(@Param("imageList")List<Image> imageList);

	List<Image> getBlackboardImage(String blackboardID);
	
	void deleteBlackboardImage(Map<String, Object> parmMap);
	
	void updateImage(Map<String,Object> map);
	
	void deleteImage(String imageId);
	
	String getImageById(String imageId);
	
	List<String> getImagePath(List<String> blackboardIdList);
	
	void deleteImages(List<String> blackboardIdList);
	
}
