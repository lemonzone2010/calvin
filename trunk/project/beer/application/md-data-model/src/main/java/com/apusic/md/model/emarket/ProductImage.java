package com.apusic.md.model.emarket;

import com.apusic.ebiz.model.BaseModel;

public class ProductImage extends BaseModel implements Comparable<ProductImage> {


	private Product product;

	private String imagePath;

	private int serialNumber;



	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int compareTo(ProductImage o) {
		if (serialNumber>o.getSerialNumber()) {
			return 1;
		}else if (serialNumber==o.getSerialNumber()) {
			return 0;
		}
		return -1;
	}

}
