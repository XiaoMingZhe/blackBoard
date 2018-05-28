package com.blackboard.service;

import java.util.List;

import com.blackboard.utils.JsonResult;

public interface CouponService {

	JsonResult GetCoupon(String mobile); 
	
	void saveCoupon(List<String> CouponList);
}
