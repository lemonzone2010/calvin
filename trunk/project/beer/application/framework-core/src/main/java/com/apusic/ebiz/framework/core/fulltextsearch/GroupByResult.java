package com.apusic.ebiz.framework.core.fulltextsearch;

import java.util.List;

import org.hibernate.search.query.facet.Facet;

import com.apusic.ebiz.framework.core.dao.Page;


 public class  GroupByResult <T>{
	 private Page<T> page;
	private List<Facet> facets;
	
	
	public GroupByResult(Page page, List<Facet> facets) {
		this.page = page;
		this.facets = facets;
	}

	

	public List<Facet> getFacets() {
		return facets;
	}
	public void setFacets(List<Facet> facets) {
		this.facets = facets;
	}



	public Page<T> getPage() {
		return page;
	}



	public void setPage(Page<T> page) {
		this.page = page;
	}


	
	
}
