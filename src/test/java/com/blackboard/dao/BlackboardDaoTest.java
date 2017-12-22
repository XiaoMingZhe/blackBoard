package com.blackboard.dao;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.blackboard.BaseTest;
import com.blackboard.dao.BlackboardDao;
import com.blackboard.entity.Blackboard;
import com.blackboard.service.BlackboardService;
import com.blackboard.utils.GainUuid;

public class BlackboardDaoTest extends BaseTest {
	
	@Autowired
	private BlackboardDao blackboardDao;
	@Autowired
	private BlackboardService blcakboardService;
	
	/*
	@Test
	public void createBlackboard() {
		Blackboard blackboard = new Blackboard();
		blackboard.setEnterpriseId("100");
		blackboard.setTitle("test");
		blackboard.setContent("create for testing!");
		blackboard.setCreateBy("xiaoming");
		String blackboardId = blcakboardService.createBlackboard(blackboard);
		//blackboard.setBlackBoradId(GainUuid.getUUID());
		//blackboard.setCreateTime(new Date());
		System.out.println("*************************"+blackboard);
		
		//blackboardDao.createBlackboard(blackboard);
		
		//System.out.println(blackboardId);
		
		Map<String, Object> map = new HashMap<>();
        map.put("enterpriseId" , "100");
        map.put("blackboardId" , blackboardId);
		
		Blackboard blackboard2 = blackboardDao.getBlackboardById(map);
		System.out.println(blackboard2);
		
	}
	
	*/
//	@Test
//	public void getAllBlackboard() {
//		String eid = "101";
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("enterpriseId", eid);
//		map.put("first", 0);
//		map.put("end", 10);
//		List<Blackboard> bblist = blackboardDao.getAllBlackboard(map);
//		for(Blackboard ls:bblist){
//		System.out.println(ls);
//		}	
//	}
	
	@Test
	public void getBlackboardById() {
		Map<String, Object> map = new HashMap<>();
        map.put("enterpriseId" , "100");
        map.put("blackboardId" , "abc");
		
		Blackboard blackboard = blackboardDao.getBlackboardById(map);
		System.out.println(blackboard);
	}
	
	
	@Test
	public void getPersonalBlackboard() {
		Map<String, Object> map = new HashMap<>();
        map.put("enterpriseId" , "101");
        map.put("createBy" , "lzx");
		
		List<Blackboard> personalList = blackboardDao.getPersonalBlackboard(map);
		System.out.println(personalList);
	}
	

	@Test
	public void testDelete() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("enterpriseId","100");
		paramMap.put("blackboardId", "abc");
		blackboardDao.delete(paramMap);
	}
	
	
	@Test
	public void updateBlackboard() {
		
		Map<String, Object> map = new HashMap<>();
        map.put("enterpriseId" , "101");
        map.put("blackboardId" , "qwer");
		
		Blackboard blackboard = blackboardDao.getBlackboardById(map);
		System.out.println(blackboard);
		
		blackboard.setTitle("TEST");
		blackboard.setContent("TEST");
		//blackboard.setUpdateTime(new Date());
		

		
		System.out.println(blackboard);
		
		blackboardDao.updateBlackboard(blackboard);
		
	}

}
