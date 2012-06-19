package com.apusic.ebiz.smartorg.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.model.user.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-smartorg-service.xml",
		"classpath:apusic-ebiz-smartorg-service-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class UserRoleServiceTest {

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private CrudService crudService;

	//Method name should be self-discribing
	@Test
	@Transactional
	public void grantRoleToUser(){
		//Create some users and role for testing
		User user1 = new User();
		user1.setName("achen1");
		user1.setPassword("111");
		crudService.create(user1);

		User user2 = new User();
		user2.setName("achen2");
		user2.setPassword("111");
		crudService.create(user2);

		User user3 = new User();
		user3.setName("achen3");
		user3.setPassword("111");
		crudService.create(user3);

		Role role1 = new Role();
		role1.setName("ADMIN");
		crudService.create(role1);


		Role role2 = new Role();
		role2.setName("ADMIN2");
		crudService.create(role2);

		Role role3 = new Role();
		role3.setName("ADMIN3");
		crudService.create(role3);



		//Step 1: I need to retrieve an existing user from database
		//So I need to inject CrudService
		User user = crudService.retrieve(User.class, 1);

		//Step 2: I need to load an existing role as well
		Role userRole = crudService.retrieve(Role.class, 1);

		//Step 3: grant the role to the user 1 - role 1
		userRoleService.grantRoleToUser(userRole, user);


		//Sometimes I want to grant a role to a user by passing the role id and the user object
		//Then I add the interface

		int roleId = 2;
		user2 = crudService.retrieve(User.class, 2);

		//role 2 -> user 2
		userRoleService.grantRoleToUser(roleId, user2);

		//role 3 -> user 3
		roleId = 3;
		int userId = 3;
		userRoleService.grantRoleToUser(roleId, userId);
	}


	@Test
	@Transactional
	public void verifyGrantedRolesForEachUser(){
		User user1 = crudService.retrieve(User.class, 1);
		boolean hasAnyRole = user1.hasAnyRole("ADMIN");
		boolean hasAllRole = user1.hasAllRole("ADMIN");
		assertTrue(hasAnyRole);
		assertTrue(hasAllRole);

		User user2 = crudService.retrieve(User.class, 2);
		hasAnyRole = user2.hasAnyRole("ADMIN2");
		hasAllRole = user2.hasAllRole("ADMIN2");
		assertTrue(hasAnyRole);
		assertTrue(hasAllRole);

		User user3 = crudService.retrieve(User.class, 3);
		hasAnyRole = user3.hasAnyRole("ADMIN3");
		hasAllRole = user3.hasAllRole("ADMIN3");
		assertTrue(hasAnyRole);
		assertTrue(hasAllRole);
	}

	@Test
	@Transactional
	public void granteRolesToUsers(){
		userRoleService.grantRolesToUsers(new int[]{1,2,3}, new int[]{1,2,3});
		User user = crudService.retrieve(User.class, 1);

		boolean hasAllRoles = user.hasAllRole(new String[]{"ADMIN","ADMIN2","ADMIN3"});

		assertTrue(hasAllRoles);
	}
}
