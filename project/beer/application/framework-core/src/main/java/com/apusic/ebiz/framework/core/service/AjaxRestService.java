package com.apusic.ebiz.framework.core.service;

import java.io.Serializable;
import java.util.Collection;

import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.model.IdEntity;

public interface AjaxRestService<T extends IdEntity> {
	T create(T e);

	T retrieve(Class<T> entityClass,Serializable id);

	void update(T e);

	void delete(T e);

	void deleteAll(Collection<T> e);

	T findBy(Class<T> entityClass, String property, Object value);

	Page<T> findPage(Class<T> entityClass, Page<T> p);
}
