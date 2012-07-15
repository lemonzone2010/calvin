package com.apusic.md.emarket.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryObjectProxyFactory;
import com.apusic.ebiz.model.datamanagement.Category;
import com.apusic.ebiz.model.datamanagement.ConfigurationData;
import com.apusic.ebiz.model.datamanagement.DataType;
import com.apusic.ebiz.model.user.User;
import com.apusic.md.model.emarket.Brand;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductCategory;
import com.apusic.md.model.emarket.ProductImage;
import com.apusic.md.model.emarket.ProductRecommend;
import com.apusic.md.model.emarket.ProductStateType;
import com.apusic.md.model.emarket.ProductStockType;
import com.apusic.md.model.emarket.RecommendType;
import com.apusic.md.model.emarket.StateType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-datamanagement-service.xml",
		"classpath*:apusic-md-emarket-service.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-md-emarket-service-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class ProductRecommendServiceTest {

	@Autowired
	private ProductRecommendService productRecommendService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CrudService crudService;

	private static boolean flag = true;

	@Before
	@Transactional
	public void init() throws IOException {
		if( !flag){
			return;
		}
		flag = false;

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
		user.setName("test" + new Random().nextFloat());
		user.setPassword("admin");
		this.crudService.create(user);

		p.setUser(user);

		this.productService.addPublishProduct(p, c1.getId(), b1.getId());
		Assert.assertTrue(p.getId() > 0);
	}

	@Test
	@Transactional
	public void addProductRecommend() {
		Product product = productService.getProduct(1);
		List<RecommendType> recommendTypes = new ArrayList<RecommendType>();
		recommendTypes.add(RecommendType.HOT);
		recommendTypes.add(RecommendType.NEW);
		productRecommendService.addProductRecommend(product, recommendTypes);

		recommendTypes = new ArrayList<RecommendType>();
		recommendTypes.add(RecommendType.HOT);
		recommendTypes.add(RecommendType.RECOMMEND);
		productRecommendService.addProductRecommend(product, recommendTypes);
	}

	@Test
	@Transactional
	public void getRecommendTypesByproductId() {
		List<RecommendType> recommendTypes = productRecommendService
				.getRecommendTypesByProductId(1);
		Assert.assertTrue(recommendTypes.size() > 0);
	}

	@Test
	@Transactional
	public void findNewProductsShelves4Page() {
		GenericQueryObject queryObject = (GenericQueryObject) QueryObjectProxyFactory.getProxy(ProductRecommend.class);
		Page<ProductRecommend> proPage = productRecommendService
				.findNewProductsShelves4Page(queryObject);
		Assert.assertTrue(proPage.getTotal() == 0);
	}

	@Test
	@Transactional
	public void findRecommends4Page() {
		GenericQueryObject queryObject = (GenericQueryObject) QueryObjectProxyFactory.getProxy(ProductRecommend.class);
		Page<ProductRecommend> proPage = productRecommendService
				.findRecommends4Page(queryObject);
		Assert.assertTrue(proPage.getTotal() > 0);
	}

	@Test
	@Transactional
	public void findHOTProducts4Page() {
		GenericQueryObject queryObject = (GenericQueryObject) QueryObjectProxyFactory.getProxy(ProductRecommend.class);
		Page<ProductRecommend> proPage = productRecommendService
				.findHotProducts4Page(queryObject);
		Assert.assertTrue(proPage.getTotal() > 0);
	}

	@Test
	@Transactional
	public void updateProductRecommendsByProductId(){
		Product product = productService.getProduct(1);
		List<RecommendType> recommendTypes = new ArrayList<RecommendType>();
		recommendTypes.add(RecommendType.HOT);
		productRecommendService.addProductRecommend(product, recommendTypes);
		ProductRecommend productRecommend = crudService.retrieve(ProductRecommend.class, 1);
		Date startDate = productRecommend.getStartDate();

		productRecommendService.updateProductRecommendsByProductId(1);
		ProductRecommend productRecommend2 = crudService.retrieve(ProductRecommend.class, 1);
		Date startDate2 = productRecommend2.getStartDate();

		Assert.assertTrue(startDate2.compareTo(startDate)>0);
	}

	@Test
	@Transactional
	public void abolishProductRecommendById() {
		ProductRecommend productRecommend = crudService.retrieve(
				ProductRecommend.class, 1);
		Assert.assertNotNull(productRecommend);
		productRecommendService.abolishProductRecommendById(productRecommend
				.getId());
		productRecommend = crudService.retrieve(ProductRecommend.class, 1);
		Assert.assertNull(productRecommend);
	}
}
