package com.apusic.ebiz.framework.core.dao;

import java.io.Serializable;
import java.util.Collection;

import com.apusic.ebiz.framework.core.model.IdEntity;

/**
 * This is a generic CRUD (Create Retrieve Update Delete) service
 * 
 * @author achen
 */
public interface CrudRepository {

	<E extends IdEntity> E create(E e);

	// Return null if not found
	<E, T extends Serializable> E retrieve(Class<E> clazz, T id);

	<E extends IdEntity> void update(E e);

	// Remove the object state from the database. And the object becomes
	// detached
	void delete(Object e);

	void deleteAll(Collection<?> e);

	<T extends Serializable> void delete(Class<?> clazz, T id);

	// Gets the underlying implementation object
	Object getDelegate();
}
