package com.apusic.ebiz.framework.core.fulltextsearch;

import java.util.ArrayList;

import org.hibernate.search.FullTextQuery;
import org.hibernate.search.query.engine.spi.FacetManager;

public class SearchCallback<T> extends AbstractFullTextQueryCallback{

	private int firstResult;
	private int maxResults;

	public SearchCallback(FullTextQueryParameter<T> param){
		super(param);
	}

	protected Object doInHibernate(FullTextQuery query,FacetManager facetManager) {
		if (query==null) {
			return new ArrayList(0);
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

}
