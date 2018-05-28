package com.blackboard.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blackboard.dao.CouponDao;
import com.blackboard.entity.Coupon;
import com.blackboard.service.CouponService;
import com.blackboard.utils.JsonResult;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CouponDao couponDao;

	@Override
	public JsonResult GetCoupon(String mobile) {
		JsonResult jsonResult = new JsonResult();
		

		logger.info("当前用户手机号码为:" + mobile);
		

		Coupon coupon = couponDao.selectCouponCodeByMobile(mobile);
		//先判断有没有获取过
		//领过优惠卷
		if (coupon != null) {
			jsonResult.put("type", 0);
			jsonResult.put("message", "您已领取过优惠卷！");
			jsonResult.put("coupon", coupon.getCouponCode());
		}
		//没有领过
		else {
			//判断还有没有卷
			//没卷
			List<Coupon> couponList = couponDao.selectCouponCode();
			logger.info("优惠卷还剩" + couponList.size() + "张");
			if (couponList == null || couponList.size() <= 0) {
				jsonResult.put("type", 0);
				jsonResult.put("message", "优惠卷已发完，请留意下次活动！");
			} else {
				Coupon cc = couponList.get(0);
				cc.setUser(mobile);
				jsonResult.put("type", 1);
				jsonResult.put("message", "恭喜你，领取成功!");
				jsonResult.put("coupon", cc.getCouponCode());
				couponDao.updateCoupon(cc);
			}
		}
		return jsonResult;
	}

	@Override
	public void saveCoupon(List<String> CouponList) {
		logger.info("传入优惠卷数量为:"+CouponList.size());
		couponDao.saveCoupon(CouponList);
	}

}
