package com.blackboard.utils;

//import com.github.tobato.fastdfs.domain.StorePath;
//import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 图片上传工具
 * Created by Administrator on 2017/8/9.
 */
public class UploadFile {

    /**
     *图片存储到分布式文件系统fastdfs上
     * @param request
     * @return
     *//*
    public static List<String> upload(HttpServletRequest request,FastFileStorageClient fastFileStorageClient) {
        List<String> list = new ArrayList<String>();
        InputStream input = null;
        String subName = null;
        try {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                subName = part.getSubmittedFileName();
                if (isImageFile(subName)) {
                    input = part.getInputStream();
                    String strs = part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf(".") + 1);
                    StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage((FileInputStream) input, part.getSize(), strs, null);
                    String urls = storePath.getFullPath();
                    list.add(storePath.getFullPath()+","+subName);
                }
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return list;
    }*/
    /**
     * 原文件上传存储方式
     * @param request
     * @return
     */
    public static Map<String,Object> imageUpload(HttpServletRequest request){
        Logger logger = LoggerFactory.getLogger(UploadFile.class);
        Map<String,Object> r = new HashMap<>();
        StringBuilder oldNames = new StringBuilder();
        StringBuilder newNames = new StringBuilder();
        //flag:0 上传成功 1 未选择图片 2 图片超出最大限制5MB 3 只能选择图片
        r.put("flag",0);
        //获取系统目录
        String userDir = System.getProperty("user.dir");
        //保存路径
        String savePath = "/colleagueImg/upload"+getDirname();
        //判断是否有文件
        //文件数
        int count = 0;
        try {
            Collection<Part> parts = request.getParts();
            File dirs = new File(userDir+savePath);
            if (!dirs.exists() && !dirs.isDirectory()){
                logger.info("目录不存在，创建");
                dirs.mkdirs();
            }else {
                logger.info("目录已存在，不创建");
            }
            InputStream in = null;
            OutputStream out = null;
            for (Part part:parts){
                //原文件（图片）名
                String subName = part.getSubmittedFileName();
                if (subName != null){
                    //文件是否大于5MB
                    if (part.getSize()>5*1024*1024){
                        r.put("msg","请选择小于5MB的图片");
                        r.put("flag",2);
                        return r;
                    }
                    count++;
                    in = part.getInputStream();
                    //判断是否为图片
                    if (subName != null && !"".equalsIgnoreCase(subName.trim())&&isImageFile(subName)){
                        //自定义图片名
                        String fileName = getUUID()+getFileType(subName);
                        out = new FileOutputStream(userDir+savePath+fileName);
                        IOUtils.copy(in,out);
                        oldNames.append(count == 1 ?subName:","+subName);
                        newNames.append(count == 1 ?fileName:","+fileName);
                    }else {
                        r.put("flag",3);
                        r.put("msg","只能上传图片");
                        return r;
                    }
                }
            }
            if (out != null){
                out.close();
            }
            if (in != null){
                in.close();
            }
            if (count == 0){
                r.put("flag",1);
                r.put("msg","请选择上传的图片");
                return r;
            }
            //设置返回目录
            r.put("dir",savePath.substring(1,savePath.length()));
            //原图片名
            r.put("oldNames",oldNames);
            //自定义图片名
            r.put("newNames",newNames);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return r;
    }
    /**
     * 生成日期目录
     * @param
     * @return
     */
    public static String getDirname()
    {
        SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyy-MM-dd");
        String dataTime = sdfYmd.format(System.currentTimeMillis());
        String year=dataTime.substring(0,4);
        String month=dataTime.substring(5,7);
        String date=dataTime.substring(8,10);
        return "/"+year+"/"+month+"/"+date+"/";
    }

    /**
     * 生成UUID随机数
     * @return
     */
    public static String getUUID(){
        String str = UUID.randomUUID().toString();
        return str.replace("-","");
    }
    /**
     * 获取文件名后缀
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName){
        if(fileName != null && fileName.indexOf('.') >= 0){
            return fileName.substring(fileName.lastIndexOf('.'),fileName.length());
        }
        return "";
    }

    /**
     * 判断文件是否为图片
     * @param fileName
     * @return
     */
    public static boolean isImageFile(String fileName){
        String[] imgType = {".jpg",".jpeg",".png",".gif",".bmp"};
        if (fileName != null){
            fileName = fileName.toLowerCase();
            for (String t:imgType){
                if (fileName.endsWith(t)){
                    return true;
                }
            }
        }
        return false;
    }
}
