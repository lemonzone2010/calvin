package com.apusic.md.emarket.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.fulltextsearch.FullTextQueryParameter;
import com.apusic.ebiz.framework.core.fulltextsearch.FullTextQueryService;
import com.apusic.ebiz.model.user.User;
import com.apusic.md.model.emarket.Brand;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductCategory;
import com.apusic.md.model.emarket.ProductImage;
import com.apusic.md.model.emarket.ProductStateType;
import com.apusic.md.model.emarket.ProductStockType;
import com.apusic.md.model.emarket.StateType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-datamanagement-service.xml",
		"classpath*:apusic-md-emarket-service.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-md-emarket-service-test.xml" })
public class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private CrudService crudService;

	@Autowired
	private FullTextQueryService fullTextQueryService;

	@Before
	public void init(){
		ProductCategory c1 = new ProductCategory();
		c1.setKey("shouji");
		c1.setName("手机");
		c1.setParent(c1);
		c1.setSerialNumber(1);
		c1.setState(StateType.ENABLED);
		crudService.create(c1);

		Brand b1 = new Brand();
		b1.setLogoPath("c:/root");
		b1.setName("三星");
		b1.setState(StateType.ENABLED);
		b1.setUrl("www.sina.com.cn");
		crudService.create(b1);
	}

	@Test
	public void addWaitPublishProduct() throws IOException{
		Product p = new Product();
		p.setAttribute("网络:移动3G");
		p.setCode("code");
		p.setCreateTime(new Date());
		p.setCustomization("颜色:red|black");
		p.setDesc("好手机");
		p.setIntroduction("introduction");
		p.setKeywords("三星,手机");
		p.setPublishedTime(new Date());
		p.setRetailPrice(1000);
		p.setState(ProductStateType.WAIT_PUBLISH);
		p.setStock(ProductStockType.IN_STOCK);
		p.setWholesalePrice(800);
		p.setWholeSaleThrehold(100);

		ProductImage pi = new ProductImage();
		pi.setImagePath("c:/root/iamge.gif");
		p.addImage(pi);

		User user = new User();
		user.setDisbled(true);
		user.setName("test");
		user.setPassword("admin");
		this.crudService.create(user);

		p.setUser(user);

		this.productService.addPublishProduct(p, 1, 1);
	}

	@Test
	public void fullTextQuery(){
		FullTextQueryParameter<Product> query = new FullTextQueryParameter<Product>(Product.class,new String[]{"shouji"},new String[]{"category.key","brand.name","customization","stock"});
		FullTextQueryParameter<Brand> query2 = new FullTextQueryParameter<Brand>(Brand.class,new String[]{"三星"},new String[]{"name"});
		List<Product> p = fullTextQueryService.find(query, 10,10);
		List<Brand> find = fullTextQueryService.find(query2, 10, 10);
		System.out.println("ProductServiceTest.fullTextQuery()");
	}

	@Test
	public void addPublishProduct() throws IOException{
		Product p = new Product();
		p.setAttribute("网络:移动3G");
		p.setCode("code");
		p.setCreateTime(new Date());
		p.setCustomization("颜色:red|black");
		p.setDesc("好手机");
		p.setIntroduction("introduction");
		p.setKeywords("三星,手机");
		p.setPublishedTime(new Date());
		p.setRetailPrice(1000);
		p.setState(ProductStateType.WAIT_PUBLISH);
		p.setStock(ProductStockType.IN_STOCK);
		p.setWholesalePrice(800);
		p.setWholeSaleThrehold(100);

		ProductImage pi = new ProductImage();
		pi.setImagePath("c:/root/iamge.gif");
		p.addImage(pi);
		User user = new User();
		user.setDisbled(true);
		user.setName("test2");
		user.setPassword("admin");
		this.crudService.create(user);

		p.setUser(user);
		this.productService.addPublishProduct(p, 1, 1);
		Assert.assertTrue(p.getId()>0);
	}

	@Test
	public void findPublishedProduct4Page(){
		GenericQueryObject query = new GenericQueryObject(Product.class);
		query.getDetachedCriteria().createAlias("category", "category");
		query.eq("category.name", "手机");
		Page<Product> productPage = productService.findPublishedProduct4Page(query);
		Assert.assertTrue(productPage.getTotal()>0);
		for(Product product:productPage.getObjects()){
			Assert.assertTrue(product.getId()>0);
		}
	}
}
