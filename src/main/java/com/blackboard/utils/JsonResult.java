package com.blackboard.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class JsonResult extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public JsonResult() {
		put("code", 0);
	}
	
	public static JsonResult error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static JsonResult error(String msg) {
		return error(500, msg);
	}
	
	public static JsonResult error(int code, String msg) {
		JsonResult r = new JsonResult();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static JsonResult ok(String msg) {
		JsonResult r = new JsonResult();
		r.put("msg", msg);
		return r;
	}
	
	public static JsonResult ok(Map<String, Object> map) {
		JsonResult r = new JsonResult();
		r.putAll(map);
		return r;
	}
	
	public static JsonResult ok() {
		return new JsonResult();
	}

	public JsonResult put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
