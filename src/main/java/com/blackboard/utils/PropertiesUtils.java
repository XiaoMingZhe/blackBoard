package com.blackboard.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

	 private static Properties p = new Properties();  
	  
	    /** 
	     * 读取properties配置文件信息 
	     */  
	    static{  
	        try {  
	            p.load(Tools.class.getClassLoader().getResourceAsStream("OaMsg.properties"));  
	        } catch (IOException e) {  
	            e.printStackTrace();   
	        }  
	    }  
	    /** 
	     * 根据key得到value的值 
	     */  
	    public static String getProperties(String key)  
	    {  
	        return p.getProperty(key);  
	    }  
}
