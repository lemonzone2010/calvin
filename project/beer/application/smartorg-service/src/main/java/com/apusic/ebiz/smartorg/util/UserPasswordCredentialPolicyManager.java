package com.apusic.ebiz.smartorg.util;

import com.apusic.ebiz.model.user.User;

public interface UserPasswordCredentialPolicyManager {

	/**
	 * 将密码进行加密
	 *
	 * @param password
	 * @return
	 */
	String encrypt(User user);
}
