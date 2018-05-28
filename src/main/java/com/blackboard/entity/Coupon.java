package com.blackboard.entity;

/**
 * 小应用
 * @author xmz
 *
 */
public class Coupon {

	private int id;// 卷ID
	private String couponCode;// 激活码
	private String user;// 领劵用户

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
