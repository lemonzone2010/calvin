package com.apusic.ebiz.framework.core.fulltextsearch;

import org.hibernate.search.FullTextQuery;
import org.hibernate.search.query.engine.spi.FacetManager;

public class SearchResultSizeCallback<T> extends AbstractFullTextQueryCallback{

	public SearchResultSizeCallback(FullTextQueryParameter<T> param){
		super(param);
	}

	protected Object doInHibernate(FullTextQuery query,FacetManager facetManager) {
		if (query == null) {
			return 0;
		}
		return query.getResultSize();
	}
}
