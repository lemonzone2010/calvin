package com.apusic.md.emarket.service;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.emarket.ProductTemplate;

@Service("emarket_ProductTemplateService")
public class ProductTemplateServiceImpl implements ProductTemplateService {

	@Autowired
	private CrudService crudService;

	@Autowired
	private QueryService queryService;

	@Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;

	@Transactional
	public void addProductTemplate(ProductTemplate productTemplate) {
		this.crudService.create(productTemplate);
	}

	@Transactional
	public void deleteProductTemplate(int id) {
		this.crudService.delete(ProductTemplate.class, id);
	}

	@Transactional(readOnly=true)
	public Page<ProductTemplate> findProductTemplate4Page(
			GenericQueryObject query) {
	    query.getDetachedCriteria().addOrder(Order.desc("createTime"));
		return queryService.findPageBy(query
				.getDetachedCriteria(), configService
				.getIntegerValueByKey("pageSize"));
	}

	public ProductTemplate getProductTemplate(int id) {
		return this.crudService.retrieve(ProductTemplate.class, id);
	}

	@Transactional
	public void updateProductTemplate(ProductTemplate productTemplate) {
		this.crudService.update(productTemplate);
	}

}
