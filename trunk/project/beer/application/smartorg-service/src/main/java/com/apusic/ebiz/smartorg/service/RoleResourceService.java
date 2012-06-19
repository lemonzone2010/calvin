package com.apusic.ebiz.smartorg.service;

import java.util.Collection;
import java.util.List;

import com.apusic.ebiz.model.user.Resource;
import com.apusic.ebiz.model.user.Role;

public interface RoleResourceService {
	List<Resource> findResourceByRoleId(int roleId);

	List<Role> findRolesbyResourceId(int resourceId);

	/**
	 * 对一个集合资源授一个角色
	 * @param resources
	 * @param role
	 */
	void authorizationForResources(Collection<Resource> resources,Role role);

	/**
	 * 对一个集合资源授一个角色集合
	 * @param resources
	 * @param role
	 */
	void authorizationForResources(Collection<Resource> resources,Collection<Role> roles);

	/**
	 * 给一个资源授一个角色
	 * @param resource
	 * @param role
	 */
	void authorizationForResource(Resource resource,Role role);

	/**
	 * 给一个资源授一个角色集合
	 * @param resource
	 * @param roles
	 */
	void authorizationForResource(Resource resource,Collection<Role> roles);

	/**
	 * 取消一个集合资源的一个角色
	 * @param resources
	 * @param role
	 */
	void unAuthorizationForResources(Collection<Resource> resources,Role role);

	/**
	 * 取消一个集合资源的一个角色集合
	 * @param resources
	 * @param role
	 */
	void unAuthorizationForResources(Collection<Resource> resources,Collection<Role> roles);

	/**
	 * 取消一个资源的一个角色
	 * @param resource
	 * @param role
	 */
	void unAuthorizationForResource(Resource resource,Role role);

	/**
	 * 取消一个资源的一个角色集合
	 * @param resource
	 * @param roles
	 */
	void unAuthorizationForResource(Resource resource,Collection<Role> roles);
}
