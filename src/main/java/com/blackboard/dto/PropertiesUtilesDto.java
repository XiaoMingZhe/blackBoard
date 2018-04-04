package com.blackboard.dto;

public class PropertiesUtilesDto {
	private static final String APP_ID = "78";
	private static final String APP_PASSWORD = "OxQ7164W3957a6s2xS58nQ5f88DqGt1P";
	private static final String GRANT_TYPE = "client_credentials";
	private static final String OA_MSG_TOKEN_UR = "http://117.136.180.220:8080/services/OauthAPIV1/token";
	
	public static String getAppId() {
		return APP_ID;
	}
	public static String getAppPassword() {
		return APP_PASSWORD;
	}
	public static String getGrantType() {
		return GRANT_TYPE;
	}
	public static String getOaMsgTokenUr() {
		return OA_MSG_TOKEN_UR;
	} 

	
	
}
