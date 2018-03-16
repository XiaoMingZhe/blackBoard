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
	List<BlackboardDto> getAllBlackboard(Map<String,Object> paramMap);
	
	
	/**
	 * 查询企业所有黑板报,被拉黑的黑板报不可见 
	 * @param enterpriseId
	 * @param blcaklterId
	 * @param beBlcaklterId
	 */
	List<Blackboard> getAllBlackboard(String enterpriseId,String blcaklterId,String beBlcaklterId);
	
	
	/**
	 * 查询单条黑板报详情 
	 * @param paramMap
	 */
	BlackboardDto getBlackboardById(Map<String,Object> paramMap);
	
	
	/**
	 * 查询企业个人所有黑板报
	 * @param paramMap
	 */
	List<BlackboardDto> getPersonalBlackboard(Map<String,Object> paramMap);
	
	
	/**
	 * 删除黑板报 
	 * @param paramMap
	 */
	void delete(Map<String, Object> paramMap);
	
	/**
	 * 批量删除黑板报
	 * @param map
	 */
	void deleteList(List<String> list);
	
	
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

	
	/**
	 * 定时连接数据库
	 * @return
	 */
	Long getdual();
	
	/**
	 * 更新浏览数
	 * @param blackBoardId
	 */
	void updatePageViews(String blackBoardId);
	
	/**
	 * 查询黑板报ID列表
	 * @return
	 */
	List<String> selectIDList(Map<String,Object> map);
	
	/**
	 * 消息提醒列表
	 * @param moblie
	 * @return
	 */
	List<Map<String,Object>> selectRemind(Map<String,Object> map);
	
	/**
	 * 添加可见范围
	 * @param visibleRange
	 */
	void saveVisibleRange(Map<String,Object> map);
	
	/**
	 * 删除可见范围
	 * @param blackBoardId
	 */
	void deleteVisibleRange(String blackBoardId);
	
	/**
	 * 批量删除可见范围
	 * @param list
	 */
	void deleteVisibleRangeList(List<String> list);
	
	
}
