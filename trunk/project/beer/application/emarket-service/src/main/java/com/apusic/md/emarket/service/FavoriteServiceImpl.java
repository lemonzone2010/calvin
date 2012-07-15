package com.apusic.md.emarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.emarket.Favorite;
@Service("emarket_FavoriteService")
public class FavoriteServiceImpl implements FavoriteService {


	@Autowired
	private CrudService crudService;

	@Autowired
	private QueryService queryService;

	@Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;

	@Transactional
	public void saveProductToFavorite(Favorite favorite) {
		this.crudService.create(favorite);
	}

	@Transactional
	public void deleteProductAtFavorite(Integer id) {
		this.crudService.delete(Favorite.class,id);
	}

	@Transactional
	public Page<Favorite> findFavoriteAllProduct4Page(GenericQueryObject query) {
		return queryService.findPageBy(query.getDetachedCriteria(), configService
				.getIntegerValueByKey("pageSize"));
	}

	@Transactional
	public List<Favorite> findFavoriteAllProducts(GenericQueryObject query) {
		return queryService.findBy(query.getDetachedCriteria());
	}

}
