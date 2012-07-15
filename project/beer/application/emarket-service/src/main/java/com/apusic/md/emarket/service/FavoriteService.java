package com.apusic.md.emarket.service;

import java.util.List;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.md.model.emarket.Favorite;

/**
 * 收藏夹服务类
 * @author chenwenjie
 *
 */
public interface FavoriteService {

	/**
	 * 保存商品到收藏夹
	 * @param Favorite
	 */
	void saveProductToFavorite(Favorite favorite);
	
	/**
	 * 删除收藏夹中的商品
	 * @param productId
	 */
	void deleteProductAtFavorite(Integer id);
	
	
	/**
	 * 获取所有收藏夹的商品 分页
	 * @return
	 */
	Page<Favorite> findFavoriteAllProduct4Page(GenericQueryObject query);
	
	
	/**
	 * 获取所有收藏夹的商品
	 * @return
	 */
	List<Favorite> findFavoriteAllProducts(GenericQueryObject query);
}
