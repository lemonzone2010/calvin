package com.apusic.ebiz.smartorg.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.smartorg.SmartOrgException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-smartorg-service-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class UserGroupServiceTest {

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private GroupService groupService;

	@Test
	public void deleteGroupAndUsers() throws SmartOrgException{
		if(this.groupService.groupExists("test")){
			this.userGroupService.deleteGroupAndUsers("test");
		}
	}
}
