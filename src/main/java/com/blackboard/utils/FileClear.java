package com.blackboard.utils;
/*package com.soecode.lyf.utils;

import java.io.File;

import org.apache.log4j.Logger;

public class FileClear {
	　private static final Logger logger = Logger.getLogger(FileClear.getClass());
	　private final static String FS = System.getProperty("file.separator");
	　private static String TOMCAT_HOME = System.getProperty("catalina.home");
	　private static String PATH = "temp";
	　private static final String TEMP_PATH = TOMCAT_HOME + FS + PATH + FS;
	　private static boolean isRunning = false;
	　　
	public void run() {
		
	　　if (!isRunning) {
		　　logger.debug("删除/Tomcat/Temp/目录下过期图片执行开始...");
		　　isRunning = true;
		　　logger.debug("开始执行删除tomcat下temp文件夹png文件任务");
		　　logger.debug("要删除图片文件所在路径为" + TEMP_PATH);
		　　File fileTemp = new File(TEMP_PATH);
		　　// 判断文件是否存在
		　　boolean falg = false;
		　　falg = fileTemp.exists();
		　　if (falg) {
			　　logger.debug("temp文件存在");
			　　if (true == fileTemp.isDirectory()) {
			　　logger.debug("temp文件是个目录");
			　　String[] png = fileTemp.list();
				　　for (int i = 0; i < png.length; i++) {
					　　if (true == png[i].endsWith("png")) {
					　　File file = new File(TEMP_PATH + FS + png[i]);
						　　if (true==file.isFile()) {
						　　boolean flag = false;
						　　flag = file.delete();
							　　if (flag) {
							　　logger.debug("成功删除无效图片文件:" + file.getName());
							　　}
					　　		}
				　　		}
			　　		}
		　　		}
		　　} else {
		　　logger.debug("未找到tomcat/temp文件夹，执行失败，请手动删除temp下所有png文件");
		　　}
		
		　　isRunning = false;
		　　logger.debug("删除/Tomcat/Temp/目录下过期图片执行结束...");
		　　}
	
	　　}

}
*/