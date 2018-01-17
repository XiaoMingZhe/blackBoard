package com.blackboard.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blackboard.dao.ImageDao;
import com.blackboard.dto.UploadException;
import com.blackboard.entity.Image;
import com.blackboard.service.ImageService;
import com.blackboard.utils.GainUuid;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageDao imageDao;

	/**
	 * 添加图片
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param blackboardId
	 *            黑板报ID
	 * @param images
	 *            图片文件数组对象
	 * @param serverPath
	 *            服务器路径
	 * @return imageId 图片ID
	 */
	@Override
	public List<Map<String, Object>> addImage(List<MultipartFile> images, String serverPath, String Path) {
		List<Image> imageList = new ArrayList<Image>();
		// Image image = new Image();
		for (int i = 0; i < images.size(); i++) {
			MultipartFile file = images.get(i);
			// 构建新的文件名
			String oFileName = file.getOriginalFilename();

			// 临时文件存放绝对路径
			String realPath = serverPath + "uploadImages/" + oFileName;

			System.out.println(realPath);
			File dest = new File(realPath);
			File parent = dest.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			try {
				file.transferTo(dest);
			} catch (Exception e) {
				e.printStackTrace();
				throw new UploadException("上传图片失败！");
			}

			// 相对路径
			String relativePath = Path + "/uploadImages/" + oFileName;

			// 设置图片信息
			Image image = new Image();
			image.setImageId(GainUuid.getUUID());
			image.setImagePath(relativePath);
			image.setImageName(oFileName);
			image.setOrderNumber(i);
			image.setUploadDate(new Date());
			System.out.println("****Image:" + image);
			imageList.add(image);
		}

		List<Map<String, Object>> list = new ArrayList<>();
		imageDao.saveImage(imageList);

		for (Image i : imageList) {
			Map<String, Object> map = new HashMap<>();
			map.put("imagePath", i.getImagePath());
			map.put("imageId", i.getImageId());
			list.add(map);
		}

		return list;
	}

	/**
	 * 展示当前黑板报所有图片
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param blackboradId
	 *            黑板报ID
	 * @return List<Image> 图片列表
	 */
	@Override
	public List<Image> getBlackboardImage(String enterpriseId, String blackboradId) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("enterpriseId", enterpriseId);
		parmMap.put("blackboardId", blackboradId);

		List<Image> images = imageDao.getBlackboardImage(parmMap);

		return images;
	}

	/**
	 * 删除当前黑板报所有图片
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param blackboradId
	 *            黑板报ID
	 */
	@Override
	public void deleteBlackboardImage(String enterpriseId, String blackboradId) {

		List<Image> images = getBlackboardImage(enterpriseId, blackboradId);

		for (Image lists : images) {
			String realPath = lists.getImagePath();
			File imgFile = new File(realPath);
			if (imgFile.exists()) {
				imgFile.delete();
			}
		}

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("enterpriseId", enterpriseId);
		parmMap.put("blackboardId", blackboradId);

		imageDao.deleteBlackboardImage(parmMap);
	}

	/**
	 * 更新图片
	 * 
	 * @param enterpriseId
	 *            企业ID
	 * @param blackboradId
	 *            黑板报ID
	 * @param images
	 *            图片文件数组对象
	 * @param serverPath
	 *            服务器路径
	 * @return true/false 修改结果
	 */
	@Override
	public Boolean updateBlackboardImage(String enterpriseId, String blackboradId, MultipartFile[] images,
			String serverPath) {

		deleteBlackboardImage(enterpriseId, blackboradId);

		// addImage(images, serverPath);

		return true;
	}

	@Override
	public void deleteImage(String imageId) {

		imageDao.deleteImage(imageId);

	}

}
