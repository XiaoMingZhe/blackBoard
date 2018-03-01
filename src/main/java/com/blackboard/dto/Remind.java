package com.blackboard.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Remind {

	private String title;//标题或图片
	private String userName;//用户名
	@JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
	private Date creatTime;//时间
	private String moblie;//用户手机号

}
