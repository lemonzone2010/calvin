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
import com.apusic.md.model.emarket.News;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-datamanagement-service.xml",
		"classpath*:apusic-md-emarket-service.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-md-emarket-service-test.xml" })
public class HomepageHtmlGeneratorTest {

	@Autowired
	@Qualifier("emarket_HomepageHtmlGenerator")
	private HtmlGenerator generator;

	@Autowired
	private CrudService crudService;

	@Before
	public void init(){
		for(int i = 0; i <10; i++){
			News n = new News();
			n.setName("爱上蔡康永的【CAI】时尚" + i);
			n.setContent("content");
			n.setCreateTime(new Date());
			crudService.create(n);
		}

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
	public void generateBrandHtml() throws IOException{
		this.generator.generate(0);
	}
}
