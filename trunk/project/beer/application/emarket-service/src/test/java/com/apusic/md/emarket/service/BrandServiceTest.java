package com.apusic.md.emarket.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.md.model.emarket.Brand;
import com.apusic.md.model.emarket.ProductCategory;
import com.apusic.md.model.emarket.StateType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-datamanagement-service.xml",
		"classpath*:apusic-md-emarket-service.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-md-emarket-service-test.xml" })
public class BrandServiceTest {
	@Autowired
	private CrudService crudService;

	@Autowired
	private BrandService brandService;

	public static boolean isInit=false;

	@Before
	public void init(){
		if (isInit) {
			return ;
		}
		//isInit=true;

		ProductCategory category=new ProductCategory();
		category.setLevel(1);
		category.setName("11");
		category.setState(StateType.ENABLED);
		category.setParent(null);
		crudService.create(category);


		ProductCategory category1=new ProductCategory();
		category1.setLevel(2);
		category1.setName("11");
		category1.setState(StateType.ENABLED);
		category1.setParent(category);
		crudService.create(category1);

		ProductCategory category11=new ProductCategory();
		category11.setLevel(3);
		category11.setName("11");
		category11.setState(StateType.ENABLED);
		category11.setParent(category1);
		crudService.create(category11);

		Brand b1=new Brand();
		b1.setLogoPath("http://www.baidu.com/img/baidu_sylogo1.gif");
		b1.setName("百度");
		b1.setState(StateType.ENABLED);
		b1.setUrl("www.baidu.com");
		b1.addCategory(category11);
		crudService.create(b1);

		Brand b2 = new Brand();
		b2.setLogoPath("http://www.google.com.hk/intl/zh-CN/images/logo_cn.png");
		b2.setName("google");
		b2.setState(StateType.ENABLED);
		b2.setUrl("www.google.com.hk");
		b1.addCategory(category11);
		crudService.create(b2);

		Brand b3 = new Brand();
		b3.setLogoPath("http://www.google.com.hk/intl/zh-CN/images/logo_cn.png");
		b3.setName("google2");
		b3.setState(StateType.DISABLED);
		b3.setUrl("www.google.com.hk");
		b1.addCategory(category11);
		crudService.create(b3);
	}

	@Test
	@Transactional
	public void findEnabled() {
		List<Brand> brands=brandService.findEnabled();
		Assert.assertTrue(2<=brands.size());
	}

	@Test
	@Transactional
	public void findByCategoryId() {
		List<Brand> brandsAll=brandService.findAll();
		List<Brand> brands=brandService.findByCategoryId(3);
		Assert.assertTrue(brandsAll.size()>brands.size());
	}
	

	@Test
	@Transactional
	public void findById(){
	    Brand brand = brandService.findById(brandService.findAll().get(0).getId());
	    Assert.assertTrue(brand != null);
	}

	@Test
	@Transactional
	public void findRelatingsByLevel2CategoryId(){
	    List<Brand> brand = brandService.findBrandsByLevel2CategoryId(3);
	    Assert.assertTrue(brand.size()>0);
	}

	@Transactional
	@Test
	public void updateBrandCategeries(){
		Brand brand = brandService.findById(1);
		List<Integer> categeries = new ArrayList<Integer>();
		categeries.add(1);
		categeries.add(2);
		brandService.updateBrandCategeries(brand, categeries);
		Assert.assertTrue(brand.getCategorys().size()==2);

		List<Integer> categeries2 = new ArrayList<Integer>();
		categeries2.add(2);
		categeries2.add(3);
		brandService.updateBrandCategeries(brand, categeries2);
		Assert.assertTrue(brand.getCategorys().size()==2);
	}

	@Transactional
	@Test
	public void createBrand(){
		Brand brand = new Brand();
		brand.setName("123");
		brand.setUrl("123456");
		ProductCategory category = crudService.retrieve(ProductCategory.class, 1);
		brand.addCategory(category);
		brandService.addBrand(brand);
		Brand brand2 = crudService.retrieve(Brand.class, brand.getId());
		Assert.assertTrue(brand2.getCategorys().size()==1);
	}

	@Transactional
	@Test
	public void deleteBrand(){
		Brand brand = brandService.findAll().get(0);
		int id = brand.getId();
		brandService.deleteBrand(brand);
		brand = brandService.findById(id);
		Assert.assertNull(brand);
	}

}
