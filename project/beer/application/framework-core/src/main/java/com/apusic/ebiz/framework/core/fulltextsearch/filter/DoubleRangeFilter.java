package com.apusic.ebiz.framework.core.fulltextsearch.filter;

public class DoubleRangeFilter extends Filter{
	public DoubleRangeFilter(String name, String field, double min, double max){
		this(name, field, min, max, (int) 4);
	}

	public DoubleRangeFilter(String name, String field, double min, double max, int precisionStep){
		super(name);
		this.put("field", field);
		this.put("min", min);
		this.put("max", max);
		this.put("precisionStep", precisionStep);
		this.put("minInclusive", true);
		this.put("maxInclusive", true);
	}
}
