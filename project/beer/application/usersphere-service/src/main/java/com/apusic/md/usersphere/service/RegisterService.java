package com.apusic.md.usersphere.service;

import com.apusic.ebiz.model.user.User;
import com.apusic.md.model.usersphere.Contact;
import com.apusic.md.model.usersphere.UserProfile;

public interface RegisterService {

	/**
	 *普通会员注册
	 * @param user
	 * @param userProfile
	 */
	void register(User user,UserProfile userProfile);

	/**
	 *商铺会员注册
	 * @param user
	 * @param userProfile
	 * @param contact
	 */
	void register(User user,UserProfile userProfile,Contact contact);

}
