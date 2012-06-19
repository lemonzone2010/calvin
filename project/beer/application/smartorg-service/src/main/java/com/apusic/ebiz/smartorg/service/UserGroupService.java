package com.apusic.ebiz.smartorg.service;

import java.util.List;

import com.apusic.ebiz.model.user.Group;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.SmartOrgException;

public interface UserGroupService {

	/**
	 *将用户添加到新的群组
	 *
	 * @param userName
	 * @param groupName
	 * @param isDelete 是否从原来的群组移除
	 * @throws SmartOrgException
	 */
	void addUserToGroup(String userName,String oldGroupName,String newGroupName,boolean isDelete)throws SmartOrgException;

	/**
	 * 从该群组移除该用户
	 *
	 * @param userName
	 * @param groupName
	 * @throws SmartOrgException
	 */
	void removeUserFromGroup(String userName,String groupName)throws SmartOrgException;

	/**
	 * 查看群组是否该用户
	 * @param userName
	 * @param groupName
	 * @return
	 */
	boolean isUserInGroup(String userName,String groupName);

	/**
	 * 获取用户所有的组织
	 * @param username
	 * @return
	 */
	 List<Group> getGroupsForUser(String username);

	 /**
	  * 删除指定群组和该群组的用户
	  * @param groupName
	 * @throws SmartOrgException
	  */
	 void deleteGroupAndUsers(String groupName) throws SmartOrgException;

	 /**
	  * 获取该群组下的所有用户
	  * @param groupName
	  * @return
	  */
	 List<User> getUsersFromGroup(String groupName);
}
