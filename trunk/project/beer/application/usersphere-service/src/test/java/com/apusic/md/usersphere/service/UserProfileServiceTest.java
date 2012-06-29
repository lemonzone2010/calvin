package com.apusic.md.usersphere.service;

import org.hibernate.criterion.MatchMode;
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
import com.apusic.ebiz.smartorg.service.RoleService;
import com.apusic.ebiz.smartorg.service.UserService;
import com.apusic.md.model.usersphere.MemberType;
import com.apusic.md.model.usersphere.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-md-usersphere-service.xml", "classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-md-usersphere-service-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class UserProfileServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserProfileService userProfileService;

	@Test
	public void addUserProfile(){
		User user = new User();
		user.setName("UserProfileServiceTest-name");
		user.setPassword("admin");
		userService.addUser(user);

		UserProfile u = new UserProfile();
		u.setAddress("address");
		u.setMemberType(MemberType.PERSONAL);
		userProfileService.addUserProfile(u, user.getId());
	}

	@Test
	public void udpateUserProfile(){
		UserProfile u = userProfileService.getUserProfile(1);
		u.setAddress("xxxxx");
		userProfileService.updateUserProfile(u);
	}

	@Test
	public void deleteUserProfile(){
		userProfileService.deleteUserProfile(1);
		User userById = userService.getUserById(1);
		Assert.assertNotNull(userById);
	}

	@Test
	public void getUserProfiles(){
		User user = new User();
		user.setName("UserProfileServiceTest-name11");
		user.setPassword("admin");
		userService.addUser(user);
		UserProfile u = new UserProfile();
		u.setAddress("address");
		u.setMemberType(MemberType.PERSONAL);
		userProfileService.addUserProfile(u, user.getId());
		User loadedUser = userService.getUserByName("UserProfileServiceTest-name11");
		UserProfile userProfile = userProfileService.getUserProfilebyUser(loadedUser);
		Assert.assertEquals("address", userProfile.getAddress());
	}

	@Test
	public void findUserProfile4Page(){
		User user = new User();
		user.setName("abc-name11");
		user.setPassword("abc");
		userService.addUser(user);
		UserProfile u = new UserProfile();
		u.setFullName("abcd");
		u.setAddress("address");
		u.setMemberType(MemberType.PERSONAL);
		userProfileService.addUserProfile(u, user.getId());

		GenericQueryObject queryObject = new GenericQueryObject(UserProfile.class);
		queryObject.like("fullName", "abc", MatchMode.ANYWHERE);
		Page<UserProfile> userProfilePage = userProfileService.findUserProfile4Page(queryObject);
		Assert.assertTrue(userProfilePage.getTotal()>0);
		Assert.assertTrue(userProfilePage.getObjects().size()>0);
	}

	public void findMemberUser4Page(){
		GenericQueryObject query=new GenericQueryObject(UserProfile.class);
		Page<UserProfile> userProfiles=userProfileService.findMemberUser4Page(query);
		Assert.assertTrue(userProfiles.getObjects().size()==1);
		for (UserProfile item : userProfiles.getObjects()) {
			System.out.println(item.getUser().getName());
		}
	}

}
