package com.apusic.md.emarket.service;

import java.io.IOException;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.md.model.emarket.Brand;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductStateType;

/**
 * 商品服务类
 * @author xuzhengping
 *
 */
public interface ProductService {

	/**
	 * 添加未发布商品
	 * @param p
	 * @param categoryId 类别id
	 * @param brandId 品牌id
	 */
	void addWaitPublishProduct(Product p,int categoryId,int brandId);

	/**
	 * 发布一个未发布的商品
	 * @param productId
	 * @throws IOException
	 */
	void addPublishProduct(int productId) throws IOException;

	/**
	 * 添加已经发布的商品
	 * @param p
	 * @throws IOException
	 */
	void addPublishProduct(Product p,int categoryId,int brandId) throws IOException;

	/**
	 * 添加下架商品
	 * @param p
	 */
	void updateProductState(int productId,ProductStateType StateType)throws IOException;

	/**
	 * 获取一个商品
	 * @param productId
	 * @return
	 */
	Product getProduct(int productId);

	/**
	 * 预览商品
	 * @param Product product
	 * @return
	 * @throws IOException
	 */
	String previewProduct(Product product,String previewName) throws IOException;
	String previewProductWholesale(Product product,String previewName) throws IOException;


	/**
	 *	分页查询已经发布的商品
	 * @param query
	 * @return
	 */
	Page<Product> findPublishedProduct4Page(GenericQueryObject query);

	/**
     *  分页查询下架的商品
     * @param query
     * @return
     */
	Page<Product> findCancelPublishedProduct4Page(GenericQueryObject query);

	/**
     *  分页查询未发布的商品
     * @param query
     * @return
     */
	Page<Product> findWaitPublishedProduct4Page(GenericQueryObject query);

	/**
	 * 更新已发布产品
	 * @param product
	 * @throws IOException
	 */
	public void updatePublishProduct(Product product) throws IOException;

	/**
	 * 更新待发布产品
	 * @param product
	 */
	public void updateWaitPublishProduct(Product product) throws IOException;

	/**
	 * 分页查询商品
	 * @param query
	 * @return
	 */
	Page<Product> findProduct4Page(GenericQueryObject query);

	/**
	 * 判断品牌下有没有商品
	 * @param brand
	 * @return
	 */
	boolean hasProductsByBrand(Brand brand);

}
