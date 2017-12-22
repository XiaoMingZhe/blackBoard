package com.blackboard.entity;

import java.util.Date;

/**
 * 图片
 * 
 * @author Administrator
 *
 */
public class Image {

	
	private String imageId;//图片ID
	
	private String blackboardId;//对应黑板报ID
	
	private String imagePath;//图片上传地址
	
	private String imageName;//图片名称
	
	private Integer orderNumber;//上传顺序
	
	private Date uploadDate;//上传时间
	
	private String enterpriseId; //企业ID

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getBlackboardId() {
		return blackboardId;
	}

	public void setBlackboardId(String blackboardId) {
		this.blackboardId = blackboardId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Override
	public String toString() {
		return "Image [imageId=" + imageId + ", blackboardId=" + blackboardId
				+ ", imagePath=" + imagePath + ", imageName=" + imageName
				+ ", orderNumber=" + orderNumber + ", uploadDate=" + uploadDate
				+ ", enterpriseId=" + enterpriseId + "]";
	}

	
}
