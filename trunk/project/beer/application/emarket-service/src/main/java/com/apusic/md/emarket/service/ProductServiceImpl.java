package com.apusic.md.emarket.service;

import java.io.IOException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.framework.core.fulltextsearch.IndexService;
import com.apusic.md.model.emarket.Brand;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductCategory;
import com.apusic.md.model.emarket.ProductStateType;
import com.apusic.md.model.emarket.SaleType;

@Service("emarket_ProductService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private QueryService queryService;

	@Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;

	@Autowired
	private CrudService crudService;

	@Autowired
	private IndexService indexService;

	@Autowired
	@Qualifier("emarket_ProductHtmlGenerator")
	private HtmlGenerator generator;

	@Autowired
    @Qualifier("emarket_ProductWholesaleHtmlGenerator")
    private HtmlGenerator wholesaleGenerator;

	@Transactional
	public void updateProductState(int productId,ProductStateType stateType) throws IOException {
		//删除索引
		Product product = this.getProduct(productId);
		Assert.notNull(product);

		product.setState(stateType);
		this.crudService.update(product);
		if(ProductStateType.PUBLISH.equals(stateType)){
		    indexService.index(Product.class, productId);
		}else{
		    indexService.purge(Product.class, productId);
		}
		this.generator.remove(String.valueOf(productId));
		this.generator.generate(product.getId());
		if(product.getWholesalePrice()>0){
            wholesaleGenerator.generate(product.getId());
        }
	}
	@Transactional
	public void addPublishProduct(Product p,int categoryId,int brandId) throws IOException {
		this.addWaitPublishProduct(p, categoryId, brandId);
		this.addPublishProduct(p.getId());
		if(p.getWholesalePrice()>0){
		 wholesaleGenerator.generate(p.getId());
		}
	}

	@Transactional
	public void addWaitPublishProduct(Product p,int categoryId,int brandId) {
		ProductCategory category = this.crudService.retrieve(ProductCategory.class, categoryId);
		Brand brand = this.crudService.retrieve(Brand.class, brandId);
		Assert.notNull(category);
		Assert.notNull(brand);
		Assert.notNull(p.getUser());
		p.setState(ProductStateType.WAIT_PUBLISH);
		p.setCategory(category);
		p.setBrand(brand);

		this.crudService.create(p);
	}

	@Transactional
	public void addPublishProduct(int productId) throws IOException {
		// 创建索引
		// 生存静态页面
		Product product = this.getProduct(productId);
		product.setState(ProductStateType.PUBLISH);
		Assert.notNull(product);
		this.generator.generate(productId);
	}

	@Transactional
	public void updatePublishProduct(Product product) throws IOException{
		this.crudService.update(product);
		//重新生存静态网页
		this.generator.remove(String.valueOf(product.getId()));
		this.generator.generate(product.getId());
		if (ProductStateType.PUBLISH.equals(product.getState())) {
		    //如果是上架，则加入lucene索引
		    indexService.index(Product.class, product.getId());
        }else{
    		indexService.purge(Product.class, product.getId());
        }
		if(SaleType.WHOLESALE.equals(product.getSaleType())){
            wholesaleGenerator.generate(product.getId());
        }
	}


	@Transactional
	public void updateWaitPublishProduct(Product product)throws IOException{
		this.crudService.update(product);
		
		if(product.getWholesalePrice()>0){
			wholesaleGenerator.generate(product.getId());
	     }

	}
	
	

	@Transactional(readOnly=true)
	public Product getProduct(int productId) {
		return this.crudService.retrieve(Product.class, productId);
	}

	public String previewProduct(Product product,String previewName) throws IOException {
		retrieveCategoryAndBrand(product);
		return generator.preview(product, previewName);
	}

	public String previewProductWholesale(Product product,String previewName)throws IOException {
		retrieveCategoryAndBrand(product);
        return wholesaleGenerator.preview(product, previewName);
    }
	private void retrieveCategoryAndBrand(Product product) {
		Brand brand  = crudService.retrieve(Brand.class, product.getBrand().getId());
		ProductCategory category = crudService.retrieve(ProductCategory.class, product.getCategory().getId());
		product.setBrand(brand);
		product.setCategory(category);
	}

	public Page<Product> findPublishedProduct4Page(GenericQueryObject query) {
		query.eq("state", ProductStateType.PUBLISH);
		query.getDetachedCriteria().addOrder(Order.desc("publishedTime"));
		return findProduct4Page(query);
	}

	public Page<Product> findCancelPublishedProduct4Page(GenericQueryObject query) {
        query.eq("state", ProductStateType.CANCEL_PUBLISH);
        query.getDetachedCriteria().addOrder(Order.desc("publishedTime"));
        return findProduct4Page(query);
    }

    public Page<Product> findWaitPublishedProduct4Page(GenericQueryObject query) {
        query.eq("state", ProductStateType.WAIT_PUBLISH);
        query.getDetachedCriteria().addOrder(Order.desc("publishedTime"));
        return findProduct4Page(query);
    }

	public Page<Product> findProduct4Page(GenericQueryObject query) {
		query.getDetachedCriteria().addOrder(Order.desc("publishedTime"));
		 return queryService.findPageBy(query
	                .getDetachedCriteria(), configService
	                .getIntegerValueByKey("pageSize"));
	}

	public boolean hasProductsByBrand(Brand brand) {
		if(brand == null){
			return false;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.eq("brand.id", brand.getId()));
		long rowCount = queryService.getRowCount(criteria);
		return rowCount>0;
	}




}
