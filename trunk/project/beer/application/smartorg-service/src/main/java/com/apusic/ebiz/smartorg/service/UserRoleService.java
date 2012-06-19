package com.apusic.ebiz.smartorg.service;

import java.util.List;

import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.model.user.Role;

public interface UserRoleService {

	void grantRoleToUser(Role userRole, User user);

	void grantRoleToUser(int roleId, User user2);

	void grantRoleToUser(Role userRole, int userId);

	void grantRoleToUser(int roleId, int userId);

	void grantRolesToUsers(int[] roleIds, int[] userIds);

	void revokeRoleFromUser(Role userRole, User user);

	void revokeRoleFromUser(int roleId, User user2);

	void revokeRoleFromUser(Role userRole, int userId);

	void revokeRoleFromUser(int roleId, int userId);

	void revokeRolesFromUsers(int[] roleIds, int[] userIds);

	List<Role> findRolesByUserId(int userid);
}
