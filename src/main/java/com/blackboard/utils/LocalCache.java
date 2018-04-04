/**   
 * @Title: LocalCache.java 
 * @Package com.yitianyike.utils 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author Aaron·Li   
 * @date 2016年11月29日 下午6:28:52 
 * @version V1.0   
 */
package com.blackboard.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本地缓存工具（不建议存储量超过50万）
 * 
 * @author shuwen.xiao
 */
public class LocalCache implements Serializable {
	private static ConcurrentHashMap<String, CacheValue> cache = new ConcurrentHashMap<>();
	private static Logger log = LoggerFactory.getLogger(LocalCache.class);
	private static List<String> removeList = new ArrayList<String>();
	private static final long serialVersionUID = -3273454956563078833L;
	private static boolean isRun = false;// 用于防止定时任务叠加执行
	private static Timer timer;
	private static LocalCache localCache;
	private static byte[] lock = new byte[0];
	/**
	 * 定时器清理间隔（默认60秒钟执行一次）
	 */
	private static Long DEFAULT_TASK_DELAY = 60000 * 120L;

	/**
	 * 存储有效期,默认存储120分钟
	 */
	private static Long DEFAULT_TIMEOUT = 60000 * 120L;

	private LocalCache() {
	}

	public static void setDefaultTimeout(Long timeout) {
		if (timeout > 0) {
			DEFAULT_TIMEOUT = timeout;
		}
	}

	public static void setDefaultClearDelay(Long delay) {
		if (delay >= 500) {
			DEFAULT_TASK_DELAY = delay;
		}
	}

	public static Long getDefaultTimeout() {
		return DEFAULT_TIMEOUT;
	}

	public static Long getDefaultClearDelay() {
		return DEFAULT_TASK_DELAY;
	}

	private static void clearCache() {
		if (!isRun) {
			synchronized (lock) {
				try {
					if (removeList.isEmpty() == false) {
						removeList.clear();
					}
					isRun = true;
					long start = System.currentTimeMillis();
					if (!cache.isEmpty()) {
						Iterator<Entry<String, CacheValue>> i = cache.entrySet().iterator();
						while (i.hasNext()) {
							Entry<String, CacheValue> e = i.next();
							if (e.getValue() == null) {
								removeList.add(e.getKey());
							} else {
								if (e.getValue().isTimeout()) {
									removeList.add(e.getKey());
								}
							}
						}

						for (String key : removeList) {
							cache.remove(key);
						}
					}
					int clearSize = removeList.size();
					removeList.clear();
					log.debug("本次清理：" + clearSize + ",当前剩余：" + cache.size() + ",清理用时：" + (System.currentTimeMillis() - start) + "毫秒");
				} finally {
					isRun = false;
				}
			}

		}
	}

	public static LocalCache createLocalCache() {
		synchronized (lock) {
			if (localCache == null) {
				localCache = new LocalCache();
				timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						clearCache();
					}
				}, 0, DEFAULT_TASK_DELAY);
			}
		}
//		for(String key : localCache.cache.keySet()) {
//			log.info("缓存中的数据:key:"+key+"-value:"+localCache.cache.get(key).getValue());
//		}
		
		return localCache;
	}

	public boolean containsKey(String key) {
		return get(key) != null;
	}

	public Object get(String key) {
		if (key != null) {
			Object obj = null;
			if (cache.containsKey(key)) {
				obj = cache.get(key);
			}
			if (obj != null) {
				CacheValue cvalue = (CacheValue) obj;
				return cvalue.getValue();
			}
		}
		return null;
	}

	/**
	 * 默认缓存 30分钟
	 */
	public Object put(String key, Object value) {
		return put(key, value, DEFAULT_TIMEOUT);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            单位默认是秒,传入小于0的值则使用默认值(30分钟)，传入0 则永久有效(应用重启后失效,目前没有实现存盘)
	 * @return
	 */
	public Object put(String key, Object value, Long timeout) {
		if (key == null) {
			return null;
		}
		CacheValue cvalue = new CacheValue();
		cvalue.setKey(key);
		if (timeout > 0) {
			cvalue.setTimeout(timeout * 1000L);
		} else if (timeout < 0) {
			cvalue.setTimeout(DEFAULT_TIMEOUT);
		} else {
			cvalue.setTimeout(0L);
		}
		cvalue.setValue(value);
		return cache.put(key, cvalue);
	}

	class CacheValue implements Serializable {
		private static final long serialVersionUID = 800800964682488492L;
		private String key;
		private Long createTime;
		private Long timeout;
		private Object value;

		public CacheValue() {
			createTime = System.currentTimeMillis() - 100;// 将创建时间提前100毫秒，防止定时回收任务因上一次执行时间过长导致回收滞后
		}

		/**
		 * 判断对象是否已经失效
		 * 
		 * @return 返回 true 失效 false 没有失效
		 */
		public boolean isTimeout() {
			boolean ret = true;
			if (value != null) {
				if (timeout == 0) {
					return false;
				} else {
					ret = System.currentTimeMillis() - createTime >= timeout;
					if (ret) {
						value = null;
					}
				}
			}
			return ret;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Long getCreateTime() {
			return createTime;
		}

		public Long getTimeout() {
			return timeout;
		}

		public void setTimeout(Long timeout) {
			this.timeout = timeout;
		}

		public Object getValue() {
			isTimeout();
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}

	public void remove(String key) {
		cache.remove(key);
	}

	public long size() {
		return cache.size();
	}
	
	public static void main(String[] args) {
		LocalCache localCache = LocalCache.createLocalCache();
		localCache.put("xsw", "15013190462");
		LocalCache localCache2 = LocalCache.createLocalCache();
		String rsp = localCache2.get("xsw").toString();
		log.info(rsp);
	}
}