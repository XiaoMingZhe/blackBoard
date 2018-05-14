package com.blackboard.dao;

import java.util.Map;

import com.blackboard.entity.ReadingRecord;

public interface ReadingRecordDao {

	/**
	 * 插入阅读数据
	 * @param readingRecord
	 */
	void saveReadingRecord(ReadingRecord readingRecord);
	
	/**
	 * 查看阅读数
	 * @param map
	 * @return
	 */
	long selectReadingRecordCount(Map<String,Object> map);
}
