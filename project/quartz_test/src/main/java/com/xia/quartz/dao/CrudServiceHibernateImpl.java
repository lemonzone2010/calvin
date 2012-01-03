package com.xia.quartz.dao;

import java.io.Serializable;
import java.util.Collection;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository(value = "crudService")
public class CrudServiceHibernateImpl implements CrudService{
	private HibernateTemplate hibernateTemplate;

	@Autowired
	public CrudServiceHibernateImpl(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public <E> E create(E e) {

		hibernateTemplate.persist(e);

		//This is problematic in a long-running conversation wiht an 'extended Session/persistence context'
		//hibernateTemplate.save(e);
		hibernateTemplate.flush();

		//To prevent the inconsistent when database triggers update the data
		hibernateTemplate.refresh(e);
		return e;
	}

	//Return null if not found
	public <E, T extends Serializable> E retrieve(Class<E> clazz, T id) {
		return (E) hibernateTemplate.get(clazz, id);
	}

	public void  update(Object e) {
		hibernateTemplate.merge(e);
	}

	public void delete(Object entity) {
		hibernateTemplate.delete(entity);
	}

	public void deleteAll(Collection entities) {
		hibernateTemplate.deleteAll(entities);
	}

	public <T extends Serializable> void delete(Class clazz, T id) {
		//First retrieve the entity intance by its id
		Object entity = retrieve(clazz, id);
		if (entity != null){
			this.delete(entity);
		}
	}

	public Object getDelegate() {
		return this.hibernateTemplate;
	}
}