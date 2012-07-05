package com.xia.search.solr.util;

import com.xia.search.solr.hibernate.HibernateContext;
import com.xia.search.solr.query.MySolrService;
import com.xia.search.solr.query.MySolrServiceImpl;

public class SolrContext {
	public static final String SOLR_SERVER_URL = "http://localhost:8080/solr-server";
	private static MySolrService mySolrService;
//	private static Map<String,Docuemtn>

	public static MySolrService getMySolrService() {
		if (mySolrService == null)
			mySolrService = new MySolrServiceImpl(SOLR_SERVER_URL, HibernateContext.getSessionFactory());
		return mySolrService;
	}
}
