package com.xia.search.solr.service;

import com.xia.search.solr.hibernate.HibernateContext;

//TODO 配置文件,spring文件
//TODO 服务器的翻译
public class SolrContext {
	public static final String SOLR_SERVER_URL = "http://localhost:8080/solr-server";
	public static final boolean NEED_UPDATE_SCHEMA_AT_START = true;//启动时，会更新schema.xml，如果已有schema，可以关闭此配置
	private static SolrService mySolrService;

	public static SolrService getMySolrService() {
		if (mySolrService == null)
			mySolrService = new SolrServiceImpl(SOLR_SERVER_URL, HibernateContext.getSessionFactory());
		return mySolrService;
	}
}
