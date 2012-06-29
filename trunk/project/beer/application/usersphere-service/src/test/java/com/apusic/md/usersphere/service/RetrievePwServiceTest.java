package com.apusic.md.usersphere.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.UserService;
import com.apusic.md.model.usersphere.MemberType;
import com.apusic.md.model.usersphere.RetrievePW;
import com.apusic.md.model.usersphere.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-md-usersphere-service.xml", "classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-md-usersphere-service-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class RetrievePwServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private RetrievePwService retrievePwService;

	private void prepareUser(String userName){
		User u = new User();
		u.setName(userName);
		u.setPassword("admin");
		userService.addUser(u);

		UserProfile p = new UserProfile();
		p.setAddress("address");
		p.setEmail("xxx@apuisc.com");
		p.setMemberType(MemberType.PERSONAL);

		userProfileService.addUserProfile(p, u.getId());
	}

	@Test
	@Transactional
	public void submitRetrievePw(){
		this.prepareUser("RetrievePwServiceTest");
		User user = userService.getUserByName("RetrievePwServiceTest");
		UserProfile userProfile = userProfileService.getUserProfilebyUser(user);
		RetrievePW p = new RetrievePW();
		p.setName("RetrievePwServiceTest");
		retrievePwService.submitRetrievePw(p);
		retrievePwService.resetPassword(p, "xxxxxxxxxxxxx");
		User userByName = userService.getUserByName("RetrievePwServiceTest");
	}

	@Test
	@Transactional
	public void getUserProfilebyName(){
		this.prepareUser("RetrievePwServiceTest111");
		UserProfile userProfile = retrievePwService.getUserProfilebyName("RetrievePwServiceTest111");
		Assert.assertNotNull(userProfile);
	}

}
