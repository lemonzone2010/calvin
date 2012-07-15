package com.apusic.md.emarket.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.md.model.emarket.CategoryAttr;
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
public class CategoryAggributeServiceTest {

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private CategoryAttributeService categoryAttributeService;

	@Autowired
	private CrudService crudService;

	@Before
	public void init(){
		ProductCategory p = new ProductCategory();
		p.setKey("类别");
		p.setLevel(3);
		p.setName("类别");
		p.setParent(p);
		p.setSerialNumber(1);
		p.setState(StateType.ENABLED);

		CategoryAttr attr = new CategoryAttr();
		attr.setCategory(p);
		attr.setKey("color");
		attr.setName("颜色");
		attr.setSerialNumber(1);
		attr.setValue("红色|蓝色|褐色");

		CategoryAttr attr1 = new CategoryAttr();
		attr1.setCategory(p);
		attr1.setKey("型号");
		attr1.setName("型号");
		attr1.setSerialNumber(1);
		attr1.setValue("XXL|XXXL|XL");

		p.addCategoryAttr(attr);
		p.addCategoryAttr(attr1);

		productCategoryService.addCategory(p);

	}

	@Test
	public void getAttibutesByCategoryId(){
		List<CategoryAttr> attrs = categoryAttributeService.getAttibutesByCategoryId(1);
		Assert.assertTrue(2<=attrs.size());
	}

	@Test
	public void createAttr(){
	    CategoryAttr attr = new CategoryAttr();
	    ProductCategory p = new ProductCategory();
	    p.setId(2);
        attr.setCategory(p);
        attr.setKey("color");
        attr.setName("颜色");
        attr.setSerialNumber(1);
        attr.setValue("红色|蓝色|褐色");
        categoryAttributeService.createAttr(attr);
        Assert.assertTrue(categoryAttributeService.getAttibutesByCategoryId(2).size() > 0);
	}
}
