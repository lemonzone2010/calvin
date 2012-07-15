package com.apusic.md.emarket.service;

import java.io.Serializable;

public interface EmarketIndexService {

	/**
	 * Index an entity even if this entity is not
	 * inserted or updated to the database.
	 * @param <T>
	 * @param entities
	 */
	<T> void index(Class<T>... entities);
	public  void indexPublishedProduct() ;
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
}
