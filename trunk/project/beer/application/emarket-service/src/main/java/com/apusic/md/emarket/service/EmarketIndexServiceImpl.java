package com.apusic.md.emarket.service;

import java.io.Serializable;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.fulltextsearch.IndexService;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductStateType;

@Service("emarket_EmarketIndexService")
public class EmarketIndexServiceImpl implements EmarketIndexService {

	@Autowired
	private IndexService indexService;

	@Transactional
	public <T> void index(Class<T>... entities) {
		indexService.index(entities);
	}
	
	@Transactional
	public  void indexPublishedProduct() {
		indexService.indexByCriteria(Product.class,Restrictions.eq("state", ProductStateType.PUBLISH));
	}
	
	@Transactional
	public <T, I extends Serializable> void index(Class<T> entity, I id) {
		indexService.index(entity, id);
	}
	

	@Transactional
	public <T, I extends Serializable> void purge(Class<T> clazz, I id) {
		indexService.purge(clazz, id);
	}

	@Transactional
	public <T> void purgeAll(Class<T> clazz) {
		indexService.purgeAll(clazz);
	}

	
}
