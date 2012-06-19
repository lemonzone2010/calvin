package com.apusic.ebiz.smartorg.service;

import java.util.Collection;
import java.util.List;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.user.Role;

public interface RoleService {
	/**
	 *添加角色
	 *
	 * @param role
	 */
	void addRole(Role role);

	/**
	 *根据角色名删除角色
	 *
	 * @param roleName
	 */
    void deleteRole(String roleName);

    /**
     * 根据角色ID删除角色
     *
     * @param id
     */
    void deleteRoleById(int id);

    /**
     * 更新用户角色
     * @param role
     */
    void updateRole(Role role);

    /**
     * 判断角色是否存在
     *
     * @param roleName
     * @return
     */
    boolean roleExists(String roleName);

    /**
     *根据角色Id获取role
     * @param id
     * @return
     */
    Role getRoleById(int id);

    /**
     * 根据角色名获取角色
     * @param roleName
     * @return
     */
    Role getRoleByName(String roleName);

    /**
     * 获取所有的角色
     * @return
     */
    List<Role> getAllRoles();

    /**
     *查询指定条件的一页角色
     * @param query
     * @return
     */
    Page findRole4Page(GenericQueryObject query);

    /**
     * 查询指定条件的角色个数
     * @param query
     * @return
     */
    int findRoleCountByQueryParameter(GenericQueryObject query);

    /**
     * 批量删除角色
     * @param roles
     */
    void deleteAllRoles(Collection<Role> roles);
}
