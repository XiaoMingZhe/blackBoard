package com.blackboard.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.w3c.dom.Document;

public class WebServicesClient {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** 
     * 对服务器端返回的XML文件流进行解析 
     *  
     * @param 参数
     * 
     * @return 
     */  
    public String getWeather(Map<String, Object> map) {  
        try {  
            // 获取调用接口后返回的流  
            InputStreamReader is = getSoapInputStream(map);  
            String result = "";
            char[] buffer = new char[1024]; 
            int len = 0; 
            while ((len = is.read(buffer)) > 0) { 
            result += new String(buffer, 0, len); 
            }
            if (result != null && !"".equals(result)) { 
            	int beginIndex = result.indexOf("ns2:resultdesc"); 
            	int endIndex = result.indexOf("</ns2:resultdesc>"); 
            	result = result.substring(beginIndex + 55, endIndex); 
            	} 
              
            is.close();  
            return result;  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error("发送消息送审失败!");
            return null;  
        }  
    }  
  
    /* 
     * 用户把SOAP请求发送给服务器端，并返回服务器点返回的输入流 
     *  
     * @param city 用户输入的城市名称 
     *  
     * @return 服务器端返回的输入流，供客户端读取 
     *  
     * @throws Exception 
     *  
     * @备注：有四种请求头格式1、SOAP 1.1； 2、SOAP 1.2 ； 3、HTTP GET； 4、HTTP POST 
     * 参考---》http:// 
     * www.webxml.com.cn/WebServices/WeatherWebService.asmx?op=getWeatherbyCityName 
     */  
    private InputStreamReader getSoapInputStream(Map<String,Object> map) throws Exception {  
        try {  
            // 获取请求规范  
            String soap = getSoapRequest(map);  
            if (soap == null) {  
                return null;  
            }  
            // 调用的webserviceURL  
            URL url = new URL(  
                    "http://218.205.115.242:18101/Ercs_Proxy/ExtendedBusinessPlatformService/SendMsgAuthenticationReq");  
            HttpURLConnection  conn = (HttpURLConnection) url.openConnection();  
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);  
            conn.setDoOutput(true);  
            conn.setRequestProperty("Content-Length",  
                    Integer.toString(soap.length()));  
            conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");  
            // 调用的接口方法是  
            conn.setRequestProperty("SOAPAction",  
                    "SendMsgAuthenticationReq");  
            OutputStream os = conn.getOutputStream();  
            
            OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");  
            osw.write(soap);  
            osw.flush();  
            osw.close();  
            // 获取webserivce返回的流  
            InputStream is = conn.getInputStream(); 
            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
            int responseCode = conn.getResponseCode(); 
            logger.info("返回码为"+responseCode); 
            logger.info("return " + is.available());
            return isr;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /* 
     * 获取SOAP的请求头，并替换其中的标志符号为用户输入的城市 
     *  
     * @param city： 用户输入的城市名称 
     *  
     * @return 客户将要发送给服务器的SOAP请求规范 
     *  
     * @备注：有四种请求头格式1、SOAP 1.1； 2、SOAP 1.2 ； 3、HTTP GET； 4、HTTP POST 
     * 参考---》http:// 
     *  
     * 本文采用：SOAP 1.1格式 
     */  
    private String getSoapRequest(Map<String,Object> map) {  
    	Date day=new Date();    
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
    	String timestamp = df.format(day);  
        StringBuffer sb = new StringBuffer(); 
        //获取参数
        String mobile = (String) map.get("mobile");
        String msgid = (String) map.get("msgid");
        logger.info("msgid:"+msgid);
        String content = (String) map.get("content");
        
        System.out.println(MD5(content));
        
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chin=\"http://www.chinamobile.com/\">");  
        sb.append("   <soapenv:Header>");
        sb.append("   	<chin:authenticatorcode>"+SHA256.getSHA256StrJava(msgid+"88"+timestamp)+"</chin:authenticatorcode>");
        sb.append("  	<chin:timestamp>"+timestamp+"</chin:timestamp>");
        sb.append("   	<chin:receiveraddress>blackboard</chin:receiveraddress>");
        sb.append("  	<chin:senderaddress>blackboard</chin:senderaddress>");
        sb.append("   	<chin:version>1.0.0</chin:version>");
        sb.append("   	<chin:msgid>"+msgid+"</chin:msgid>");
        sb.append("   	<chin:msgname>H5黑板报安全送审</chin:msgname>");
        sb.append("   </soapenv:Header>");
        sb.append("   <soapenv:Body>");  
        sb.append("      <chin:exPstReq>");  
        sb.append("         <msgid>"+msgid+"</msgid>");  
        sb.append("         <userid>"+mobile+"</userid>");  
        sb.append("         <date>"+timestamp+"</date>"); 
        sb.append("         <hash>"+MD5(content)+"</hash>"); 
        sb.append("         <contenttype>1</contenttype>"); 
        try {
			sb.append("         <contentlength>"+content.getBytes("utf-8").length+"</contentlength>");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
        sb.append("         <content>"+content+"</content>"); 
        sb.append("         <fileposition/>"); 
        sb.append("         <svctype>16</svctype>"); 
        sb.append("         <platfrom>10</platfrom>"); 
        sb.append("      </chin:exPstReq>");  
        sb.append("   </soapenv:Body>");  
        sb.append("</soapenv:Envelope>");  
  
  
        return sb.toString();  
    } 
    
    
    private String MD5(String content){
    	String md5 = DigestUtils.md5DigestAsHex(content.getBytes());
    	StringBuilder hash = new StringBuilder("md5");
    	for(int i = 0;i<md5.length()/2;i++){
    		hash.append(":"+md5.substring(2*i,2*i+2).toUpperCase());
    	}
    	return hash.toString();
    }
}
