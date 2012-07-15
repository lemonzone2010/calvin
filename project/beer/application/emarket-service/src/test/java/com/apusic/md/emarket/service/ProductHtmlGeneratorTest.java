package com.apusic.md.emarket.service;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.model.datamanagement.Category;
import com.apusic.ebiz.model.datamanagement.ConfigurationData;
import com.apusic.ebiz.model.datamanagement.DataType;
import com.apusic.ebiz.model.user.User;
import com.apusic.md.model.emarket.Brand;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductCategory;
import com.apusic.md.model.emarket.ProductStateType;
import com.apusic.md.model.emarket.ProductStockType;
import com.apusic.md.model.emarket.SaleType;
import com.apusic.md.model.emarket.StateType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-datamanagement-service.xml",
		"classpath*:apusic-md-emarket-service.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-md-emarket-service-test.xml" })
public class ProductHtmlGeneratorTest {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductImageService imageService;

	@Autowired
	private CrudService crudService;

	@Autowired
	@Qualifier("emarket_ProductHtmlGenerator")
	private HtmlGenerator generator;

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

		Category c = new Category();
		c.setName("test");
		crudService.create(c);
		ConfigurationData data = new ConfigurationData();
		data.setCategory(c);
		data.setKey("login.url");
		data.setName("login.url");
		data.setValue("http://127.0.0.1:6888/iam/login");
		data.setType(DataType.PLAINTEXT);
		crudService.create(data);

		ConfigurationData data1 = new ConfigurationData();
		data1.setCategory(c);
		data1.setKey("logout.url");
		data1.setName("logout.url");
		data1.setValue("http://127.0.0.1:6888/iam/logout");
		data1.setType(DataType.PLAINTEXT);
		crudService.create(data1);

		ConfigurationData data2 = new ConfigurationData();
		data2.setCategory(c);
		data2.setKey("person.register.url");
		data2.setName("person.register.url");
		data2.setValue("http://127.0.0.1:6888/iam/login1");
		data2.setType(DataType.PLAINTEXT);
		crudService.create(data2);


	}

	@Test
	public void addWaitPublishProduct() throws IOException{
		Product p = new Product();
		p.setDesc("这是描述");
		p.setId(0);
		p.setIntroduction("这是介绍");
		p.setKeywords("这是关键字");
		p.setProductName("三星手机");
		p.setAttribute("网络:移动3G;电池:1个月;屏幕:TFT;3D加速:有");
		p.setCode("code");
		p.setCreateTime(new Date());
		p.setDesc("好手机");
		p.setKeywords("三星,手机");
		p.setPublishedTime(new Date());
		p.setRetailPrice(1000);
		p.setState(ProductStateType.WAIT_PUBLISH);
		p.setStock(ProductStockType.IN_STOCK);
		p.setWholesalePrice(800);
		p.setWholeSaleThrehold(100);
		p.setSaleType(SaleType.RETAIL);
		p.setCustomization("颜色:红色|蓝色|黑色;尺码:L|M|XL|XXL");

/*		ProductImage pi = new ProductImage();
		pi.setImagePath("/product/images/1.jpg");
		crudService.create(pi);
		ProductImage pi2 = new ProductImage();
		pi2.setImagePath("/product/images/1.jpg");
		crudService.create(pi2);
	//	List<ProductImage> images=new ArrayList<ProductImage>();

		p.addImage(pi);
		p.addImage(pi2);*/

		User user = new User();
		user.setDisbled(true);
		user.setName("admin");
		user.setPassword("admin");
		this.crudService.create(user);

		p.setUser(user);
		this.productService.addWaitPublishProduct(p, 1, 1);
		this.generator.generate(1);
	}


}
