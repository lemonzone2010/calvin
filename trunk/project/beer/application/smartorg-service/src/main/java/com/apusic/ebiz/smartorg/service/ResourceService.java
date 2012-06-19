package com.apusic.ebiz.smartorg.service;

import java.util.Collection;
import java.util.List;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.user.Resource;

public interface ResourceService {

	/**
	 * 添加资源
	 * @param Resource
	 */
	void addResource(Resource resource);

	/**
	 * 删除资源
	 * @param id
	 */
	void deleteResourceById(int id);

	/**
	 *更新资源
	 * @param resource
	 */
	void updateResource(Resource resource);

	/**
	 * 获取资源
	 * @param resourceId
	 * @return
	 */
	Resource getResourceById(int resourceId);

	/**
	 * 通过context和value获取唯一的资源
	 * @param context
	 * @param value
	 * @return
	 */
	Resource getResourceByContextAndValue(String context,String value);

	/**
	 * 获取所有资源
	 * @return
	 */
	List<Resource> getAllResources();

	/**
     *查询指定条件的一页角色
     * @param query
     * @return
     */
    Page findResource4Page(GenericQueryObject query);

    /**
     * 查询指定条件的角色个数
     * @param query
     * @return
     */
    int findResourceCountByQueryParameter(GenericQueryObject query);

    /**
     * 批量删除资源
     * @param resoruces
     */
    void deleteAllResource(Collection<Resource> resoruces);

    /**
     * 根据角色ID查找该角色拥有的资源
     * @param id
     * @return
     */
	List<Resource> findresourceByRoleId(int id);
	
	/**
	 * 根据资源条件过滤
	 * @param resource
	 * @return
	 */
	List<Resource> getResourcesByExample(Resource resource);
}
