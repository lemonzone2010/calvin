package com.xia.search.solr.util;

import com.xia.search.solr.hibernate.HibernateContext;
import com.xia.search.solr.service.SolrService;
import com.xia.search.solr.service.SolrServiceImpl;

public class SolrContext {
	public static final String SOLR_SERVER_URL = "http://localhost:8080/solr-server";
	private static SolrService mySolrService;
//	private static Map<String,Docuemtn>
	//本地缓存id<==>uuid映射，没有时，才去solr查询

	public static SolrService getMySolrService() {
		if (mySolrService == null)
			mySolrService = new SolrServiceImpl(SOLR_SERVER_URL, HibernateContext.getSessionFactory());
		return mySolrService;
	}
}
