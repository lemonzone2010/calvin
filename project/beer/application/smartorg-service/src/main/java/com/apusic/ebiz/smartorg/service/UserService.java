package com.apusic.ebiz.smartorg.service;

import java.util.Collection;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.SmartOrgException;

/**
 * 用户服务接口
 * @author xuzhengping
 *
 */
public interface UserService {

	/**
	 * 添加一个没有群组的用户
	 *
	 * @param user
	 */
	void addUser(User user);

	/**
	 * 添加一个用户，需要指定该用户所在的组织
	 *
	 * @param user
	 * @param groupName
	 * @throws SmartOrgException
	 */
	void addUser(User user, String groupName) throws SmartOrgException;

	/**
	 * 删除userName的用户
	 *
	 * @param userName
	 * @return
	 * @throws SmartOrgException
	 */
	void deleteUser(String userName);

	/**
	 * 根据用户Id删除用户
	 * @param id
	 * @return
	 * @throws SmartOrgException
	 */
	void deleteUserById(int id);

	/**
	 *更新用户
	 *
	 * @param user
	 * @throws SmartOrgException
	 */
	void updateUser(User user) throws SmartOrgException;

	/**
	 * 判断用户是否存在
	 *
	 * @param userName
	 * @return
	 */
	boolean userExists(String userName);

	/**
	 * 获取一个用户
	 *
	 * @param userName
	 * @return
	 */
	User getUserByName(String userName);

	/**
	 * 根据用户id获取用户
	 *
	 * @param id
	 * @return
	 */
	User getUserById(int id);

	/**
	 *根据查询条件返回一页用户
	 *
	 * @param query
	 * @return
	 */
	Page findUser4Page(GenericQueryObject query);

	/**
	 * 返回指定条件的用户数量
	 * @param query
	 * @return
	 */
	long findUserCountByQueryParameter(GenericQueryObject query);

	/**
	 * 修改密码，不需要通过验证旧密码
	 * @param userName
	 * @param password
	 * @throws SmartOrgException
	 */
	void setPassword(String userName, String password) throws SmartOrgException;

	/**
	 * 需要验证旧密码，如果旧密码不合法，抛出异常
	 * @param userName
	 * @param oldPwd
	 * @param newPwd
	 * @throws SmartOrgException
	 */
	void changePassword(String userName,String oldPwd,String newPwd) throws SmartOrgException;

	/**
	 * 验证密码是否正确
	 * @param userName
	 * @param password
	 * @throws SmartOrgException
	 */
	void validatePassword(String userName,String password) throws SmartOrgException;

	/**
	 *批量删除用户
	 * @param c
	 */
	void deleteAllUsers(Collection<User> c);

	/**
	 *禁用用户
	 * @param id
	 */
	void disableByUserId(int id);

	/**
	 *激活用户
	 * @param id
	 */
	void enableByUserId(int id);
}
