package com.apusic.ebiz.smartorg.service;

import java.util.List;

import org.junit.Assert;
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
public class ResourceServiceTest {
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;

	@Before
	public void before(){
		Role role = new Role();
		role.setAlias("aaa");
		role.setDesc("aaaa");
		role.setName("admin");
		this.roleService.addRole(role);
	}
	
	@Test
	public void addResource(){
		Resource r= new Resource();
		r.setDesc("会员中心首页");
		r.setPriority(0);
		r.setResContext("member-center");
		r.setResName("会员中心首页");
		r.setResValue("/membercenter/homepage.faces");
		this.resourceService.addResource(r);
	}

	@Test
	public void findResourceByRoleId(){
		Role role = roleService.getAllRoles().get(1);
		Resource r= new Resource();
		r.setDesc("会员中心首页");
		r.setPriority(0);
		r.setResContext("member-center");
		r.setResName("会员中心首页");
		r.setResValue("/membercenter/homepage.faces");
		r.addRole(role);
		resourceService.addResource(r);
		List<Resource> resources = resourceService.findresourceByRoleId(role.getId());
		Assert.assertTrue(resources.size() > 0);
	}
}
