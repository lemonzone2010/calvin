package com.apusic.ebiz.framework.core.dao;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.DummyRole;
import com.apusic.ebiz.framework.core.DummyUser;
import com.apusic.ebiz.framework.core.UserType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class QueryObjectTest {
	@Autowired
	@Qualifier("ebiz_CrudService")
	private CrudService crudService;

	@Autowired
	@Qualifier("queryRepository")
	private QueryRepository queryService;

	@Before
	@Transactional
	public void prepareUsers() {
		DummyUser user = new DummyUser();
		user.setUserType(UserType.INTERNAL);
		user.setName("Sudo");
		user.setUserName("1");
		user.setPassword("222");
		user.setCreateTime(new Date());
		
		DummyRole role = new DummyRole();
		role.setRoleName("ROLE_ADMIN");
		
		user.setRole(role);

		// Once the transient object being saved, it will become managed
		crudService.create(user);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void toCriteria() {

		DummyUser user = new DummyUser();

		user.setName("Sudo");
		user.setUserName("1");
		user.setPassword("222");

		// Once the transient object being saved, it will become managed
		// DummyUser attachedUser = crudService.create(user);

		QueryObject queryObject = QueryObjectProxyFactory.getProxy(DummyUser.class);

		List<String> names = new ArrayList<String>();
		names.add("Sudo");
		names.add("Alex Chen");
		queryObject.getParameters().put("name", names);

		queryObject.in("name", "${name}");

		DetachedCriteria detachedCriteria = queryObject.getDetachedCriteria();

		List<DummyUser> users = queryService.findBy(detachedCriteria);
		assertTrue(users.size() > 0);
	}

	@Test
	@Transactional
	public void innerJoinQuery() {
		QueryObject innerJoinQuery = QueryObjectProxyFactory.getProxy(DummyUser.class);
		innerJoinQuery.eq("role.roleName", "ROLE_ADMIN");
		DetachedCriteria detachedCriteria = innerJoinQuery.getDetachedCriteria();
		List<DummyUser> users = queryService.findBy(detachedCriteria);
		Assert.assertTrue(users.size() > 0);
	}

	@Test
	@Transactional
	public void eqEnum() {
		QueryObject queryObject = QueryObjectProxyFactory.getProxy(DummyUser.class);
		queryObject.eq("userType", UserType.INTERNAL);
		List<DummyUser> users = queryService.findBy(queryObject.getDetachedCriteria());
		assertTrue(users.size() > 0);
		DummyUser user = users.get(0);
		Assert.assertEquals(user.getUserType(), UserType.INTERNAL);
	}

	@Test
	@Transactional
	public void proxy() {
		QueryObject queryObject = QueryObjectProxyFactory.getProxy(DummyUser.class);
		queryObject.eq("userType", UserType.INTERNAL);
		List<DummyUser> users = queryService.findBy(queryObject.getDetachedCriteria());
		assertTrue(users.size() > 0);
		DummyUser user = users.get(0);
		Assert.assertEquals(user.getUserType(), UserType.INTERNAL);
	}

	@Test
	@Transactional
	public void between() {

		QueryObject queryObject = QueryObjectProxyFactory.getProxy(DummyUser.class);

		queryObject.between("createTime", DateUtils.addDays(new Date(), -1), DateUtils.addDays(new Date(), 1));
		List<DummyUser> users = queryService.findBy(queryObject.getDetachedCriteria());
		Assert.assertTrue(users.size() > 1);

		queryObject.between("createTime", null, DateUtils.addDays(new Date(), 1));
		List<DummyUser> users2 = queryService.findBy(queryObject.getDetachedCriteria());
		Assert.assertTrue(users2.size() > 1);

		queryObject.between("createTime", DateUtils.addDays(new Date(), -1), null);
		List<DummyUser> users3 = queryService.findBy(queryObject.getDetachedCriteria());
		Assert.assertTrue(users3.size() > 1);

		queryObject.between("createTime", null, null);
		List<DummyUser> users4 = queryService.findBy(queryObject.getDetachedCriteria());
		Assert.assertTrue(users4.size() > 1);
	}

}
