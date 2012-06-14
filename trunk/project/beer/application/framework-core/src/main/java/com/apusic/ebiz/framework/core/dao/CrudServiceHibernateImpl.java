package com.apusic.ebiz.framework.core.dao;

import java.io.Serializable;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository(value = "ebiz_CrudService")
public class CrudServiceHibernateImpl implements CrudService{
	private SessionFactory sessionFactory;
	@Autowired
	public CrudServiceHibernateImpl(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public <E> E create(E e) {

		getSession().persist(e);

		//This is problematic in a long-running conversation wiht an 'extended Session/persistence context'
		//getSession().save(e);
		getSession().flush();

		//To prevent the inconsistent when database triggers update the data
		getSession().refresh(e);
		return e;
	}

	//Return null if not found
	public <E, T extends Serializable> E retrieve(Class<E> clazz, T id) {
		return (E) getSession().get(clazz, id);
	}

	public void  update(Object e) {
		getSession().merge(e);
	}

	public void delete(Object entity) {
		getSession().delete(entity);
	}

	public void deleteAll(Collection entities) {
		for (Object object : entities) {
			delete(entities);
		}
	}

	public <T extends Serializable> void delete(Class clazz, T id) {
		//First retrieve the entity intance by its id
		Object entity = retrieve(clazz, id);
		if (entity != null){
			this.delete(entity);
		}
	}

	public Object getDelegate() {
		return this.getSession();
	}
}