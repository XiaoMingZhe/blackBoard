package com.blackboard.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 何荣
 * @create 2017-09-24 20:37
 * 计算两个日期之间的毫秒值,转换成相应格式字符串的时间形式
 **/
public class TimeUtils {

    public static String getMyTime(Date date){
        //计算传入日期与当前时间只差的秒值
        long second = ((new Date().getTime()) - date.getTime())/1000;
        if(second < 60){
            return "刚刚";
        }
        //计算两个时间之间相差几分钟
        long minutes = second / 60 ;
        if(minutes < 60){
            return minutes + "分钟前";
        }
        //计算两个时间之间相差几个小时
        long hours = minutes / 60 ;
        if(hours < 24 ){
            return hours + "小时前";
        }
        //计算两个时间相差几天
        long days = hours / 24 ;
        return days + "天前" ;
    }

    //将Date对象装换成时间字符串
    public static String toDateTimeString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    //将毫秒值装换成date对象
    public static Date toDate (String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return d ;
    }
}
