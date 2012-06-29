package com.apusic.md.usersphere.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.RoleService;
import com.apusic.md.model.usersphere.Contact;
import com.apusic.md.model.usersphere.MemberType;
import com.apusic.md.model.usersphere.UserProfile;
import com.apusic.md.usersphere.util.RoleConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-md-usersphere-service.xml", "classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-md-usersphere-service-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class RegisterServiceTest {

	@Autowired
	private RegisterService registerService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserProfileService userProfileService;

	@Before
	@Transactional
	public void addRoles(){
		Role userRole = new Role();
		userRole.setName(RoleConstant.USER);
		roleService.addRole(userRole);

		Role memberRole = new Role();
		memberRole.setName(RoleConstant.MEMBER);
		roleService.addRole(memberRole);

		Role shopRole = new Role();
		shopRole.setName(RoleConstant.SHOP);
		roleService.addRole(shopRole);
	}

	@Test
	@Transactional
	public void register1(){

		User user = new User();
		user.setName("admin");
		user.setPassword("admin");
		UserProfile userProfile = new UserProfile();
		userProfile.setAddress("address");
		registerService.register(user, userProfile);


	}

	@Test
	@Transactional
	public void register2(){
		User user = new User();
		user.setName("admin1");
		user.setPassword("admin");
		UserProfile userProfile = new UserProfile();
		userProfile.setAddress("address");
		Contact contact = new Contact();
		contact.setAddress("address");
		registerService.register(user, userProfile, contact);

		UserProfile loadedUserProfile = userProfileService.getUserProfilebyUser(user);
		Assert.assertEquals(MemberType.UNATHORIZED_SHOP, loadedUserProfile.getMemberType());

	}



}
