package com.xia.search.solr;

import org.apache.commons.lang3.StringUtils;

//http://localhost:8081/solr/terms?terms.fl=F_Table.F_title&terms.sort=index&terms.prefix=测试
/**
 * Just for test
 * @author xiayong
 *
 */
@Deprecated
public class Suggest extends AbstractSolrQuery {
	private String searchFields = "";
	private boolean indexSort=true;
	private boolean needCount=false;

	public Suggest(Class<?> clazz, String field, String value) {
		super(clazz, field, value);
		addSearchField(field);
		q = value;
	}

	private Suggest addSearchField(String field) {
		searchFields += getFieldName(field) + ",";
		return this;
	}

	/**
	 * @param indexsort
	 *            true is index sort,false is count sort,
	 * @return
	 */
	public Suggest indexSort(boolean indexsort) {
		indexSort = indexsort;
		return this;
	}

	@Override
	public String toString() {
		return q;
	}

	public String getSearchFields() {
		return StringUtils.substringBeforeLast(searchFields, ",");
	}
	public String getSearchPrefix() {
		return q;
	}

	public boolean isIndexSort() {
		return indexSort;
	}

	public boolean isNeedCount() {
		return needCount;
	}

	public void setNeedCount(boolean needCount) {
		this.needCount = needCount;
	}
}
