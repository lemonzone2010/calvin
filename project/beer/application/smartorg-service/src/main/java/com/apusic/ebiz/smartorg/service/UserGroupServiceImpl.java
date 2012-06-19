package com.apusic.ebiz.smartorg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.apusic.ebiz.model.user.Group;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.SmartOrgException;

@Service("smartorg_UserGroupService")
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	public void addUserToGroup(String userName, String oldGroupName,
			String newGroupName, boolean isDelete) throws SmartOrgException {
		// TODO Auto-generated method stub

	}

	public List<Group> getGroupsForUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isUserInGroup(String userName, String groupName) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeUserFromGroup(String userName, String groupName)
			throws SmartOrgException {
		// TODO Auto-generated method stub

	}

	@Transactional
	public void deleteGroupAndUsers(String groupName) throws SmartOrgException {
		Assert.notNull(groupName);
		Group group = this.groupService.getGroupByName(groupName);
		Set<User> users = group.getUsers();
		if (users != null && users.size() > 0) {
			for(User u : users){
				if(u.getGroups() != null && u.getGroups().size() ==1){//当用户在多个群组下面，就不能删除该用户
					this.userService.deleteUserById(u.getId().intValue());
				}
			}
		}
		this.groupService.deleteGroupById(group.getId().intValue());
	}

	@Transactional
	public List<User> getUsersFromGroup(String groupName) {
		Assert.notNull(groupName);
		Group group = this.groupService.getGroupByName(groupName);
		if(group!=null){
			Set<User> users = group.getUsers();
			if(users!=null && users.size()>0){
				List<User> result = new ArrayList<User>();
				for(User item : users){
					result.add(item);
				}
				return result;
			}
		}
		return null;
	}
}
