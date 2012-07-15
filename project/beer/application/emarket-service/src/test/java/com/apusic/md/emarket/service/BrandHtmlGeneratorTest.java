package com.apusic.md.emarket.service;

import java.io.IOException;

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
public class BrandHtmlGeneratorTest {

	@Autowired
	private BrandService brandService;

	@Autowired
	private CrudService crudService;

	@Autowired
	@Qualifier("emarket_BrandHtmlGenerator")
	private HtmlGenerator generator;

	public static boolean isInit=false;
	@Before
	public void init(){
		if (isInit) {
			return ;
		}
		isInit=true;
		ProductCategory pc=new ProductCategory();
		pc.setKey("鞋");
	     pc.setLevel(1);
	     pc.setName("鞋");
	     pc.setSerialNumber(1);
	     pc.setState(StateType.ENABLED);
	     crudService.create(pc);
		ProductCategory pc1=new ProductCategory();
		 pc1.setKey("男鞋");
	     pc1.setLevel(2);
	     pc1.setName("男鞋");
	     pc1.setSerialNumber(2);
	     pc1.setState(StateType.ENABLED);
	     crudService.create(pc1);

		Brand b1=new Brand();
		b1.setLogoPath("http://www.baidu.com/img/baidu_sylogo1.gif");
		b1.setName("百度");
		b1.setState(StateType.ENABLED);
		b1.setUrl("www.baidu.com");
		b1.addCategory(pc1);
		crudService.create(b1);

		Brand b2 = new Brand();
		b2.setLogoPath("http://www.google.com.hk/intl/zh-CN/images/logo_cn.png");
		b2.setName("google");
		b2.setState(StateType.ENABLED);
		b2.setUrl("www.google.com.hk");
		b2.addCategory(pc1);
		crudService.create(b2);

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
		this.generator.generate(1);
	}


}
