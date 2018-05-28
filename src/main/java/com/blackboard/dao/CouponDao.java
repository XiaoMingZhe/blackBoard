package com.blackboard.dao;

import java.util.List;

import com.blackboard.entity.Coupon;

public interface CouponDao {

	/**
	 * 获取优惠卷码
	 * @return
	 */
	List<Coupon> selectCouponCode();
	
	/**
	 * 根据用户手机号获取优惠卷信息
	 * @param mobile
	 * @return
	 */
	Coupon selectCouponCodeByMobile(String mobile);
	
	/**
	 * 更新领取用户
	 * @param coupon
	 */
	void updateCoupon(Coupon coupon);
	
	/**
	 * 保存优惠卷
	 * @param CouponList
	 */
	void saveCoupon(List<String> CouponList);
	
}
