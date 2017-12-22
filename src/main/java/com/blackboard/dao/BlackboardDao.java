package com.blackboard.dao;

import java.util.List;
import java.util.Map;

import com.blackboard.dto.BlackboardDto;
import com.blackboard.entity.Blackboard;

public interface BlackboardDao {
	
	/**
	 * 创建黑板报 
	 * @param blackboard
	 */
	void createBlackboard(Blackboard blackboard);
	
	
	/**
	 * 查询企业所有黑板报 
	 * @param paramMap
	 */
	public List<Blackboard> getAllBlackboard(Map<String,Object> paramMap);
	
	
	/**
	 * 查询企业所有黑板报,被拉黑的黑板报不可见 
	 * @param enterpriseId
	 * @param blcaklterId
	 * @param beBlcaklterId
	 */
	List<Blackboard> getAllBlackboard(String enterpriseId,String blcaklterId,String beBlcaklterId);
	
	
	/**
	 * 查询企业单条黑板报详情 
	 * @param paramMap
	 */
	Blackboard getBlackboardById(Map<String,Object> paramMap);
	
	
	/**
	 * 查询企业个人所有黑板报
	 * @param paramMap
	 */
	List<Blackboard> getPersonalBlackboard(Map<String,Object> paramMap);
	
	
	/**
	 * 删除黑板报 
	 * @param paramMap
	 */
	void delete(Map<String, Object> paramMap);
	
	
	/**
	 * 更新黑板报 
	 * @param blackboard
	 */
	boolean updateBlackboard(Blackboard blackboard);
	
	/**
	 * 获取个人黑板报总数量
	 * @param blackboard
	 * @return
	 */
	Long getPersonalBlackboardCount(Map<String, Object> paramMap);
	
	/**
	 * 获取企业黑板报总数量
	 * @param blackboard
	 * @return
	 */
	Long getALLBlackboardCount(Map<String, Object> paramMap);
	
}
