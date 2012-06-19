package com.apusic.ebiz.smartorg.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.smartorg.dao.RoleDao;

@Service("smartorg_RoleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private CrudService curdService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private RoleDao roleDao;

	public List<Role> getAllRoles() {
		return this.queryService.findAll(Role.class);
	}

	@Transactional
	public void addRole(Role role){
		curdService.create(role);
	}

	@Transactional
	public void deleteRole(String roleName) {
		Role role = getRoleByName(roleName);
		if (role != null) {
			this.curdService.delete(role);
		}
	}

	@Transactional
	public void deleteRoleById(int id) {
		this.curdService.delete(Role.class, id);
	}

	public Page findRole4Page(GenericQueryObject query) {
		// TODO Auto-generated method stub
		return null;
	}

	public int findRoleCountByQueryParameter(GenericQueryObject query) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Role getRoleById(int id) {
		return this.curdService.retrieve(Role.class, id);
	}

	public Role getRoleByName(String roleName) {
		return roleDao.getRoleByName(roleName);
	}

	public boolean roleExists(String roleName) {
		return getRoleByName(roleName) != null;
	}

	@Transactional
	public void updateRole(Role role) {
		this.curdService.update(role);
	}

	@Transactional
	public void deleteAllRoles(Collection<Role> roles) {
		this.curdService.deleteAll(roles);
	}
}
