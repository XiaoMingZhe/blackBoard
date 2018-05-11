package com.blackboard.service.impl;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.blackboard.dto.ActMsg;
import com.blackboard.dto.ExtNotic;
import com.blackboard.dto.ExtensionInfo;
import com.blackboard.dto.OaMsgReq;
import com.blackboard.service.OaMsgPushService;
import com.blackboard.utils.Base64;
import com.blackboard.utils.HttpHelper;
import com.blackboard.utils.LocalCache;
import com.blackboard.utils.OaTokenJob;
import com.blackboard.utils.PropertiesUtils;

@Service
public class OaMsgPushImpl implements OaMsgPushService {
	
	
	private static final String ACCESS_NO = PropertiesUtils.getProperties("ACCESS_NO");
	private static final String APP_ID = PropertiesUtils.getProperties("APP_ID");
	private static final String OA_MSG_PUSH_URL = PropertiesUtils.getProperties("OA_MSG_PUSH_URL");
	private static final String BLACKBOARD_URL = PropertiesUtils.getProperties("BLACKBOARD_URL");
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取OA消息推送请求报文
	 * @param req
	 * @return
	 */
	private String getOaReqXml(OaMsgReq req) {
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<msg:outboundMessageRequest xmlns:msg=\"urn:oma:xml:rest:netapi:messaging:1\">");
		xml.append("<address>"+req.getAddress()+"</address>");
		xml.append("<destAddress>"+req.getDestAddress()+"</destAddress>");
		xml.append("<senderAddress>"+req.getSenderAddress()+"</senderAddress>");
		xml.append("<outboundIMMessage>");
		xml.append("<imFormat>"+req.getImFormat()+"</imFormat>");
		xml.append("<contentType>"+req.getContentType()+"</contentType>");
		xml.append("<shortMessageSupported>"+req.getShortMessageSupported()+"</shortMessageSupported>");
		xml.append("<storeSupported>"+req.getStoreSupported()+"</storeSupported>");
		xml.append("<receiptRequest>"+req.getReceiptRequest()+"</receiptRequest>");
		xml.append("<bodyText>");
		xml.append(req.getBodyText());
		xml.append("</bodyText>");
		xml.append("</outboundIMMessage>");
		xml.append("</msg:outboundMessageRequest>");
		return xml.toString();
	}
	
	/**
	 * 获取消息推送请求BODY XML
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	private String getMsgBodyXml(ExtNotic ext) throws Exception {
		String link = URLEncoder.encode(ext.getExtensionInfo().getActMsg().getThumbLink(),"UTF-8");
		StringBuffer xml = new StringBuffer("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		xml.append("<commontemplate xmlns=\"urn:ietf:params:xml:ns:templateInfo\" xmlns:xsi=\"http://www..w3.org/2001/XMLSchema-instance\" xmlns:cp=\"urn:ietf:params:xml:ns:capacity\">");		
		xml.append("<extensionType>"+ext.getExtensionType()+"</extensionType>");
		xml.append("<extensionVer>"+ext.getExtensionVer()+"</extensionVer>");
		xml.append("<extensionInfo>");
		xml.append("<Forwardable>"+ext.getExtensionInfo().getForwardable()+"</Forwardable>");
		xml.append("<AccessNo>"+ext.getExtensionInfo().getAccessNo()+"</AccessNo>");
		xml.append("<DisplayMode>"+ext.getExtensionInfo().getDisplayMode()+"</DisplayMode>");
		xml.append("<ActMsg>");
		xml.append("<Version>"+ext.getExtensionInfo().getActMsg().getVersion()+"</Version>");
		xml.append("<Title>"+ext.getExtensionInfo().getActMsg().getTitle()+"</Title>");
		xml.append("<ThumbLink>"+link+"</ThumbLink>");
		xml.append("<Summary>"+ext.getExtensionInfo().getActMsg().getSummary()+"</Summary>");
		xml.append("<Timestamp>"+ext.getExtensionInfo().getActMsg().getTimestamp()+"</Timestamp>");
		xml.append("<MediaType>"+ext.getExtensionInfo().getActMsg().getMediaType()+"</MediaType>");
		xml.append("<Text>"+ext.getExtensionInfo().getActMsg().getText()+"</Text>");
		xml.append("</ActMsg>");
		xml.append("</extensionInfo>");
		xml.append("</commontemplate>]]>");
		return xml.toString();
	}

	@Override
	public void pushMsg(Map<String, Object> msg) throws Exception {
		ExtNotic ext = new ExtNotic();
		ext.setExtensionType("ActiveMessage");
		ext.setExtensionVer("v1.0");
		
		ExtensionInfo exinfo = new ExtensionInfo();
		exinfo.setForwardable("0");
		exinfo.setAccessNo(ACCESS_NO);
		exinfo.setDisplayMode("0");
		
		ActMsg am = new ActMsg();
		am.setVersion("v1.0");
		am.setTitle((String) msg.get("Title"));
		am.setSummary((String)msg.get("Connent"));
		am.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		am.setMediaType("text");
		am.setText("黑板报");
		
		exinfo.setActMsg(am);
		ext.setExtensionInfo(exinfo);
		
		//接口凭证
		String access_token = "";
		access_token = new OaTokenJob().getMsgToken();
	
		//接口的授权码
		String Authorization = "Bearer "+Base64.encode((APP_ID+":"+access_token)).replaceAll("[\\s*\t\n\r]", "");
		
		List<String> mobileList = (List<String>) msg.get("mobile");
		//接口不支持批量只能一个个推送
		for(String s : mobileList) {
			String phone = s;
			//替换参数
			ext.getExtensionInfo().getActMsg().setThumbLink("http://"+BLACKBOARD_URL+"/toindex#/index/dynamic/"+msg.get("blackboardId")+"/");
			System.out.println(ext.getExtensionInfo().getActMsg().getThumbLink());
			OaMsgReq req = new OaMsgReq();
			req.setAddress("tel:+86"+phone);
			req.setDestAddress("tel:+86"+phone);
			req.setSenderAddress("tel:"+ACCESS_NO);
			req.setImFormat("IM");
			req.setBodyText(this.getMsgBodyXml(ext));
			req.setReceiptRequest("false");
			req.setContentType("application/commontemplate+xml");
			req.setShortMessageSupported("false");
			req.setStoreSupported("true");
			String xml = this.getOaReqXml(req);
			xml = xml.replaceAll("\r|\n", "");
			log.info("请求报文："+xml);
			String url = OA_MSG_PUSH_URL.replace("${accNbr}", ACCESS_NO);
			log.info("请求URL： "+url);
			System.out.println(Authorization);
			JSONObject resp = HttpHelper.Msgpush(url,xml,"tel:+86"+phone,Authorization);
			log.info("接口返回  :"+resp);
		}
	}
}
