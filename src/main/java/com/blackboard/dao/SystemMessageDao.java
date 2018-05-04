package com.blackboard.dao;

import java.util.List;
import java.util.Map;

import com.blackboard.dto.SystemMessageDto;
import com.blackboard.entity.SystemMessage;

public interface SystemMessageDao {

	/**
	 * 保存返回消息
	 * @param systemMessage
	 */
	void saveSystemMessage(SystemMessage systemMessage);
	
	/**
	 * 删除系统消息
	 * @param MessageId
	 */
	void deleteSystemMessage(List<Integer> MseeageIdList);
	
	/**
	 * 更新系统消息
	 * @param MessageId
	 */
	void updateSystemMessage(String userId);
	
	/**
	 * 查询系统消息
	 * @param map
	 * @return
	 */
	List<SystemMessageDto> selectSystemMessages(Map<String,String> map);

	/**
	 * 获取未读系统信息条数
	 * @param map
	 * @return
	 */
	Long slectSystemCount(Map<String,String> map);
}
