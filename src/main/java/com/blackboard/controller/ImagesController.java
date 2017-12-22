package com.blackboard.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@RequestMapping(value="addimages",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult upLoadImages (HttpServletRequest request , @RequestParam(value="img", required = false)List<MultipartFile> images){
		
		
		if(images == null || images.size()<=0){
			return JsonResult.error("请求参数非法");
		}
		
		String serverPath = request.getServletContext().getRealPath("/");
		String Path = request.getContextPath(); 
		
		List<Map<String,Object>> list = imageService.addImage(images, serverPath,Path);
		
		return JsonResult.ok().put("list", list);
	}

	
	@RequestMapping(value="deleteImage",method = RequestMethod.GET)
	@ResponseBody
	public JsonResult deleteImage(HttpServletRequest request ,String imageId){
		
		System.out.println(imageId);
		if(imageId == null || imageId.length()<=0){
			return JsonResult.error("请求参数非法");
		}
		
		imageService.deleteImage(imageId);
		
		return JsonResult.ok();
	}
}
