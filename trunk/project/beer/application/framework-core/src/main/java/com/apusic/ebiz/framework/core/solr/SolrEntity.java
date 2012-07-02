package com.apusic.ebiz.framework.core.solr;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class SolrEntity {
	private Annotation[] annotations;;
	private Map<String, SolrField> fieldMap = new HashMap<String, SolrField>();

	private String name;
	private String tableName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void addField(String fieldName, SolrField field) {
		fieldMap.put(fieldName, field);
	}

	public Map<String, SolrField> getFieldMap() {
		return Collections.unmodifiableMap(fieldMap);
	}

	public SolrField get(String fieldName) {
		return getFieldMap().get(fieldName);
	}

	public Annotation[] getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}
}