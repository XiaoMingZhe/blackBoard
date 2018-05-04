package com.blackboard.service;

import java.util.List;
import java.util.Map;

import com.blackboard.dto.SystemMessageDto;

public interface SystemMessageService {

	/**
	 * 删除系统消息
	 * @param MessageId
	 */
	void deleteSystemMessage(List<Map<String,Integer>> MseeageIdList);
	
	/**
	 * 更新系统消息
	 * @param MessageId
	 */
	void updateSystemMessage(String MessageId);
	
	/**
	 * 查询系统消息
	 * @param map
	 * @return
	 */
	List<SystemMessageDto> selectSystemMessages(Map<String,String> map);
}
