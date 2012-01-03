package com.xia.quartz.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface PageQueryService {
	<T> T findBy(Class<T> clazz, String property, String value);

	<T> List<T> findListBy(Class<T> clazz, String property, String value);

	public <T> long getRowCount(Class<T> clazz);

	public long getRowCount(DetachedCriteria criteria);

	public <T> List<T> findAll(Class<T> clazz);

	public <T> long getRowCountByExample(T t);

	public <T> List<T> findByExample(T t);

}
