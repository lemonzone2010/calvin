package com.apusic.md.usersphere.dao;

import com.apusic.ebiz.model.user.User;
import com.apusic.md.model.usersphere.UserProfile;

public interface UserProfileDao {
	UserProfile getUserProfilebyUser(User user);
}
