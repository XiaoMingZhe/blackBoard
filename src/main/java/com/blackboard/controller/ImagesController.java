package com.blackboard.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.blackboard.service.ImageService;
import com.blackboard.utils.JsonResult;

@RequestMapping("images")
@Controller
public class ImagesController {
	
	@Autowired
	private ImageService imageService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 *  添加图片
	 * @param request
	 * @param images
	 * @return
	 */
	@RequestMapping(value="/addimages",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult upLoadImages (HttpServletRequest request , @RequestParam(value="img", required = false)List<MultipartFile> images){
		
		
		logger.info("=========上传图片开始=========");
		logger.info("=========图片集合长度========="+images.size());
		for (int i = 0; i < images.size(); i++) {
			MultipartFile file = images.get(i);
			Long upLoadSize = file.getSize();
			if(upLoadSize>5000000L){
				return JsonResult.error("第"+(i+1)+"个文件过大");
			}
			String fileName = file.getOriginalFilename();
			logger.info("==========文件名称为:"+fileName);
			if(!checkFile(fileName)){
				return JsonResult.error("第"+(i+1)+"个文件类型错误");
			}
		}
		
		
		if(images == null || images.size()<=0){
			return JsonResult.error("请求参数非法");
		}
		
		String serverPath = request.getServletContext().getRealPath("/");
		String Path = request.getContextPath(); 
		
		List<Map<String,Object>> list = imageService.addImage(images, serverPath,Path);
		
		return JsonResult.ok().put("list", list);
	}


	/**
	 * 删除图片
	 * @param request
	 * @param imageId
	 * @return
	 */
	@RequestMapping(value="/deleteImage",method = RequestMethod.GET)
	@ResponseBody
	public JsonResult deleteImage(HttpServletRequest request ,String imageId){
		
		System.out.println(imageId);
		if(imageId == null || imageId.length()<=0){
			return JsonResult.error("请求参数非法");
		}
		
		imageService.deleteImage(imageId);
		
		return JsonResult.ok();
	}
	
	
	
	/**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    private boolean checkFile(String fileName) {
        //设置允许上传文件类型
        String suffixList = "jpg,gif,png,ico,bmp,jpeg";
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")
                + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }
}
