package com.apusic.ebiz.smartorg.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.SmartOrgException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-smartorg-service-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	public void addUser(){
		User user = new User();
		user.setDisbled(true);
		user.setName("user");
		user.setPassword("111111");
		user.setUserType("test");
		if(!this.userService.userExists("user")){
			this.userService.addUser(user);
		}
	}

	@Test
	public void addUser2() throws SmartOrgException{
		User user = new User();
		user.setDisbled(true);
		user.setName("user1");
		user.setPassword("111111");
		user.setUserType("test");
		if(!this.userService.userExists("user1")){
			//this.userService.addUser(user,"root");
		}
	}

	@Test
	public void deleteUser(){
		this.userService.deleteUser("user1");
	}

	@Test
	public void getUserByName(){
		Assert.assertNotNull(this.userService.getUserByName("user"));
	}

	@Test
	public void changePassword() throws SmartOrgException{
		//this.userService.changePassword("user", "111111", "897456");
	}

	@Test
	public void validatePassword() throws SmartOrgException{
		userService.validatePassword("user", "111111");
	}

	@Test
	public void findUserCountByQueryParameter(){
		GenericQueryObject genericQueryObject = new GenericQueryObject(User.class);
		genericQueryObject.isNotNull("name");
		long cout = userService.findUserCountByQueryParameter(genericQueryObject);
		Assert.assertTrue(cout>0);
	}

	@Test
	public void findUser4Page(){
		GenericQueryObject genericQueryObject = new GenericQueryObject(User.class);
		genericQueryObject.isNotNull("name");
		Page<User> page = userService.findUser4Page(genericQueryObject);
		Assert.assertTrue(page.getObjects().size()>0);
	}

	@Test
	public void disableByUserId(){
		User user=userService.getUserByName("user");
		userService.disableByUserId(user.getId());
		user=userService.getUserById(user.getId());
		Assert.assertTrue(user.isDisbled()==false);

	}

	@Test
	public void enableByUserId(){
		User user=userService.getUserByName("user");
		userService.enableByUserId(user.getId());
		user=userService.getUserById(user.getId());
		Assert.assertTrue(user.isDisbled()==true);

	}

}