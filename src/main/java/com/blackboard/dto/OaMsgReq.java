package com.blackboard.dto;

public class OaMsgReq {

	private String address;
	private String destAddress;
	private String senderAddress;
	private String imFormat;
	private String contentType;
	private String shortMessageSupported;
	private String storeSupported;
	private String receiptRequest;
	private String bodyText;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getImFormat() {
		return imFormat;
	}

	public void setImFormat(String imFormat) {
		this.imFormat = imFormat;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getShortMessageSupported() {
		return shortMessageSupported;
	}

	public void setShortMessageSupported(String shortMessageSupported) {
		this.shortMessageSupported = shortMessageSupported;
	}

	public String getStoreSupported() {
		return storeSupported;
	}

	public void setStoreSupported(String storeSupported) {
		this.storeSupported = storeSupported;
	}

	public String getReceiptRequest() {
		return receiptRequest;
	}

	public void setReceiptRequest(String receiptRequest) {
		this.receiptRequest = receiptRequest;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

}
