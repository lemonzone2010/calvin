package com.apusic.md.model.emarket;

import com.apusic.ebiz.model.BaseModel;

/**
 * 广告
 * @author xuzhengping
 *
 */
public class Advertisement extends BaseModel {


	private AdvertisementPosition position;// 广告放置的位置

	private String name;//广告标题

	private String imagePath;//图片路径

	private String url;//访问的URL

	private int serialNumber;//排序号



	public AdvertisementPosition getPosition() {
		return position;
	}

	public void setPosition(AdvertisementPosition position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
}
