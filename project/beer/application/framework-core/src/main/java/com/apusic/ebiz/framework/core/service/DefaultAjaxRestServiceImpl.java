package com.apusic.ebiz.framework.core.service;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.PageQueryService;
import com.apusic.ebiz.framework.core.util.Reflections;

@Service
public class DefaultAjaxRestServiceImpl<T> implements AjaxRestService<T> {
	@Autowired
	private CrudService crudService;
	@Autowired
	private PageQueryService pageQueryService;
	protected Class<T> entityClass;

	public DefaultAjaxRestServiceImpl() {
		this.entityClass = Reflections.getSuperClassGenricType(getClass());
	}

	@Override
	@Transactional
	public T create(T e) {
		return crudService.create(e);
	}

	@Override
	@Transactional
	public void delete(T e) {
		crudService.delete(e);
	}

	@Override
	@Transactional
	public void deleteAll(Collection<T> e) {
		crudService.deleteAll(e);
	}

	@Override
	public T findBy(String property, String value) {
		return pageQueryService.findBy(entityClass, property, value);
	}

	@Override
	public Page<T> findPage(Page<T> p) {
		return pageQueryService.findPage(entityClass, p);
	}

	@Override
	public T retrieve(Serializable id) {
		return crudService.retrieve(entityClass, id);
	}

	@Override
	@Transactional
	public void update(T e) {
		crudService.update(e);
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

}
