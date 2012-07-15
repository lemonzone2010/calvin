package com.apusic.ebiz.framework.core.solr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.apusic.ebiz.framework.core.DummyUser;
import com.apusic.ebiz.framework.core.dao.CrudService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class HibernateEventsTest {

	@Autowired
	@Qualifier("ebiz_CrudService")
	private CrudService crudService;
	
	@Test
	public void insertEvent() {
		DummyUser user = new DummyUser();
		user.setUserName("Test");
		user.setEmailAddress("ttt@apusic.com");
		user.setPassword("122");
		user.setSex("m");
		crudService.create(user);//after insert,solr must add new record
	}
}
