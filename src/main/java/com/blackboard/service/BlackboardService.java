package com.blackboard.service;


import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blackboard.entity.Blackboard;
import com.blackboard.utils.JsonResult;

public interface BlackboardService {
	
	
	/**
	 * 创建纯文字黑板报 
	 * @param blackBoard   黑板报对象
	 * @return blackboardId   黑板报ID
	 */
	void createBlackboard(Blackboard blackboard);
	
	
	/**
	 * 创建黑板报 
	 * @param blackboard   黑板报对象
	 * @param images 	        图片对象
	 * @param serverPath   保存图片的服务器路劲
	 * @return blackboardId   黑板报ID
	 */
	String createBlackboard(Blackboard blackboard,MultipartFile[] images,String serverPath);
	
	/**
	 * 查询企业所有黑板报
	 * @param enterpriseId  企业ID
	 * @param pageNumber    页码（第几页）
	 * @return blackboardList   黑板报列表对象
	 */
	Map<String,Object> getAllBlackboard(String enterpriseId,Integer pageNumber);
	
	
	/**
	 * 查询企业所有黑板报,被拉黑的黑板报不可见 
	 * @param enterpriseId
	 * @param blcaklterId
	 * @param beBlcaklterId
	 * @return List<Blackboard> 黑板报列表
	 */
	List<Blackboard> getAllBlackboard(String enterpriseId,String blcaklterId,String beBlcaklterIdString);
	
	
	/**
	 * 展示单条黑板报详情
	 * @param enterpriseId  企业ID
	 * @param blackboardId  黑板报ID
	 * @param currentUser   当前用户
	 * @return result    当前黑板报所有详情（评论、状态）
	 */
	JsonResult getBlackboardById(String blackboardId,String enterpriseId,String mobile);
	
	
	/**
	 * 查询企业个人所有黑板报
	 * @param enterpriseId   企业ID
	 * @param createBy       黑板报所属用户
	 * @param pageNumber     页码（第几页）
	 * @return List<Blackboard> 个人发布的所有黑板报记录
	 */
	Map<String,Object> getPersonalBlackboard(String enterpriseId, String createBy,Integer pageNumber);
	
	
	/**
	 * 删除黑板报 
	 * @param enterpriseId   企业ID
	 * @param blackboardId	  黑板报ID
	 */
	void delete(String blackboardId,String enterpriseId);
	
	/**
	 * 修改黑板报 
	 * @param blackboard   修改的黑板报对象 
	 * @param images	        图片文件对象
	 * @param serverPath   保存图片的服务器路劲
	 * return flag   是否更新成功（true/false）
	 */
	boolean updateBlackboard(Blackboard blackboard);

}
