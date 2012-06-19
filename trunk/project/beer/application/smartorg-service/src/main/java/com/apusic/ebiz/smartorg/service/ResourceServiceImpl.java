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
import com.apusic.ebiz.model.user.Resource;
import com.apusic.ebiz.smartorg.dao.ResourceDao;

@Service("smartorg_ResourceService")
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private CrudService curdService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private ResourceDao resourceDao;

	public List<Resource> getAllResources() {
		return queryService.findAll(Resource.class);
	}

	public List<Resource> getResourcesByExample(Resource resource){
	    return queryService.findByExample(resource);
	}
	
	public Resource getResourceById(int resourceId) {
		return curdService.retrieve(Resource.class, resourceId);
	}

	@Transactional
	public void addResource(Resource resource) {
		this.curdService.create(resource);
	}

	@Transactional
	public void deleteResourceById(int id) {
		this.curdService.delete(Resource.class,id);
	}

	public Page findResource4Page(GenericQueryObject query) {
		// TODO Auto-generated method stub
		return null;
	}

	public int findResourceCountByQueryParameter(GenericQueryObject query) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	public void updateResource(Resource resource) {
		this.curdService.update(resource);
	}

	@Transactional
	public void deleteAllResource(Collection<Resource> resoruces) {
		this.curdService.deleteAll(resoruces);
	}

	@Transactional
	public Resource getResourceByContextAndValue(String context, String value) {
		return resourceDao.getResourceByContextAndValue(context, value);
	}

	public List<Resource> findresourceByRoleId(int id) {
		return resourceDao.findresourceByRoleId(id);
	}
}
