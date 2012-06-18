package com.apusic.ebiz.framework.core.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.DummyRole;
import com.apusic.ebiz.framework.core.DummyUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-user.xml"  })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class QueryRepositoryHibernateImplTest {

	@Autowired
	@Qualifier("ebiz_CrudService")
	private CrudService crudService;

	@Autowired
	@Qualifier("queryRepository")
	private QueryRepository queryService;

	@Before
	@Transactional
	public void setup() {
		// Create some data for testing
		for (int i = 0; i < 20; i++) {
			DummyUser user = new DummyUser();
			user.setUserName("xx");
			user.setEmailAddress("hsia@163.com");
			user.setPassword("122");
			user.setSex("m");

			DummyRole role = new DummyRole();
			role.setRoleName("ROLE_ADMIN");

			user.setName("Xia");
			user.setRole(role);
			crudService.create(user);
		}
	}

	@Test
	public void findAll() {
		List<DummyUser> users = queryService.findAll(DummyUser.class);
		assertTrue(users.size() > 0);
	}

	@Test
	public void findAllPage() {
		Page pages = queryService.findPage(DummyUser.class, new Page());
		assertTrue(pages.getRows().size() > 0);
	}

	@Test
	public void findEntitiesByExample() {
		DummyUser example = new DummyUser();
		example.setName("Xia");
		List<DummyUser> users = queryService.findByExample(example);
		assertTrue(users.size() > 0);
	}

	@Test
	public void findPagedEntitiesByExample() {
		DummyUser example = new DummyUser();
		example.setName("Xia");

		// Get the first page
		Page<DummyUser> page = queryService.findPageByExample(example, new Page());
		List<DummyUser> users = page.getRows();

		// On the page, there are 10 user objects
		//assertEquals(users.size(),10);
		assertNotNull(users);
		assertTrue(users.size()>1);
		assertTrue(page.isFirstPage());

	}

	@Test
	public void findPageByDetachedCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(DummyUser.class);
		criteria.add(Property.forName("name").eq("Xia"));
		Page<DummyUser> page = queryService.findPageBy(criteria, 10);
		List<DummyUser> users = page.getRows();
		// On the page, there are 10 user objects
		assertTrue(users.size() == 10);
		assertTrue(page.isFirstPage());
	}

	@Test
	public void getRowCountByDetachedCriteria() {
		for (int i = 0; i < 20; i++) {
			DummyUser user = new DummyUser();
			user.setUserName("achen");
			user.setEmailAddress("xiayong");
			user.setPassword("122");
			user.setSex("m");

			DummyRole role = new DummyRole();
			role.setRoleName("ROLE_ADMIN");

			user.setName("xxx");
			user.setRole(role);
			crudService.create(user);
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(DummyUser.class);
		criteria.add(Property.forName("name").eq("xxx"));
		long rowCnt = queryService.getRowCount(criteria);
		assertTrue(rowCnt == 20);
	}

}