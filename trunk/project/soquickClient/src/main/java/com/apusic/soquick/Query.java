package com.apusic.soquick;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.common.util.DateUtil;

/**
 * 已实现：and,or,not,order,range
 * @author xiayong
 *
 */
public class Query extends AbstractSolrQuery{
	private Map<String,ORDER> order=new HashMap<String,ORDER>();
	

	/**
	 * @param clazz 有hibernate注解的类
	 * @param field bean类的属性,可为*
	 * @param value 对应属性要查询的值，可为*
	 */
	public Query(Class<?> clazz, String field, String value) {
		super(clazz, field, value);
		//检查是否有hibernate注解
		add(field, value, "");
	}

	/**
	 * @param field
	 * @param isAsc 是否升序
	 * @return
	 */
	public Query addSort(String field, boolean isAsc) {
		order.put(getFieldName(field), isAsc ? ORDER.asc : ORDER.desc);
		return this;
	}
	public Query and(String field, String value) {
		add(field, value, " AND ");
		return this;
	}
	public Query not(String field, String value) {
		add(field, value, " NOT ");
		return this;
	}

	public Query or(String field, String value) {
		add(field, value, " OR ");
		return this;
	}

	// mod_date:[20020101 TO 20030101]
	public Query andRange(String field, Object... value) {
		if (value == null) {
			return this;
		}
		if (value.length == 1) {
			q += " AND " + getFieldName(field) + ":[" + getString(value[0]) + " TO *]";
		} else {
			q += " AND " + getFieldName(field) + ":[" + getString(value[0]) + " TO " + getString(value[1]) + "]";
		}
		return this;
	}

	private String getString(Object o) {
		return (o instanceof Date) ? DateUtil.getThreadLocalDateFormat().format(o): o.toString();
	}

	private Query add(String field, String value, String condition) {
		if(StringUtils.contains(field, "*")&&StringUtils.equals("*", value)) {
			if(!(StringUtils.equals(field, "*")&&StringUtils.equals("*", value)))
				throw new SoQuickException("filed里不能带*.(例外：value为*时，field可为*)");
		}
		q += condition + getFieldName(field) + ":" + value;
		return this;
	}

	@Override
	public String toString() {
		return StringUtils.isBlank(q) ? "*:*" : q;
	}
	
	/*private String encoding(String o) {
		try {
			return java.net.URLEncoder.encode(o, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return o;
		}
	}*/
	public Map<String, ORDER> getOrder() {
		return order;
	}




}
