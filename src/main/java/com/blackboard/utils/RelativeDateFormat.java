package com.blackboard.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RelativeDateFormat {

	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;
	private static final long ONE_WEEK = 604800000L;

	private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static final String ONE_DAY_AGO = "天前";
	private static final String ONE_MONTH_AGO = "月前";
	private static final String ONE_YEAR_AGO = "年前";

	public static String format(Date date) {
		
		if((new Date().getYear() - date.getYear())!=0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.format(date);
		}
		
		long delta = new Date().getTime() - date.getTime();
		
		if (delta < 1L * ONE_MINUTE) {
//			long seconds = toSeconds(delta);
			return "刚刚";
		}
		if (delta < 45L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		if (delta < 48L * ONE_HOUR) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			return "昨天"+sdf.format(date);
		}
		if (delta < 72L * ONE_HOUR) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			return "前天"+sdf.format(date);
		}
		if (delta < 12L * 4L * ONE_WEEK) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
			return sdf.format(date);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.format(date);
		}
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;

	}

	private static long toHours(long date) {

		return toMinutes(date) / 60L;

	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}

}
