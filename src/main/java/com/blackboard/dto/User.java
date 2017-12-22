package com.blackboard.dto;

public class User {

	private LCompanyUser lcompanyUser;

	private Long cUId;// 用户ID
	private String cUMobile;// 手机号
	private String cUName;// 用户名
	private String enterpriseId;// 和通讯录企业ID

	public Long getcUId() {
		return cUId;
	}

	public void setcUId(Long cUId) {
		this.cUId = cUId;
	}

	public String getcUMobile() {
		return cUMobile;
	}

	public void setcUMobile(String cUMobile) {
		this.cUMobile = cUMobile;
	}

	public String getcUName() {
		return cUName;
	}

	public void setcUName(String cUName) {
		this.cUName = cUName;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public LCompanyUser getLcompanyUser() {
		return lcompanyUser;
	}

	public void setLcompanyUser(LCompanyUser lcompanyUser) {
		this.lcompanyUser = lcompanyUser;
	}

	@Override
	public String toString() {
		return "User [lcompanyUser=" + lcompanyUser + ", cUId=" + cUId + ", cUMobile=" + cUMobile + ", cUName=" + cUName
				+ ", enterpriseId=" + enterpriseId + "]";
	}

}
