package com.apusic.ebiz.framework.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.model.IdEntity;

@Repository("repositoryFacade")
public class RepositoryFacadeImpl implements RepositoryFacade {
	@Autowired
	private QueryRepository queryRepository;
	@Autowired
	@Qualifier("ebiz_CrudService")
	private CrudService crudRepository;
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#findBy(java.lang.Class, java.lang.String, java.lang.String)
	 */
	@Override
	public <T> T findBy(Class<T> clazz, String property, String value) {
		return queryRepository.findBy(clazz, property, value);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#findListBy(java.lang.Class, java.lang.String, java.lang.String)
	 */
	@Override
	public <T> List<T> findListBy(Class<T> clazz, String property, String value) {
		return queryRepository.findListBy(clazz, property, value);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#findPage(java.lang.Class, furniture.core.dao.Page)
	 */
	@Override
	public <T> Page<T> findPage(Class<T> clazz, Page<T> p) {
		return queryRepository.findPage(clazz, p);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#getRowCount(java.lang.Class)
	 */
	@Override
	public <T> long getRowCount(Class<T> clazz) {
		return queryRepository.getRowCount(clazz);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#getRowCount(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public long getRowCount(DetachedCriteria criteria) {
		return queryRepository.getRowCount(criteria);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#findAll(java.lang.Class)
	 */
	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		return queryRepository.findAll(clazz);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#getRowCountByExample(T)
	 */
	@Override
	public <T> long getRowCountByExample(T t) {
		return queryRepository.getRowCountByExample(t);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#findByExample(T)
	 */
	@Override
	public <T> List<T> findByExample(T t) {
		return queryRepository.findByExample(t);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#findPageByExample(T, furniture.core.dao.Page)
	 */
	@Override
	public <T> Page<T> findPageByExample(T e, Page<T> p) {
		return queryRepository.findPageByExample(e, p);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#findRange(java.lang.Class, int, int)
	 */
	@Override
	public <T> List<T> findRange(Class<T> entity, int start, int size) {
		return queryRepository.findRange(entity, start, size);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#findBy(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public <T> List<T> findBy(DetachedCriteria criteria) {
		return queryRepository.findBy(criteria);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#findBy(org.hibernate.criterion.DetachedCriteria, int, int)
	 */
	@Override
	public <T> List<T> findBy(DetachedCriteria criteria, int first, int count) {
		return queryRepository.findBy(criteria, first, count);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#findPageBy(org.hibernate.criterion.DetachedCriteria, int)
	 */
	@Override
	public <T> Page<T> findPageBy(DetachedCriteria criteria, int size) {
		return queryRepository.findPageBy(criteria, size);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#create(E)
	 */
	@Override
	@Transactional
	public <E extends IdEntity> E create(E e) {
		return crudRepository.create(e);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#retrieve(java.lang.Class, T)
	 */
	@Override
	public <E, T extends Serializable> E retrieve(Class<E> clazz, T id) {
		return crudRepository.retrieve(clazz, id);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#update(E)
	 */
	@Override
	@Transactional
	public <E extends IdEntity> void update(E e) {
		crudRepository.update(e);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#delete(java.lang.Object)
	 */
	@Override
	@Transactional
	public void delete(Object e) {
		crudRepository.delete(e);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#deleteAll(java.util.Collection)
	 */
	@Override
	@Transactional
	public void deleteAll(Collection<?> e) {
		crudRepository.deleteAll(e);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#delete(java.lang.Class, T)
	 */
	@Override
	@Transactional
	public <T extends Serializable> void delete(Class<?> clazz, T id) {
		crudRepository.delete(clazz, id);
	}
	/* (non-Javadoc)
	 * @see furniture.core.dao.RepositoryFacade#getDelegate()
	 */
	@Override
	public Object getDelegate() {
		return crudRepository.getDelegate();
	}
}
