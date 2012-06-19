package com.apusic.ebiz.framework.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
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
import com.apusic.ebiz.framework.core.Permission;

/**
 * Test the CRUD service with Derby database see
 * http://indiwiz.com/2009/01/05/unit-testing-hibernate-code-with-derby/
 * http://www.disasterarea.co.uk/blog/?p=75
 * http://www.adam-bien.com/roller/abien/entry/generic_crud_service_aka_dao
 * 
 * @author achen
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class CrudRepositoryHibernateImplTest {

	// Simulate to share by all threads
	private static Permission detachedPermission;

	@Autowired
	@Qualifier("ebiz_CrudService")
	private CrudService crudService;

	@Test
	@Transactional
	@Rollback(false)
	public void createPermission() {

		detachedPermission = new Permission();
		// Originally this instance of Permission is detached.
		boolean isAttached = ((Session) crudService.getDelegate()).contains(detachedPermission);
		assertFalse(isAttached);

		detachedPermission.setName("READ_WRITE");

		Permission attachedPermission = crudService.create(detachedPermission);

		Permission p2 = new Permission();
		p2.setName("Good");
		crudService.create(p2);

		// The same object
		assertTrue(detachedPermission == attachedPermission);

		// But become attached
		isAttached = ((Session) crudService.getDelegate()).contains(detachedPermission);

		assertTrue(isAttached);

		assertNotNull(detachedPermission.getId());

	}

	@Test
	@Transactional
	public void retrievePermission() {
		// It becomes detached again
		boolean isAttached = ((Session) crudService.getDelegate()).contains(detachedPermission);
		assertFalse(isAttached);

		// We need to get it from the database
		Permission p = crudService.retrieve(Permission.class, -10l);
		// Cannot be found
		assertNull(p);

		Long id = 0l;
		Permission atachedPermission = crudService.retrieve(Permission.class, id);

		// Then become attached
		isAttached = ((Session) crudService.getDelegate()).contains(atachedPermission);
		assertTrue(isAttached);

		// Get its properties
		assertEquals(atachedPermission.getName(), "READ_WRITE");
	}

	@Test
	@Transactional
	public void updatePermission() {
		Permission p = new Permission();
		p.setId((int) 1l);
		p.setName("UPDATE");

		crudService.update(p);

		// Still detached
		boolean isAttached = ((Session) crudService.getDelegate()).contains(p);

		assertFalse(isAttached);

		assertEquals(p.getName(), "UPDATE");

		Permission p1 = crudService.retrieve(Permission.class, 1l);

		// Not the same object
		assertFalse(p == p1);

		// But equals
		assertEquals(p, p1);

	}

	@Test
	@Transactional
	@Rollback(true)
	public void deletePermission() {
		Permission p2 = new Permission();
		Integer id = 20;
		p2.setId(id);
		crudService.delete(p2);
	}

	@Test
	@Transactional
	public void deleteAllPermissions() {
		Permission p1 = new Permission();
		p1.setName("name1");
		crudService.create(p1);

		Permission p2 = new Permission();
		p2.setName("name2");
		crudService.create(p2);

		List<Permission> permissions = new ArrayList<Permission>();
		permissions.add(p1);
		permissions.add(p2);

		crudService.deleteAll(permissions);

	}

	@Test
	@Transactional
	public void createUser() {
		DummyUser user = new DummyUser();
		user.setUserName("Alex Chen");
		user.setEmailAddress("chenmingdong@apusic.com");
		user.setPassword("122");
		user.setSex("m");

		DummyRole role = new DummyRole();
		role.setRoleName("ROLE_ADMIN");

		user.setName("Alex Chen");
		user.setRole(role);

		// Once the transient object being saved, it will become managed
		DummyUser attachedUser = crudService.create(user);
		assertEquals(attachedUser.getUserName(), "Alex Chen");
		assertEquals(attachedUser.getEmailAddress(), "chenmingdong@apusic.com");
		assertEquals(attachedUser.getName(), "Alex Chen");
		boolean isAttached = ((Session) crudService.getDelegate()).contains(attachedUser);
		assertTrue(isAttached);

		isAttached = ((Session) crudService.getDelegate()).contains(role);
		assertTrue(isAttached);
	}

	@Test
	@Transactional
	public void createUserWithExistingRole() {

		for (int i = 0; i < 100; i++) {
			DummyRole role = new DummyRole();
			role.setRoleName("ROLE_ADMIN");
			crudService.create(role);
		}

		DummyRole existingRole = crudService.retrieve(DummyRole.class, Long.valueOf(22));

		DummyUser anotherNewUser = new DummyUser();
		anotherNewUser.setSex("m");
		anotherNewUser.setUserName("Alex Chen");
		anotherNewUser.setEmailAddress("chenmingdong@apusic.com");
		anotherNewUser.setPassword("122");
		anotherNewUser.setName("achen");
		crudService.create(anotherNewUser);
		anotherNewUser.setRole(existingRole);
		boolean isAttached = ((Session) crudService.getDelegate()).contains(existingRole);
		assertTrue(isAttached);
	}

	@Test
	@Transactional
	public void retrieveUser() {
		// So the user will become managed (a.k.a attached) by Hibernate Session
		for (int i = 0; i < 100; i++) {
			createUser();
		}

		DummyUser user = crudService.retrieve(DummyUser.class, 11l);
		assertEquals(user.getName(), "Alex Chen");
		assertEquals(user.getRole().getRoleName(), "ROLE_ADMIN");

	}

	@Test
	@Transactional
	@Rollback(true)
	public void deleteUser() {
		DummyUser user = crudService.retrieve(DummyUser.class, 29l);
		if (user != null) {
			crudService.delete(user);
		}
	}

	@Test
	@Transactional
	@Rollback(true)
	public void deleteDetachedUser() {
		DummyUser user = new DummyUser();

		// Exist or not exist
		user.setId(29);
		user.setPassword("123");
		crudService.delete(user);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void deleteDetachedUserById() {
		for (int i = 0; i < 100; i++) {
			createUser();
		}
		DummyUser user = crudService.retrieve(DummyUser.class, 10l);
		crudService.delete(user);
	}
}