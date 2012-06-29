package com.apusic.md.usersphere.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.UserService;
import com.apusic.md.model.usersphere.MemberType;
import com.apusic.md.model.usersphere.UserProfile;
import com.apusic.md.usersphere.service.UserProfileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-md-usersphere-service.xml", "classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-md-usersphere-service-test.xml" })
@Transactional
public class UserProfileDaoTest {

	@Autowired
	private UserProfileDao userProfileDao;

	@Autowired
	private UserService userService;

	@Autowired
	private UserProfileService userProfileService;

	@Before
	public void init(){
		User u = new User();
		u.setName("UserProfileDaoTest");
		u.setPassword("admin");
		userService.addUser(u);
		UserProfile p = new UserProfile();
		p.setAddress("address");
		p.setMemberType(MemberType.PERSONAL);
		userProfileService.addUserProfile(p, u.getId());
	}

	@Test
	public void getUserProfilebyUser(){
		User user = userService.getUserByName("UserProfileDaoTest");
		UserProfile p = userProfileDao.getUserProfilebyUser(user);
		Assert.assertNotNull(p);
	}
}
