package com.apusic.ebiz.framework.core.solr;

import java.util.List;

import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.DummyBook;
import com.apusic.ebiz.framework.core.dao.QueryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class HibernateSolrIntegratorTest {

	@Autowired
	@Qualifier("queryRepository")
	QueryService queryService;
	
	private void init() {
		//Configuration cfg = new Configuration().configure(new File("hibernate.cfg.xml"));
		//sessionFactory = cfg.buildSessionFactory();
		// TODO Auto-generated method stub
		//Configuration configuration;
	}
	@Test
	public void search() {
		List<DummyBook> findAll = queryService.findAll(DummyBook.class);
		System.out.println(findAll);
	}
}
