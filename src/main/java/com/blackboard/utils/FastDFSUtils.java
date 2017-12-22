package com.blackboard.utils;
/*package com.soecode.lyf.utils;


import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*//**
 * @author 张富强
 * @create 2017-09-22 14:28
 **//*
public class FastDFSUtils {

    *//**
     * 无参构造初期化信息
     * @throws Exception
     *//*


    public   List<Map<String,String>> upload_file( String fastdfsServerUrl , List<MultipartFile> multipartFiles) throws Exception{
        *//** 设置加载的配置文件路径 *//*
        String conf_file = this.getClass().getResource("/fastdfs_client.conf").getPath();
        *//** 初始化客户端全局信息 *//*
        ClientGlobal.init(conf_file);
        List<Map<String,String>> fastDFS_returns = new ArrayList<>();
        //遍历
        for (int i = 0; i < multipartFiles.size(); i++) {
            //获取multipartFile 对象
            MultipartFile multipartFile = multipartFiles.get(i);

            String fileExtensionName = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            if( !this.isImageFile(fileExtensionName)){
                throw new RcsException("上传文件不是图片");
            }
            *//**
             * 创建存储客户端对象
             * 第一个参数：追踪服务对象(trackerServer) 可选
             * 第二个参数：存储服务对象(storageServer) 可选
             * *//*
            StorageClient storageClient = new StorageClient();
            *//** 上传文件到fastdfs分布式文件系统 *//*
            String[] upload_file = storageClient.upload_file(multipartFile.getBytes(), fileExtensionName, null);
            // http://192.168.12.128/group1/M00/00/00/wKiwgFjzhzWAP-diAAA9pCqpLB0086.jpg
            *//**
             * 数组中第一个元素: group组的名称
             * 数组中第二个元素: 图片存储文件路径
             *//*
            StringBuilder url = new StringBuilder(fastdfsServerUrl);
            StringBuilder fastDFS_path = new StringBuilder();
            //拼接图片地址
            for (String str : upload_file) {
                //访问路径
                url.append( "/"+ str );
                //fastDFS 存储分类与路径
                fastDFS_path.append(str).append(",");
            }
            Map<String ,String > params = new HashMap<>();
            //访问的路径
            params.put("url_path",url.toString());
            //存储的路径
            params.put("save_path",fastDFS_path.toString());
            //文件原名
            params.put("original_name",multipartFile.getOriginalFilename());
            //添加集合
            fastDFS_returns.add(params);

        }
        //返回
        return fastDFS_returns;
    }

    *//**
     * 判断文件是否为图片
     * @param  fileExtensionName 文件后缀名
     * @return
     *//*
    private  boolean isImageFile(String fileExtensionName){
        String[] imgType = {"jpg","jpeg","png","gif","bmp"};
        if (fileExtensionName != null){
            fileExtensionName = fileExtensionName.toLowerCase();
            for (String t:imgType){
                if (fileExtensionName.equals(t)){
                    return true;
                }
            }
        }
        return false;
    }

}
*/