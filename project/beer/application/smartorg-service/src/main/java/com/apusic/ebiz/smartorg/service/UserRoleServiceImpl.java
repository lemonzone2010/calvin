package com.apusic.ebiz.smartorg.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.model.user.User;

@Service("smartorg_UserRoleService")
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private CrudService crudService;

	@Transactional
	public void grantRoleToUser(Role userRole, User user) {
		user.addRole(userRole);
		crudService.update(user);
	}

	@Transactional
	public void grantRoleToUser(int roleId, User user2) {
		user2.addRoleById(Role.class, roleId);
		crudService.update(user2);
	}

	@Transactional
	public void grantRoleToUser(Role userRole, int userId) {
		User user = crudService.retrieve(User.class, userId);
		user.addRole(userRole);
		crudService.update(user);
	}

	@Transactional
	public void grantRoleToUser(int roleId, int userId) {
		User user = crudService.retrieve(User.class, userId);
		Role userRole = crudService.retrieve(Role.class, roleId);
		user.addRole(userRole);
		crudService.update(user);
	}

	@Transactional
	public void grantRolesToUsers(int[] roleIds, int[] userIds) {
		if (roleIds==null || roleIds.length==0){
			return;
		}
		if (userIds == null || userIds.length ==0){
			return;
		}
		for (int roleId : roleIds){
			for(int userId : userIds){
				grantRoleToUser(roleId, userId);
			}
		}
	}

	@Transactional
	public List<Role> findRolesByUserId(int userid) {
		List<Role> roles = new ArrayList<Role>();
		User user = this.crudService.retrieve(User.class, userid);
		if(user!=null){
			Set<Role> setRoles = user.getRoles();
			Iterator<Role> iterator = setRoles.iterator();
			while(iterator.hasNext()){
				roles.add(iterator.next());
			}
			return roles;
		}
		return null;
	}

	@Transactional
	public void revokeRoleFromUser(Role userRole, User user) {
		user.removeRole(userRole);
		crudService.update(user);
	}

	@Transactional
	public void revokeRoleFromUser(int roleId, User user2) {
		user2.removeRoleById(Role.class, roleId);
		crudService.update(user2);
	}

	@Transactional
	public void revokeRoleFromUser(Role userRole, int userId) {
		User user = crudService.retrieve(User.class, userId);
		user.removeRole(userRole);
		crudService.update(user);
	}

	@Transactional
	public void revokeRoleFromUser(int roleId, int userId) {
		User user = crudService.retrieve(User.class, userId);
		Role userRole = crudService.retrieve(Role.class, roleId);
		user.removeRole(userRole);
		crudService.update(user);
	}

	@Transactional
	public void revokeRolesFromUsers(int[] roleIds, int[] userIds) {
		if (roleIds==null || roleIds.length==0){
			return;
		}
		if (userIds == null || userIds.length ==0){
			return;
		}
		for (int roleId : roleIds){
			for(int userId : userIds){
				revokeRoleFromUser(roleId, userId);
			}
		}
	}
}
