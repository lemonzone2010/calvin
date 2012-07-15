package com.apusic.md.emarket.service;

import java.util.List;

import com.apusic.md.model.emarket.ProductImage;

public interface ProductImageService {

	List<ProductImage> getProductImagesByProductId(Integer id);

	ProductImage getOneProductImageByProductId(Integer id);
	
	void deletePictureFile(String parameterPictureName);
	
	void deleteDataBasePictureFile(ProductImage productImage);
}
