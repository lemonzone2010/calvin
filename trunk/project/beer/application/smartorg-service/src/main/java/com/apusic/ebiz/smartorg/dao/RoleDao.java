package com.apusic.ebiz.smartorg.dao;

import com.apusic.ebiz.model.user.Role;

public interface RoleDao {

	Role getRoleByName(String roleName);
}
