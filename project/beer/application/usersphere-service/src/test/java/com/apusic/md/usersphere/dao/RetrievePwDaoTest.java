package com.apusic.md.usersphere.dao;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.md.model.usersphere.RetrievePW;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-md-usersphere-service.xml", "classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-md-usersphere-service-test.xml" })
@Transactional
public class RetrievePwDaoTest {

	@Autowired
	private RetrievePwDao retrievePwDao;

	@Autowired
	private CrudService crudService;

	@Before
	public void init(){
		RetrievePW p = new RetrievePW();
		p.setEmail("email@apusic.com");
		p.setName("admin");
		p.setResetCode("test");
		p.setSecurityCode("test");
		p.setTimeout(new Date());
		crudService.create(p);
	}

	@Test
	public void getUserProfilebyUser(){
		RetrievePW retrievePWByName = retrievePwDao.getRetrievePWByName("admin");
		Assert.assertNotNull(retrievePWByName);
	}
}
