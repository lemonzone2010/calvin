package com.apusic.ebiz.smartorg.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.model.user.Group;
import com.apusic.ebiz.smartorg.SmartOrgException;
import com.apusic.ebiz.smartorg.service.GroupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-smartorg-service-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class GroupServiceTest {

	@Autowired
	private GroupService groupService;

	@Test
	public void addGroup() throws SmartOrgException{
		Group g = new Group();
		g.setAlias("中间件");
		g.setDesc("中间件公司");
		g.setName("root");
		g.setParent(g);
		groupService.addGroup(g);
		Assert.assertTrue(g.getId()>0);
	}

	@Test
	public void addGroup2() throws SmartOrgException{
		Group g = new Group();
		g.setAlias("实施服务部");
		g.setDesc("实施服务部");
		g.setName("shishifuwubu");
		groupService.addGroup(g, "root");
		g.getId();
	}

	@Test
	public void addGroupByParentId() throws SmartOrgException{
		Group g = new Group();
		g.setAlias("研发中心");
		g.setDesc("研发中心");
		g.setName("yanfazhongxin");
		groupService.addGroupByParentId(g, groupService.getGroupByName("shishifuwubu").getId());
	}

	@Test
	public void deleteGroup() throws SmartOrgException{
		groupService.deleteGroup("yanfazhongxin");
	}

	@Test
	public void deleteGroupbyId() throws SmartOrgException{
		Group groupByName = groupService.getGroupByName("shishifuwubu");
		groupService.deleteGroupById(groupByName.getId());
	}

	@Test
	public void updateGroup(){
		Group groupById = groupService.getGroupByName("root");
		groupById.setAlias("中间件有限公司");
		groupService.updateGroup(groupById);
	}

	@Test
	public void groupExists(){
		Assert.assertTrue(groupService.groupExists("root"));
	}
}
