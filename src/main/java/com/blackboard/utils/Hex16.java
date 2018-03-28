package com.blackboard.utils;

public class Hex16 {

	/**
	 * 字符串转换为16进制字符串
	 * @param strEncode
	 * @return
	 */
	public static String Encode(String strEncode)
    {
		String str = "";  
        for (int i = 0; i < strEncode.length(); i++) {  
            int ch = (int) strEncode.charAt(i);  
            String s4 = Integer.toHexString(ch);  
            str = str + s4;  
        }  
        return str; 
    }
	
	/** 
     * 16进制字符串转换为字符串 
     *  
     * @param s 
     * @return 
     */  
    public static String hexStringToString(String s) {  
        if (s == null || s.equals("")) {  
            return null;  
        }  
        s = s.replace(" ", "");  
        byte[] baKeyword = new byte[s.length() / 2];  
        for (int i = 0; i < baKeyword.length; i++) {  
            try {  
                baKeyword[i] = (byte) (0xff & Integer.parseInt(  
                        s.substring(i * 2, i * 2 + 2), 16));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        try {  
            s = new String(baKeyword, "gbk");  
            new String();  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
        return s;  
    }  
}
