package com.blackboard.dto;

/**
 * 
* Title: RcsToken
* Description:   
* @author Xmz  
* @date 2018年5月25日
 */
public class RcsToken {
	private String token;
	private String enterId;
	private String enterName;
	private String contactId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEnterId() {
		return enterId;
	}

	public void setEnterId(String enterId) {
		this.enterId = enterId;
	}

	public String getEnterName() {
		return enterName;
	}

	public void setEnterName(String enterName) {
		this.enterName = enterName;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
}
