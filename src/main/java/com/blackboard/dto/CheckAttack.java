package com.blackboard.dto;

public class CheckAttack {

	private String timestamp;//时间搓
	private String nonce;//随机字符串
	private String sign;//签名后的数据

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "CheckAttack [timestamp=" + timestamp + ", nonce=" + nonce + ", sign=" + sign + "]";
	}

}
