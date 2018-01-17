package com.blackboard.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import com.blackboard.dto.CheckAttack;

/**
 * 重放攻击验证
 * @author xmz
 *
 */
public class CheckAttackUtil {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//时间验证，时间差
	private static final long TIMEDIFFERENCE = 180000L;
	
	public boolean checkattack(HttpServletRequest request,CheckAttack checkAttack){
		CheckAttack checkAttackSession = (CheckAttack)request.getSession().getAttribute("checkAttack");
	
		if(checkAttackSession==null){
			request.getSession().setAttribute("checkAttack", checkAttack);
			return true;
		}
		
		String sign = checkAttack.getSign();
		String timestamp = checkAttack.getTimestamp();
		String nonce = checkAttack.getNonce();
		
		//MDS加密
		byte[] bytes = null;
		try {
			bytes = (timestamp+nonce).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String checksign = DigestUtils.md5DigestAsHex(bytes);
		
		//判断参数有没有被中途篡改
		if(!sign.equals(checksign)){
			System.out.println(checksign);
			logger.info("=========参数中途被篡改=========");
			return false;
		}
		
		//判断有没有超时3分钟内
		Long times = new Date().getTime();
		if(times-Long.parseLong(timestamp)>TIMEDIFFERENCE){
			System.out.println(times);
			logger.info("=========时间超时=========");
			return false;
		}
		
		//判断随机数是否用过
		if(nonce.equals(checkAttackSession.getNonce())){
			logger.info("=========同一个随机数上次已使用=========");
			return false;
		}
		
		//判断通过，覆盖新的验证
		request.getSession().setAttribute("checkAttack", checkAttack);
		return true;
	}
}
