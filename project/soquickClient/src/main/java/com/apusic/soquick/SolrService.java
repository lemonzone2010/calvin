package com.apusic.soquick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.util.NamedList;

public class SolrService {
	protected static final Log logger = LogFactory.getLog(SolrService.class);
	private static CommonsHttpSolrServer solrServer;
	private final HibernateDocBinder binder = new HibernateDocBinder();

	public SolrServer getSolrServer() {
		return solrServer;
	}

	public SolrService(String url) {
		init(url);
	}

	private void init(String url) {
		try {
			solrServer = new CommonsHttpSolrServer(url);
			solrServer.setSoTimeout(1000); // socket read timeout
			solrServer.setConnectionTimeout(100);
			solrServer.setDefaultMaxConnectionsPerHost(100);
			solrServer.setMaxTotalConnections(100);
			solrServer.setFollowRedirects(false); // defaults to false
			solrServer.setAllowCompression(true);
			solrServer.setMaxRetries(1); // defaults to 0. > 1 not recommended.
			solrServer.setParser(new XMLResponseParser());

		} catch (Exception e) {
			logger.error("初始化SOLR服务器出错", e);
			throw new SoQuickException("初始化SOLR服务器出错", e);
		}

	}

	public <T> Page<T> query(String keyword, Class<T> clazz) throws SolrServerException, IOException {
		Page<T> ret = new Page<T>();

		SolrQuery query = new SolrQuery();
		query.setQuery("F_Table.F_content:" + keyword + " OR F_Table.F_title:测试111");
		// query.add("F_Table.F_title", "测试2222");
		// query.setQuery(keyword);
		// query.set(CommonParams.DF, "F_Table.F_content");
		// query.set("q.op", "AND");
		// query.addFilterQuery("F_Table.F_title:测试111");
		// query.setRows(20);
		// query.setTerms(true);
		// query.add("F_Table.F_publishedDate", "2011-12-1");
		/*
		 * query.addFacetField("F_Table.F_publishedDate"); Calendar calendar =
		 * Calendar.getInstance(Locale.UK); calendar.set(2010, 1, 1); Date start
		 * = calendar.getTime(); calendar.set(2011, 1, 1); Date end =
		 * calendar.getTime();
		 * query.addDateRangeFacet("F_Table.F_publishedDate", start, end,
		 * "+1MONTH");
		 */
		// query.addField("title");
		// query.setFields("content");
		// query.addSortField("publishedDate", SolrQuery.ORDER.asc);

		QueryResponse rsp = solrServer.query(query);
		ret.setNumFound(rsp.getResults().getNumFound());
		ret.setqTime(Long.valueOf(rsp.getHeader().get("QTime").toString()));
		System.out.println(rsp.getResults());
		List<T> beans = getBinder().getBeans(clazz, rsp.getResults());

		ret.setResult(beans);
		return ret;
	}

	//TODO RANG,分组，分页,not,ge等参看lucene语法
	public <T> Page<T> query(Query q) throws SolrServerException, IOException {
		Page<T> ret = new Page<T>();

		SolrQuery query = new SolrQuery();
		query.setQuery(q.toString());

		QueryResponse rsp = solrServer.query(query);
		ret.setNumFound(rsp.getResults().getNumFound());
		ret.setqTime(Long.valueOf(rsp.getHeader().get("QTime").toString()));

		System.out.println(rsp.getResults());
		List<T> beans = getBinder().getBeans(q.getQueryClazz(), rsp.getResults());

		ret.setResult(beans);
		return ret;
	}

	private HibernateDocBinder getBinder() {
		return binder;
	}

	public <T> void add(T a) throws IOException, SolrServerException {
		solrServer.add(getBinder().toSolrInputDocument(a));
		solrServer.commit();
	}
}
