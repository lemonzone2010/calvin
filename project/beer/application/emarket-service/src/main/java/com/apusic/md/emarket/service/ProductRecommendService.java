package com.apusic.md.emarket.service;

import java.util.List;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductRecommend;
import com.apusic.md.model.emarket.RecommendType;

public interface ProductRecommendService {

	/**
	 * 根据类型 添加商品 扩展
	 *
	 * @param p
	 * @param recommendTypes
	 */
	void addProductRecommend(Product p, List<RecommendType> recommendTypes);

	/**
	 * 根据商品id，得到所有商品扩展 的列表
	 *
	 * @return
	 */
	List<RecommendType> getRecommendTypesByProductId(int productId);

	/**
	 * 根据id取消
	 *
	 * @param productRecommend
	 */
	void abolishProductRecommendById(int productRecommendId);

	/**
	 * 得到新品上架列表
	 *
	 * @return
	 */
	Page<ProductRecommend> findNewProductsShelves4Page(
			GenericQueryObject queryObject);

	/**
	 * 得到商城推荐
	 *
	 * @return
	 */
	Page<ProductRecommend> findRecommends4Page(GenericQueryObject queryObject);

	/**
	 *得到热卖商品
	 *
	 * @return
	 */
	Page<ProductRecommend> findHotProducts4Page(GenericQueryObject queryObject);

	/**
	 * 得到 前n个 新品上架
	 * @param count
	 * @return
	 */
	List<ProductRecommend> getTopNewProducts(int count);

	/**
	 * 得到 前n个 商城推荐
	 * @param count
	 * @return
	 */
	List<ProductRecommend> getTopRecommendProducts(int count);

	/**
	 * 得到 前n个 畅销排行
	 * @param count
	 * @return
	 */
	List<ProductRecommend> getTopHotProducts(int count);

	/**
	 * 根据商品id，删除推荐，新品，热卖等信息
	 * @param productId
	 */
	void deleteProductRecommendsByProductId(int productId);

	List<ProductRecommend> findProductRecommends(RecommendType type,int start,int limit);

	long findProductRecommends(RecommendType type);

	/**
	 * 根据产品id，更新 扩展信息的时间
	 */
	void updateProductRecommendsByProductId(int productId);
}
