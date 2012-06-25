package com.apusic.ebiz.framework.core.service;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.RepositoryFacade;
import com.apusic.ebiz.framework.core.model.IdEntity;

@Service
public class DefaultAjaxRestServiceImpl<T  extends IdEntity> implements AjaxRestService<T> {
	@Autowired
	protected RepositoryFacade repositoryFacade;

	@Override
	@Transactional
	public T create(T e) {
		return repositoryFacade.create(e);
	}

	@Override
	@Transactional
	public void delete(T e) {
		repositoryFacade.delete(e);
	}

	@Override
	@Transactional
	public void deleteAll(Collection<T> e) {
		repositoryFacade.deleteAll(e);
	}

	@Override
	@Transactional(readOnly=true)
	public T findBy(Class<T> entityClass,String property, Object value) {
		return repositoryFacade.findBy(entityClass, property, value);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<T> findPage(Class<T> entityClass,Page<T> p) {
		return repositoryFacade.findPage(entityClass, p);
	}

	@Override
	@Transactional(readOnly=true)
	public T retrieve(Class<T> entityClass,Serializable id) {
		return repositoryFacade.retrieve(entityClass, id);
	}

	@Override
	@Transactional
	public void update(T e) {
		repositoryFacade.update(e);
	}

}
