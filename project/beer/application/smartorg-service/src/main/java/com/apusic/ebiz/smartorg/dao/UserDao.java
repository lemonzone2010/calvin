package com.apusic.ebiz.smartorg.dao;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.user.User;


public interface UserDao {
	Page<User> findUserByRole(String roleName);

	Page findUserByParameter(GenericQueryObject para);

	User getUserByName(String userName);
}
