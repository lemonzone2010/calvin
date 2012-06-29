package com.apusic.md.usersphere.service;

import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.Page;
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
public class ApprovalServiceTest {

	@Autowired
	private RegisterService registerService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private ApprovalService approvalService;

	@Autowired
	private RoleService roleService;

	private User user;

	@Before
	public void register(){
		Role userRole = new Role();
		userRole.setName(RoleConstant.USER);
		roleService.addRole(userRole);

		Role memberRole = new Role();
		memberRole.setName(RoleConstant.MEMBER);
		roleService.addRole(memberRole);

		Role shopRole = new Role();
		shopRole.setName(RoleConstant.SHOP);
		roleService.addRole(shopRole);

		user = new User();
		int random = new Random().nextInt();
		user.setName("shop"+random);
		user.setPassword("shop");
		UserProfile userProfile = new UserProfile();
		userProfile.setAddress("address");
		userProfile.setEmail("hufeng@apusic.net");
		Contact contact = new Contact();
		contact.setAddress("address");

		//注册一个商家用户
		registerService.register(user, userProfile, contact);

		user = new User();
		random = new Random().nextInt();
		user.setName("user"+random);
		user.setPassword("user");
		userProfile = new UserProfile();
		userProfile.setAddress("user");
		userProfile.setEmail("hufeng@apusic.net");
		//注册一个用户
		registerService.register(user, userProfile);

	}

	@Test
	@Transactional
	public void approve(){
		UserProfile userProfile = userProfileService.getUserProfilebyUser(user);
		approvalService.approve(userProfile);
		Assert.assertEquals(MemberType.SHOP, userProfile.getMemberType());
		Assert.assertFalse(userProfile.getUser().isDisbled());
	}

	@Test
	@Transactional
	public void cancel(){
		UserProfile userProfile = userProfileService.getUserProfilebyUser(user);

		approvalService.cancel(userProfile);
		Assert.assertEquals(userProfile.getMemberType(), MemberType.CACELLED_SHOP);
	}

	@Test
	public void getAwaitingApprovalShopUsers(){
		Page<UserProfile> page = approvalService.getAwaitingApprovalShopUsers();
		List<UserProfile> userProfiles = page.getObjects();
		Assert.assertTrue(userProfiles.size()>0);
		for(UserProfile userProfile:userProfiles){
			Assert.assertEquals(userProfile.getMemberType(), MemberType.UNATHORIZED_SHOP);
		}
	}


}
