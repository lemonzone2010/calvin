package com.xia.search.solr.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacetQuery extends Query{
	public List<String> facetFields =new ArrayList<String>();
	public List<String> highlightFields =new ArrayList<String>();
	public List<DateRange> dateRangeFields =new ArrayList<DateRange>();
	public List<NumericRange> numericRangeFields =new ArrayList<NumericRange>();
	public List<String> facetQuerys =new ArrayList<String>();
	private String facetPrefix;

	public FacetQuery(Class<?> clazz, String field, String value) {
		super(clazz, field, value);
	}

	
	public static class DateRange{
		private String field;
		private Date start;
		private Date end;
		private String gap;
		public DateRange(String field, Date start, Date end, String gap) {
			this.field = field;
			this.start = start;
			this.end = end;
			this.gap = gap;
		}
		public String getField() {
			return field;
		}
		public Date getStart() {
			return start;
		}
		public Date getEnd() {
			return end;
		}
		public String getGap() {
			return gap;
		}
	}
	public static class NumericRange{
		private String field;
		private Number start;
		private Number end;
		private Number gap;
		public NumericRange(String field, Number start, Number end, Number gap) {
			super();
			this.field = field;
			this.start = start;
			this.end = end;
			this.gap = gap;
		}
		public String getField() {
			return field;
		}
		public Number getStart() {
			return start;
		}
		public Number getEnd() {
			return end;
		}
		public Number getGap() {
			return gap;
		}
	}
	public String getFacetPrefix() {
		return facetPrefix;
	}
	public void setFacetPrefix(String facetPrefix) {
		this.facetPrefix = facetPrefix;
	}
}
