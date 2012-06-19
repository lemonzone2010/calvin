package com.apusic.ebiz.smartorg.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.apusic.ebiz.model.user.Resource;
import com.apusic.ebiz.model.user.Role;

@Service("smartorg_RoleResourceService")
public class RoleResourceServiceImpl implements RoleResourceService {

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;

	@Transactional
	public List<Resource> findResourceByRoleId(int roleId) {
		List<Resource> resources = new ArrayList<Resource>();
		Role role = this.roleService.getRoleById(roleId);
		if(role==null){
			return resources;
		}else{
			Set<Resource> setResources = role.getResources();
			Iterator<Resource> iterator = setResources.iterator();
			while(iterator.hasNext()){
				resources.add(iterator.next());
			}
			return resources;
		}
	}

	@Transactional
	public List<Role> findRolesbyResourceId(int resourceId) {
		List<Role> roles = new ArrayList<Role>();
		Resource resource = this.resourceService.getResourceById(resourceId);
		if(resource!=null){
			Set<Role> setRoles = resource.getRoles();
			Iterator<Role> iterator = setRoles.iterator();
			while(iterator.hasNext()){
				roles.add(iterator.next());
			}
			return roles;
		}
		return null;
	}

	@Transactional
	public void authorizationForResource(Resource resource, Role role) {
		resource.addRole(role);
	}

	@Transactional
	public void authorizationForResource(Resource resource,
			Collection<Role> roles) {
		Assert.notNull(roles, "roles cannot be empty");
		if(roles.size() > 0){
			for(Role r : roles){
				resource.addRole(r);
			}
		}
	}

	@Transactional
	public void authorizationForResources(Collection<Resource> resources,
			Role role) {
		role =  this.roleService.getRoleById(role.getId().intValue());
		Assert.notNull(resources, "resources cannot be empty");
		if(resources.size()>0){
			for(Resource r : resources){
				role.addResource(r);
			}
		}
	}

	@Transactional
	public void authorizationForResources(Collection<Resource> resources,
			Collection<Role> roles) {
		Assert.notNull(resources, "resources cannot be empty");
		Assert.notNull(roles, "roles cannot be empty");
		if(resources.size() > 0 && roles.size() > 0){
			for(Resource r : resources){
				for(Role item : roles){
					r.addRole(item);
				}
			}
		}
	}

	@Transactional
	public void unAuthorizationForResource(Resource resource, Role role) {
		resource.removeRole(role);
	}

	@Transactional
	public void unAuthorizationForResource(Resource resource,
			Collection<Role> roles) {
		Assert.notNull(roles, "roles cannot be empty");
		if(roles.size() > 0){
			for(Role r : roles){
				resource.removeRole(r);
			}
		}
	}

	@Transactional
	public void unAuthorizationForResources(Collection<Resource> resources,
			Role role) {
		role =  this.roleService.getRoleById(role.getId().intValue());
		Assert.notNull(resources, "resources cannot be empty");
		if(resources.size()>0){
			for(Resource r : resources){
				role.removeResource(r);
			}
		}
	}

	@Transactional
	public void unAuthorizationForResources(Collection<Resource> resources,
			Collection<Role> roles) {
		Assert.notNull(resources, "resources cannot be empty");
		Assert.notNull(roles, "roles cannot be empty");
		if(resources.size() > 0 && roles.size() > 0){
			for(Resource r : resources){
				for(Role item : roles){
					r.removeRole(item);
				}
			}
		}
	}
}
