package com.blackboard.entity;

import java.util.Date;

/**
 * 黑板单实体类
 * @author luzhenxing
 *
 */
public class BlackList {
	
	private String blackltId; //黑名单ID
	private String blcaklterId; //拉黑人ID
	private String beBlcaklterIdString; //被拉黑人ID
	private Date createTime; //创建时间
	
	
	public String getBlackltId() {
		return blackltId;
	}
	public void setBlackltId(String blackltId) {
		this.blackltId = blackltId;
	}
	public String getBlcaklterId() {
		return blcaklterId;
	}
	public void setBlcaklterId(String blcaklterId) {
		this.blcaklterId = blcaklterId;
	}
	public String getBeBlcaklterIdString() {
		return beBlcaklterIdString;
	}
	public void setBeBlcaklterIdString(String beBlcaklterIdString) {
		this.beBlcaklterIdString = beBlcaklterIdString;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
