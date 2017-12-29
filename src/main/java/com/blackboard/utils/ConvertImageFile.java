package com.blackboard.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 将png图片转换成jpg格式的图片
 * @author Administrator
 *
 */
public class ConvertImageFile {
	
	public void reduce(String oldFile,String newFile) {

		BufferedImage bufferedImage;

		try {

			// read image file
			bufferedImage = ImageIO.read(new File(oldFile));

			// create a blank, RGB, same width and height, and a white
			// background
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			// TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

			// write to jpeg file
			ImageIO.write(newBufferedImage, "jpg", new File(newFile));

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
}
