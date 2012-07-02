package com.apusic.ebiz.framework.core.solr;

import java.lang.annotation.Annotation;

public class SolrField {
	private String columnName;
	private Annotation[] annotations;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Annotation[] getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}

}