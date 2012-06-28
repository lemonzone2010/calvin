package com.apusic.ebiz.framework.core.fulltextsearch;

import java.util.List;

import org.apache.lucene.search.SortField;

import com.apusic.ebiz.framework.core.fulltextsearch.filter.Filter;


public class FullTextQueryParameter<T> {

	private Class<T> clazz;

	private String[] matching;

	private String[] fields;

	private Float[] boosts;

	private SortField[] sortFields;

	private Filter[] filters;
	
	private List<String[]> special;
	
	private String facetField;
	

	public FullTextQueryParameter(Class<T> clazz, String matching[],
			String... fields) {
		this(clazz, matching, null, fields);
	}

	public FullTextQueryParameter(Class<T> clazz, String[] matching,
			Float[] boosts, String... fields) {
		this(clazz, matching, boosts, fields, (SortField[])null);
	}

	public FullTextQueryParameter(Class<T> clazz, String[] matching,
			Float[] boosts, String[] fields, SortField... sortFields){
		this(clazz, matching, boosts, fields, sortFields, (Filter[]) null);
	}

	public FullTextQueryParameter(Class<T> clazz, String[] matching,
			Float[] boosts, String[] fields, SortField[] sortFields, Filter... filters){
		this.clazz = clazz;
		this.matching = matching;
		this.boosts = boosts;
		this.fields = fields;
		this.sortFields = sortFields;
		this.filters = filters;
	}

	public SortField[] getSortFields() {
		return sortFields;
	}

	public void setSortFields(SortField[] sortFields) {
		this.sortFields = sortFields;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public String[] getMatching() {
		return matching;
	}

	public String[] getFields() {
		return fields;
	}

	public Float[] getBoostPerField() {
		return boosts;
	}

	public void setMatching(String[] matching) {
		this.matching = matching;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public Filter[] getFilters() {
		return filters;
	}

	public void setFilters(Filter[] filters) {
		this.filters = filters;
	}

	public List<String[]> getSpecial() {
		return special;
	}

	public void setSpecial(List<String[]> special) {
		this.special = special;
	}

	public String getFacetField() {
		return facetField;
	}

	public void setFacetField(String facetField) {
		this.facetField = facetField;
	}




	
}
