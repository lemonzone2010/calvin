package com.apusic.md.emarket.dao;

import java.util.List;

import com.apusic.md.model.emarket.ProductImage;

public interface ProductImageDao {

	List<ProductImage> getProductImagesByProductId(Integer id);

	ProductImage getOneProductImageByProductId(Integer id);

}
