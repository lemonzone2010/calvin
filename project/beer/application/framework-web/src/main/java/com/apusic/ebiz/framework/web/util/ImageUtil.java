package com.apusic.ebiz.framework.web.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ImageUtil {

	public static final Log log = LogFactory.getLog(ImageUtil.class);


	private static BufferedImage adjustBorder(File file, Integer width,Integer height){
		BufferedImage src = null;
		try {
			src = javax.imageio.ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} // 构造Image对象

		if(width == null && height == null){
			return src;
		}
		int old_w = src.getWidth(null); // 得到源图宽
		int old_h = src.getHeight(null);
		if (width!=null) {
			old_w = width;
			old_h = height;
		}
		BufferedImage pic = new BufferedImage(old_w, old_h,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = pic.createGraphics();
		g.fillRect(0, 0, width, height);
		g.drawImage(src.getScaledInstance(old_w, old_h,Image.SCALE_SMOOTH), 0, 0, null);
		return pic;
	}

	// 图片跟据长宽留白，成一个正方形图。 得到bufferdimage
	private static BufferedImage squareImage(File file, Integer width) {
		BufferedImage src = null;
		try {

			src = javax.imageio.ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} // 构造Image对象

		int old_w = src.getWidth(null); // 得到源图宽
		int old_h = src.getHeight(null);
		if (width!=null) {
			old_w = width;
			old_h = width;
		}
		
		// double w2=(old_w*1.00)/(w*1.00);
		// double h2=(old_h*1.00)/(h*1.00);

		// 图片跟据长宽留白，成一个正方形图。
		BufferedImage oldpic;
		int max=old_w>old_h?old_w:old_h;
		oldpic = new BufferedImage(max, max,BufferedImage.TYPE_INT_RGB);

		Graphics2D g = oldpic.createGraphics();
		g.setColor(Color.white);

		if(old_w==old_h){
			g.drawImage(src.getScaledInstance(old_w, old_h,Image.SCALE_SMOOTH), 0, 0, null);
		}else{
			int abs=Math.abs(old_w - old_h);
			g.fillRect(0, 0, max, max);
			if(old_w>old_h){
				g.drawImage(src, 0, abs / 2, old_w, old_h, Color.white,null);
	        }else{
	        	g.drawImage(src, abs/2, 0, old_w, old_h, Color.white,null);
	        }

		}
		g.dispose();
		src = oldpic;
		// 图片调整为方形结束
		return src;
	}


	//仅仅转换为正方形
	public static boolean squareImage(String oldPath, String newPath,String type) {
		File file=new File(oldPath);
		if (!file.exists()) {
			return false;
		}
		Graphics g = null;
		try {
			BufferedImage srcImg = squareImage(file,null);
			g = srcImg.getGraphics();
			g.drawImage(srcImg, 0, 0, null);
			ImageIO.write(srcImg, type, new File(newPath));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			if (g != null) {
				g.dispose();
			}
		}
		return true;
	}


}
