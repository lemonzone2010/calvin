package com.apusic.ebiz.framework.core.service;

import java.io.Serializable;
import java.util.Collection;

import com.apusic.ebiz.framework.core.dao.Page;

public interface AjaxRestService<T> {
	T create(T e);

	T retrieve(Serializable id);

	void update(T e);

	void delete(T e);

	void deleteAll(Collection<T> e);

	T findBy(String property, String value);

	Page<T> findPage(Page<T> p);
	
	void setEntityClass(Class<T> entityClass);
}
