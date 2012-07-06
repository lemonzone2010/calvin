package com.xia.search.solr.query;

import org.apache.commons.lang3.StringUtils;

import com.xia.search.solr.hibernate.HibernateContext;
import com.xia.search.solr.schema.SolrDocumentHelper;
import com.xia.search.solr.schema.SolrSchemaDocument;

public abstract class AbstractSolrQuery {
	protected String q = "";
	protected Class<?> clazz;
	private Integer start=0;
	private Integer rows=10;
	/**
	 * @param clazz 有hibernate search注解的类
	 * @param field bean类的属性,可为*
	 * @param value 对应属性要查询的值，可为*
	 */
	public AbstractSolrQuery(Class<?> clazz, String field, String value) {
		this.clazz = clazz;
	}
	
	protected String getFieldName(String fieldName) {
		SolrDocumentHelper helper=new SolrDocumentHelper(HibernateContext.getSessionFactory());
		try {
			SolrSchemaDocument document = helper.getSchemaDocument(clazz.newInstance());
			if(StringUtils.equals(fieldName, "*")) {
				return document.getField("id").getSolrName();
			}
			return document.getField(fieldName).getSolrName();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Class getQueryClazz() {
		return clazz;
	}
	
	public Integer getStart() {
		return start;
	}

	public AbstractSolrQuery setStart(Integer start) {
		this.start = start;
		return this;
	}

	public Integer getRows() {
		return rows;
	}

	public AbstractSolrQuery setRows(Integer rows) {
		this.rows = rows;
		return this;
	}
}
