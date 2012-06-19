package com.apusic.ebiz.smartorg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.user.Group;
import com.apusic.ebiz.smartorg.SmartOrgException;

@Service("smartorg_GroupService")
public class GroupServiceImpl implements GroupService {

	@Autowired
	private CrudService crudService;

	@Autowired
	private QueryService queryService;

	@Transactional
	public void addGroup(Group group) throws SmartOrgException {
		if(group.getParent()==null){
			throw new SmartOrgException("parent of group do not exist");
		}
		crudService.create(group);
	}

	@Transactional
	public void addGroup(Group group, String parentName)
			throws SmartOrgException {
		Group parentGroup = getGroupByName(parentName);
		if(parentGroup==null){
			throw new SmartOrgException(parentName + " do not exist");
		}
		group.setParent(parentGroup);
		crudService.create(group);
	}

	@Transactional
	public void addGroupByParentId(Group group, int parentId)
			throws SmartOrgException {
		Group parentGroup = getGroupById(parentId);
		if(parentGroup==null){
			throw new SmartOrgException("group Id " + parentId + " do not exist");
		}
		group.setParent(parentGroup);
		crudService.create(group);
	}

	@Transactional
	public void deleteGroup(String groupName) throws SmartOrgException{
		Group group = getGroupByName(groupName);
		if(group!=null && group.getGroups()!=null && group.getGroups().size() > 0){
			throw new SmartOrgException(groupName + " has child group,please delete child groups");
		}
		if(group!=null){
			crudService.delete(group);
		}
	}

	@Transactional
	public void deleteGroupById(int id) throws SmartOrgException{
		Group group = getGroupById(id);
		if(group !=null && group.getGroups()!=null && group.getGroups().size()>0){
			throw new SmartOrgException(id + " of group has child groups,please delete child groups");
		}
		this.crudService.delete(Group.class, id);
	}

	public Page findGroup4Page(GenericQueryObject query) {
		// TODO Auto-generated method stub
		return null;
	}

	public int findGroupCountByQueryParameter(GenericQueryObject query) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Group getGroupById(int groupId) {
		return crudService.retrieve(Group.class, groupId);
	}

	public Group getGroupByName(String groupName) {
		Assert.notNull(groupName);
		Group g = new Group();
		g.setName(groupName);
		List<Group> groups = queryService.findByExample(g);
		if(groups!=null && groups.size() > 0){
			return groups.get(0);
		}
		return null;
	}

	public boolean groupExists(String groupName) {
		return getGroupByName(groupName)!=null;
	}

	@Transactional
	public void updateGroup(Group group){
		crudService.update(group);
	}

	@Transactional
	public List<Group> getChildGroups(String groupName)
			throws SmartOrgException {
		Group parentGroup = this.getGroupByName(groupName);
		if(parentGroup==null){
			throw new SmartOrgException(groupName + " do not exists");
		}
		Set<Group> childGroups = parentGroup.getGroups();
		if(childGroups!=null && childGroups.size()>0){
			List<Group> result = new ArrayList<Group>();
			for(Group item : childGroups){
				if(parentGroup.getId()!=item.getId()){
					result.add(item);
				}
			}
			return result;
		}
		return null;
	}

	@Transactional
	public Group getParentGroupByName(String groupName) throws SmartOrgException{
		Group group = this.getGroupByName(groupName);
		if(group==null){
			throw new SmartOrgException(groupName + " do not Exists");
		}
		return group.getParent();
	}
}
