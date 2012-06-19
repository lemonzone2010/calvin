package com.apusic.ebiz.smartorg.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.user.Role;

@Repository("smartorg_RoleDao")
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private QueryService queryService;

	public Role getRoleByName(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		List<Role> roles = this.queryService.findByExample(role);
		if (roles != null && roles.size() > 0) {
			return roles.get(0);
		}
		return null;
	}
}
