package com.apusic.ebiz.framework.core.fulltextsearch;

import java.io.Serializable;

import org.hibernate.criterion.Criterion;

/**
 * Manual indexing. Bascially, this is not a recommended service.
 * But for the performance turning or dirty data issue, this service
 * can be triggered by schedule job or MQ
 *
 * @author achen
 *
 */
public interface IndexService {
	/**
	 * Index an entity even if this entity is not
	 * inserted or updated to the database.
	 * @param <T>
	 * @param entities
	 */
	<T> void index(Class<T>... entities);
	public <T> void indexByCriteria(Class<T> clazz,Criterion... criterions) ;
	/**
	 * Purging will remove the entity with the given id from
	 * the Lucene index but will not touch the database.
	 * @param <T>
	 * @param <I>
	 * @param clazz
	 * @param id
	 */
	<T, I extends Serializable> void purge(Class<T> clazz, I id);

	/**
	 * Purging all will remove all the entities from the Lucene index
	 * but will not touch the database.
	 * @param <T>
	 * @param clazz
	 */
	<T> void purgeAll(Class<T> clazz);


	/**
	 * Purging the entity by the given entity id
	 * @param <T>
	 * @param <I>
	 * @param entity
	 * @param id
	 */
	<T, I extends Serializable> void index(Class<T> entity, I id);

	<T,I extends Serializable> void delete(Class<T> entity,I id);

}
