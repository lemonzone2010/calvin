package com.apusic.ebiz.framework.web.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;

public class ImageUtilTest {

	@Test
	public void scaleImageTest() throws IOException {
		String before = "target\\classes\\before.jpg";
		File f = new File(before);
		if (!f.exists()) {
			return;
		}
		ImageUtil.squareImage(before, before + "_square.jpg","jpg");
		File f2 = new File(before + "_square.jpg");
		if (!f2.exists()) {
			Assert.assertTrue(false);
		}
		BufferedImage img = ImageIO.read(f2);
		Assert.assertTrue(img.getHeight() == img.getWidth());

	}

}
