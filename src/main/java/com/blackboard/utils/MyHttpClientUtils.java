package com.blackboard.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class MyHttpClientUtils {
	/**
	 * 用传统的URI类进行请求发送一个post请求，带的参数为json格式的
	 * 
	 * @param urlStr
	 *            请求的地址
	 * @param xmlInfo
	 *            xml格式的参数
	 */
	public static String doPostJsonParam(String urlStr, String jsonInfo) {
		String res = "";
		try {
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/json");
			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
			out.write(new String(jsonInfo.getBytes("UTF-8")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				res += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
