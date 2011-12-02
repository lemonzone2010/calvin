package com.apusic.soquick;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

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

	// TODO RANG,分组，分页,not,ge等参看lucene语法
	public <T> Page<T> query(Query q) {
		Page<T> ret = new Page<T>();

		try {
			SolrQuery query = new SolrQuery();
			query.setQuery(q.toString());

			QueryResponse rsp = solrServer.query(query);
			ret.setNumFound(rsp.getResults().getNumFound());
			ret.setqTime(Long.valueOf(rsp.getHeader().get("QTime").toString()));

			System.out.println(rsp.getResults());
			@SuppressWarnings("unchecked")
			List<T> beans = getBinder().getBeans(q.getQueryClazz(), rsp.getResults());

			ret.setResult(beans);
		} catch (Exception e) {
			logger.error("SOLR查询出错:" + q.toString(), e);
			throw new SoQuickException("SOLR查询出错:" + q.toString(), e);
		}
		return ret;
	}

	private HibernateDocBinder getBinder() {
		return binder;
	}

	public void add(Object o) {
		try {
			solrServer.add(getBinder().toSolrInputDocument(o));
			solrServer.commit();
		} catch (Exception e) {
			logger.error("增加SOLR索引出错:" + o, e);
			throw new SoQuickException("增加SOLR索引出错:" + o, e);
		}
	}

	public void delete(Query q) {
		try {
			solrServer.deleteByQuery(q.toString());
			solrServer.commit();
		} catch (Exception e) {
			logger.error("删除SOLR索引出错:" + q, e);
			throw new SoQuickException("删除SOLR索引出错:" + q, e);
		}
	}
}
