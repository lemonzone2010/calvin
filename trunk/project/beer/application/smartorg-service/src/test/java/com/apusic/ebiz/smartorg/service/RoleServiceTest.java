package com.apusic.ebiz.smartorg.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.smartorg.SmartOrgException;
import com.apusic.ebiz.smartorg.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-smartorg-service-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class RoleServiceTest {

	@Autowired
	private RoleService roleService;

	@Test
	public void addRole() throws SmartOrgException{
		Role role = new Role();
		role.setAlias("管理员");
		role.setDesc("admin");
		role.setName("admin");
		if(!roleService.roleExists("admin")){
			roleService.addRole(role);
		}
	}

	@Test
	public void update(){
		Role role = this.roleService.getRoleByName("admin");
		if(role!=null){
			role.setDesc("管理员角色");
			this.roleService.updateRole(role);
		}
	}


	@Test
	public void deleteRole(){
		this.roleService.deleteRole("admin");
	}
}
