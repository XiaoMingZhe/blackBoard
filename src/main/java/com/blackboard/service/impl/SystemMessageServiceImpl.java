package com.blackboard.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackboard.dao.SystemMessageDao;
import com.blackboard.dto.SystemMessageDto;
import com.blackboard.service.SystemMessageService;
import com.blackboard.utils.RelativeDateFormat;
import com.vdurmont.emoji.EmojiParser;

@Service
public class SystemMessageServiceImpl implements SystemMessageService {

	@Autowired
	private SystemMessageDao systemMessageDao;

	@Override
	public void deleteSystemMessage(List<Map<String,Integer>> MseeageIdList) {
		List<Integer> list = new ArrayList<>();
		for(Map<String,Integer> m : MseeageIdList){
			list.add((Integer) m.get("MessageId"));
		}
		systemMessageDao.deleteSystemMessage(list);
	}

	@Override
	public void updateSystemMessage(String MessageId) {
		systemMessageDao.updateSystemMessage(MessageId);
	}

	@Override
	public List<SystemMessageDto> selectSystemMessages(Map<String, String> map) {
		List<SystemMessageDto> list = systemMessageDao.selectSystemMessages(map);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (SystemMessageDto s : list) {
			s.setMessage(EmojiParser.parseToUnicode(s.getMessage()));
			try {
				Date date = sdf.parse(s.getCreateTime());
				s.setCreateTime(RelativeDateFormat.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		systemMessageDao.updateSystemMessage(map.get("userId"));
		return list;
	}

}
