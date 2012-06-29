package com.apusic.ebiz.datamanagement.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.ebiz.datamanagement.DatamanagementException;
import com.apusic.ebiz.model.datamanagement.Category;
import com.apusic.ebiz.model.datamanagement.ConfigurationData;
import com.apusic.ebiz.model.datamanagement.DataType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-datamanagement-service.xml",
		"classpath*:apusic-ebiz-datamanagement-service-test.xml" })
public class CategoryServiceTest {

	@Autowired
	private ConfigurationDataService configurationDataService;

	@Autowired
	private CategoryService categoryService;

	@Before
	public void init(){
		Category c = new Category();
		c.setIntro("222");
		c.setName("系统配置11");
		categoryService.addCategory(c);
		ConfigurationData data = new ConfigurationData();
		data.setCategory(c);
		data.setKey("mail.host");
		data.setName("邮件服务器");
		data.setType(DataType.PLAINTEXT);
		data.setValue("xxxx@apusic.com");
		configurationDataService.addConfigurationData(data);
	}

	@Test(expected=DatamanagementException.class)
	public void deleteCategory(){
		categoryService.deleteCategory(1);
	}

	@Test
	public void getAllCategorys(){
		List<Category> allCategorys = categoryService.getAllCategorys();
		Assert.assertNotSame(0, allCategorys.size());
	}

	@Test
	public void getCategory(){
		Category category = categoryService.getCategory(1);
		Assert.assertNotNull(category);
	}
}
