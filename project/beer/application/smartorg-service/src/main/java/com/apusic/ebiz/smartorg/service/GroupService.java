package com.apusic.ebiz.smartorg.service;

import java.util.List;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.user.Group;
import com.apusic.ebiz.smartorg.SmartOrgException;

public interface GroupService {

	/**
	 * 添加群组
	 * @param group
	 */
	void addGroup(Group group)throws SmartOrgException;

	/**
	 *添加群组
	 * @param group
	 * @param parentName
	 */
	void addGroup(Group group,String parentName)throws SmartOrgException;

	/**
	 * 添加群组根据父群组的Id
	 * @param group
	 * @param parentId
	 */
	void addGroupByParentId(Group group,int parentId)throws SmartOrgException;

	/**
	 * 删除群组
	 * @param groupName
	 * @return
	 * @throws SmartOrgException
	 */
	void  deleteGroup(String groupName) throws SmartOrgException;

	/**
	 * 根据群组id删除该群组
	 * @param id
	 * @throws SmartOrgException
	 * @throws SmartOrgException
	 */
	void deleteGroupById(int id) throws SmartOrgException;

	/**
	 * 更新群组
	 * @param group
	 * @throws SmartOrgException
	 */
	void updateGroup(Group group);

	/**
	 * 判断群组是否存在
	 * @param groupName
	 * @return
	 */
	boolean groupExists(String groupName);

	/**
	 * 获取群组by Group Name
	 * @param groupName
	 * @return
	 */
	Group getGroupByName(String groupName);

	/**
	 *获取群组by Group Id
	 * @param groupId
	 * @return
	 */
	Group getGroupById(int groupId);

	/**
	 * 获取指定条件一页group
	 * @param query
	 * @return
	 */
	Page findGroup4Page(GenericQueryObject query);

	/**
	 * 返回指定条件的group数量
	 * @param query
	 * @return
	 */
	int findGroupCountByQueryParameter(GenericQueryObject query);

	/**
	 *获取指定群组的子群组
	 * @param groupName
	 * @return
	 * @throws SmartOrgException
	 */
	List<Group> getChildGroups(String groupName) throws SmartOrgException;

	/**
	 * 获取群组的父群组
	 * @param groupName
	 * @return
	 * @throws SmartOrgException
	 */
	Group getParentGroupByName(String groupName) throws SmartOrgException;
}
