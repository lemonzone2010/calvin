package com.apusic.ebiz.smartorg.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.model.user.Resource;
import com.apusic.ebiz.model.user.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-smartorg-service-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class RoleResourceServiceTest {

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private RoleResourceService roleResourceService;

	@Before
	public void setUp(){
		Role role = new Role();
		role.setName("admin1");
		role.setAlias("管理员1");
		roleService.addRole(role);

		role = new Role();
		role.setName("admin2");
		role.setAlias("管理员2");
		roleService.addRole(role);

		Resource resource = new Resource();
		resource.setDesc("desc");
		resource.setPriority(1);
		resource.setResContext("smartorg");
		resource.setResName("test");
		resource.setResType("URL");
		resource.setResValue("/test");
		resourceService.addResource(resource);

		resource = new Resource();
		resource.setDesc("desc1");
		resource.setPriority(1);
		resource.setResContext("smartorg");
		resource.setResName("test1");
		resource.setResType("URL");
		resource.setResValue("/test1");
		resourceService.addResource(resource);
	}

	@Test
	@Transactional
	public void testAuthorizationForResource(){
		Role role = roleService.getRoleByName("admin1");
		Resource resource = resourceService.getResourceByContextAndValue("smartorg", "/test");
		roleResourceService.authorizationForResource(resource, role);
		role.setResources(null);
		roleService.updateRole(role);
	}

	@Test
	@Transactional
	public void testAuthorizationForResources(){
		Role role = roleService.getRoleByName("admin1");
		Resource resource1 = resourceService.getResourceByContextAndValue("smartorg", "/test");
		Resource resource2 = resourceService.getResourceByContextAndValue("smartorg", "/test1");
		List list = new ArrayList<Resource>();
		list.add(resource1);
		list.add(resource2);
		roleResourceService.authorizationForResources(list, role);
		role.setResources(null);
		roleService.updateRole(role);
	}
}
