package com.apusic.ebiz.framework.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.apusic.ebiz.framework.core.model.IdEntity;

public interface RepositoryFacade {

	public <T> T findBy(Class<T> clazz, String property, Object value);

	public <T> List<T> findListBy(Class<T> clazz, String property, Object value);

	public <T> Page<T> findPage(Class<T> clazz, Page<T> p);

	public <T> long getRowCount(Class<T> clazz);

	public long getRowCount(DetachedCriteria criteria);

	public <T> List<T> findAll(Class<T> clazz);

	public <T> long getRowCountByExample(T t);

	public <T> List<T> findByExample(T t);

	public <T> Page<T> findPageByExample(T e, Page<T> p);

	public <T> List<T> findRange(Class<T> entity, int start, int size);

	public <T> List<T> findBy(DetachedCriteria criteria);

	public <T> List<T> findBy(DetachedCriteria criteria, int first, int count);

	public <T> Page<T> findPageBy(DetachedCriteria criteria, int size);

	public <E extends IdEntity> E create(E e);

	public <E, T extends Serializable> E retrieve(Class<E> clazz, T id);

	public <E extends IdEntity> void update(E e);

	public void delete(Object e);

	public void deleteAll(Collection<?> e);

	public <T extends Serializable> void delete(Class<?> clazz, T id);

	public Object getDelegate();

}