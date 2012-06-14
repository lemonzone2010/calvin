package com.apusic.ebiz.framework.core.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface PageQueryService {
	<T> T findBy(Class<T> clazz, String property, String value);

	<T> List<T> findListBy(Class<T> clazz, String property, String value);

	<T> Page<T> findPage(Class<T> clazz, Page<T> p);

	public <T> long getRowCount(Class<T> clazz);

	public long getRowCount(DetachedCriteria criteria);

	public <T> List<T> findAll(Class<T> clazz);

	public <T> long getRowCountByExample(T t);

	public <T> List<T> findByExample(T t);

	public <T> Page<T> findPageByExample(T e, Page<T> p);
}
