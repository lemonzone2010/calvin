package com.apusic.ebiz.framework.core.fulltextsearch.filter;

import org.apache.lucene.search.NumericRangeFilter;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.annotations.Key;
import org.hibernate.search.filter.FilterKey;
import org.hibernate.search.filter.StandardFilterKey;

public class DoubleRangeFilterFactory {
	private String field;
	private int precisionStep;
	private Double min;
	private Double max;
	boolean minInclusive;
	boolean maxInclusive;

	private org.apache.lucene.search.Filter newDoubleRange(String field,
			int precisionStep, Double min, Double max, boolean minInclusive,
			boolean maxInclusive) {
		return NumericRangeFilter.newDoubleRange(field, precisionStep, min,
				max, minInclusive, maxInclusive);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getPrecisionStep() {
		return precisionStep;
	}

	public void setPrecisionStep(int precisionStep) {
		this.precisionStep = precisionStep;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public boolean isMinInclusive() {
		return minInclusive;
	}

	public void setMinInclusive(boolean minInclusive) {
		this.minInclusive = minInclusive;
	}

	public boolean isMaxInclusive() {
		return maxInclusive;
	}

	public void setMaxInclusive(boolean maxInclusive) {
		this.maxInclusive = maxInclusive;
	}

	@Key
	public FilterKey getKey() {
		StandardFilterKey key = new StandardFilterKey();
		key.addParameter(field);
		key.addParameter(min);
		key.addParameter(max);
		return key;
	}

	@Factory
	public org.apache.lucene.search.Filter getFilter() {
		return this.newDoubleRange(field, precisionStep, min, max,
				true, true);
	}
}
