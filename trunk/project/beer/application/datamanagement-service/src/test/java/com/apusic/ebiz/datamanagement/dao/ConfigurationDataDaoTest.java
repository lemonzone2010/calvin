package com.apusic.ebiz.datamanagement.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.model.datamanagement.Category;
import com.apusic.ebiz.model.datamanagement.ConfigurationData;
import com.apusic.ebiz.model.datamanagement.DataType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-datamanagement-service.xml",
		"classpath*:apusic-ebiz-datamanagement-service-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class ConfigurationDataDaoTest {

	@Autowired
	private ConfigurationDataDao configurationDataDao;

	@Autowired
	private CrudService crudService;

	@Test
	@Transactional
	public void getConfigurationDatabyKey() {
		Category c = new Category();
		c.setName("系统访问URL");
		c.setIntro("ttt");
		crudService.create(c);
		ConfigurationData e = new ConfigurationData();
		e.setKey("login.url3");
		e.setName("登录页面");
		e.setType(DataType.PLAINTEXT);
		e.setValue("http://127.0.0.1:7001/iam");
		e.setCategory(c);
		crudService.create(e);
		ConfigurationData configurationData = configurationDataDao
				.getConfigurationDatabyKey("login.url3");
		Assert.assertEquals("登录页面", configurationData.getName());
	}

	@Test
	@Transactional
	public void getConfigurationDatasByCategory() {
		Category c = new Category();
		c.setName("系统访问URL");
		c.setIntro("22");
		crudService.create(c);

		ConfigurationData e = new ConfigurationData();
		e.setKey("test11");
		e.setName("登录页面");
		e.setType(DataType.PLAINTEXT);
		e.setValue("http://127.0.0.1:7001/iam");
		e.setCategory(c);
		crudService.create(e);
		List<ConfigurationData> datas = configurationDataDao.getConfigurationDatasByCategory(c);
		Assert.assertSame(2, datas.get(0).getId());
	}
}
