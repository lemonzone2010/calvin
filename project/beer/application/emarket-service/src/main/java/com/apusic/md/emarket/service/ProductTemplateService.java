package com.apusic.md.emarket.service;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.md.model.emarket.ProductTemplate;

public interface ProductTemplateService {
	void addProductTemplate(ProductTemplate productTemplate);

	void deleteProductTemplate(int id);

	void updateProductTemplate(ProductTemplate productTemplate);

	ProductTemplate getProductTemplate(int id);

	Page<ProductTemplate> findProductTemplate4Page(GenericQueryObject query);
	
}
