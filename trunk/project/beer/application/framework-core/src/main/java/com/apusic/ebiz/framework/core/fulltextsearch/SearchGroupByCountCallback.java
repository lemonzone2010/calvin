package com.apusic.ebiz.framework.core.fulltextsearch;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.search.FullTextQuery;
import org.hibernate.search.query.engine.spi.FacetManager;
import org.hibernate.search.query.facet.Facet;

import com.apusic.ebiz.framework.core.dao.Page;

public class SearchGroupByCountCallback<T> extends AbstractFullTextQueryCallback{

	private int firstResult;
	private int maxResults;
	private List<Facet> facets;

	public SearchGroupByCountCallback(FullTextQueryParameter<T> param){
		super(param);
	}

	protected Page<T> doInHibernate(FullTextQuery query,FacetManager facetManager) {
		
		if (query == null || facetManager == null) {
			facets = new ArrayList<Facet>(0);
			return new Page<T>();
		}  
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		
		Page<T> page = new Page();
		page.setPageSize(maxResults);
		int rowCnt = query.getResultSize();
		page.setTotal(rowCnt);
		page.setPageNo(rowCnt%maxResults == 0 ? rowCnt/maxResults : rowCnt/maxResults+1 );
		
		page.setRows(query.list());
		
		facets = facetManager.getFacets("labelFaceting");
		return page;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public List<Facet> getFacets() {
		return facets;
	}


}
