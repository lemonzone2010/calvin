package com.xia.search.solr;

import java.util.List;

public class Page<T> {
	long numFound;
	long qTime;
	List<T> result;

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public long getNumFound() {
		return numFound;
	}

	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}

	public long getqTime() {
		return qTime;
	}

	public void setqTime(long qTime) {
		this.qTime = qTime;
	}

	@Override
	public String toString() {
		return "Page [numFound=" + numFound + ", qTime=" + qTime + ", result="
				+ result + "]";
	}
}
