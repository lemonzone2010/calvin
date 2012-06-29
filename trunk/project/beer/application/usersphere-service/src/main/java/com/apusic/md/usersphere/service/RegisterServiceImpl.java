package com.apusic.md.usersphere.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.RoleService;
import com.apusic.ebiz.smartorg.service.UserService;
import com.apusic.md.model.usersphere.Contact;
import com.apusic.md.model.usersphere.MemberType;
import com.apusic.md.model.usersphere.UserProfile;
import com.apusic.md.usersphere.util.RoleConstant;

@Service("usersphere_RegisterService")
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("smartorg_RoleService")
	private RoleService roleService;

	@Autowired
	private UserProfileService profileService;

	@Autowired
	private ContactService contactService;

	@Transactional
	public void register(User user, UserProfile userProfile) {
		user.setDisbled(true);
		//add user role
		this.addRole(user, RoleConstant.USER);
		this.addRole(user, RoleConstant.MEMBER);
		this.userService.addUser(user);
		userProfile.setMemberType(MemberType.PERSONAL);
		userProfile.setCreateTime(new Date());
		this.profileService.addUserProfile(userProfile, user.getId());

	}

	@Transactional
	public void register(User user, UserProfile userProfile, Contact contact) {
		user.setDisbled(false);
		this.addRole(user, RoleConstant.USER);
		this.addRole(user, RoleConstant.SHOP);
		this.userService.addUser(user);
		userProfile.setMemberType(MemberType.UNATHORIZED_SHOP);
		userProfile.setCreateTime(new Date());
		this.profileService.addUserProfile(userProfile, user.getId());
		this.contactService.addContact(contact, user.getId());
	}

	private void addRole(User user, String roleName){
		Role role = roleService.getRoleByName(roleName);
		Assert.notNull(role, "角色 <" + RoleConstant.USER + ">不存在，请检查基础配置！");
		user.addRole(role);
	}
}
