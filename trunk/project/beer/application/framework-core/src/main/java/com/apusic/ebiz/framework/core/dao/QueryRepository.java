package com.apusic.ebiz.framework.core.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface QueryRepository {
	<T> T findBy(Class<T> clazz, String property, String value);

	<T> List<T> findListBy(Class<T> clazz, String property, String value);

	<T> Page<T> findPage(Class<T> clazz, Page<T> p);

	public <T> long getRowCount(Class<T> clazz);

	public long getRowCount(DetachedCriteria criteria);

	public <T> List<T> findAll(Class<T> clazz);

	public <T> long getRowCountByExample(T t);

	public <T> List<T> findByExample(T t);

	public <T> Page<T> findPageByExample(T e, Page<T> p);

	public <T> List<T> findRange(Class<T> entity, final int start, final int size);

	public <T> List<T> findBy(DetachedCriteria criteria);

	public <T> List<T> findBy(DetachedCriteria criteria, final int first, final int count);

	public <T> Page<T> findPageBy(DetachedCriteria criteria, final int size);
}
