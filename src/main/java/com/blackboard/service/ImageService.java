package com.blackboard.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.blackboard.entity.Image;

public interface ImageService {
	
	/**
	 * 添加图片
	 * @param enterpriseId      企业ID
	 * @param blackboardId      黑板报ID
	 * @param images            图片文件数组对象
	 * @param serverPath        服务器路径
	 * @return imageId          图片ID
	 */
	List<Map<String,Object>> addImage(List<MultipartFile> images,String serverPath,String Path,HttpServletRequest request);
	
	/**
	 * 展示当前黑板报所有图片
	 * @param enterpriseId      企业ID
	 * @param blackboardId      黑板报ID
	 * @return List<Image>      图片列表
	 */
	List<Image> getBlackboardImage(String enterpriseId,String blackboardId);
	
	/**
	 * 删除当前黑板报所有图片
	 * @param enterpriseId      企业ID
	 * @param blackboardId      黑板报ID
	 */
	void deleteBlackboardImage(String enterpriseId,String blackboardId);
	
	
	/**
	 * 更新图片
	 * @param enterpriseId      企业ID
	 * @param blackboardId      黑板报ID
	 * @param images            图片文件数组对象
	 * @param serverPath        服务器路径
	 * @return true/false       修改结果
	 */
	Boolean updateBlackboardImage(String enterpriseId, String blackboardId,MultipartFile[] images,String serverPath);
	
	
	/**
	 * 删除图片
	 * @param imagePath
	 */
	void deleteImage(String imagePath,String serverPath);
	
	/**
	 * 根据黑板报ID删除图片
	 * @param blackboardIdList
	 * @param serverPath
	 */
	void deleteImageForBlackboard(List<String> blackboardIdList,String serverPath);
	
}
