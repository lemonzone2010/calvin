package com.apusic.ebiz.framework.core.solr;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SolrMapping {
	private static Map<Class<?>, SolrEntity> mappingMap = new HashMap<Class<?>, SolrEntity>();

	public static void addField(Class<?> entityClass, SolrEntity entity) {
		mappingMap.put(entityClass, entity);
	}

	public static Map<Class<?>, SolrEntity> getMappingMap() {
		return Collections.unmodifiableMap(mappingMap);
	}
}