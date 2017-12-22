package com.blackboard.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	/**
	 * 生成当前系统的时间，格式为yyyyMMddHHmmssSSS 用于统一认证验证token的接口(杭州研发中心的接口)
	 * 
	 * @return 17位的系统当前时间
	 */
	public static String getSystemTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}

}
