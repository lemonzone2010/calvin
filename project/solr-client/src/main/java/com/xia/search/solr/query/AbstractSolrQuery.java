package com.xia.search.solr.query;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Table;

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
	 * @param clazz 有hibernate注解的类
	 * @param field bean类的属性,可为*
	 * @param value 对应属性要查询的值，可为*
	 */
	public AbstractSolrQuery(Class<?> clazz, String field, String value) {
		this.clazz = clazz;
	}
	
	protected String getFieldName(String fieldName) {
		if(StringUtils.equals(fieldName, "*")) {
			return "*";
		}
		SolrDocumentHelper helper=new SolrDocumentHelper(HibernateContext.getSessionFactory().getCurrentSession());
		try {
			SolrSchemaDocument document = helper.getDocument(clazz.newInstance());
			return document.getField(fieldName).getSolrName();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		/*Class<?> superClazz = clazz;
		String tableName = "";
		if (superClazz.isAnnotationPresent(Table.class)) {
			tableName = ((Table) superClazz.getAnnotation(Table.class)).name();
		}
		ArrayList<AccessibleObject> members = new ArrayList<AccessibleObject>();
		while (superClazz != null && superClazz != Object.class) {
			members.addAll(Arrays.asList(superClazz.getDeclaredFields()));
			members.addAll(Arrays.asList(superClazz.getDeclaredMethods()));
			superClazz = superClazz.getSuperclass();
		}
		for (AccessibleObject member : members) {
			if (member.isAnnotationPresent(Column.class)) {
				String name = member.getAnnotation(Column.class).name();
				if (StringUtils.isNotBlank(name) && member instanceof Field
						&& StringUtils.endsWith(((Field) member).getName(), fieldName)) {
					return tableName + "." + name;
				} else {
					// TODO 对方法名的解析标记语言
				}
			}
		}
		return fieldName;*/
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
