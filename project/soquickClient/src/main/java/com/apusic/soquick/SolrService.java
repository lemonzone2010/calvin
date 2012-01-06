package com.apusic.soquick;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.client.solrj.response.TermsResponse.Term;

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

	// TODO 分组，分页,ge等参看lucene语法
	public <T> Page<T> query(Query q) {
		Page<T> ret = new Page<T>();

		try {
			SolrQuery query = new SolrQuery();
			query.setStart(q.getStart());
			query.setRows(q.getRows());
			query.setQuery(q.toString());
			for (Entry<String, ORDER> order : q.getOrder().entrySet()) {
				query.addSortField(order.getKey(), order.getValue());
			}
			QueryResponse rsp = solrServer.query(query);
			ret.setNumFound(rsp.getResults().getNumFound());
			ret.setqTime(Long.valueOf(rsp.getHeader().get("QTime").toString()));

			logger.info(rsp.getResults());
			@SuppressWarnings("unchecked")
			List<T> beans = getBinder().getBeans(q.getQueryClazz(), rsp.getResults());

			ret.setResult(beans);
		} catch (Exception e) {
			logger.error("SOLR查询出错:" + q.toString(), e);
			throw new SoQuickException("SOLR查询出错:" + q.toString(), e);
		}
		return ret;
	}
	/**
	 * 根据用户输入的前缀,查询出相关词,但貌似只能查询出分词,比如测试结果,如果输入测,则只能出测试,而输入测试结,则不出结果.
	 * Just for test now.by xiayong
	 * @param q
	 * @return
	 */
	public Page<String> suggest(Suggest q) {
		Page<String> ret = new Page<String>();
		//http://localhost:8081/solr/terms?terms.fl=F_Table.F_title&terms.sort=index&terms.prefix=测试
		try {
			SolrQuery query = new SolrQuery();
			query.setQueryType("/terms");
			//query.setTerms(true);
			query.addTermsField(q.getSearchFields());
			query.setTermsPrefix(q.getSearchPrefix());
			//query.setTermsSortString(q.isIndexSort()?"index":"count");
			
			QueryResponse rsp = solrServer.query(query);
			logger.info(rsp);
			
			//ret.setNumFound(rsp.getResults().getNumFound());
			ret.setqTime(Long.valueOf(rsp.getHeader().get("QTime").toString()));

			TermsResponse termsResponse = rsp.getTermsResponse();
			List<Term> terms = termsResponse.getTerms(q.getSearchFields());
			List<String> result=new ArrayList<String>();
			for (Term term : terms) {
				result.add(term.getTerm()+(q.isNeedCount()?("("+term.getFrequency()+")"):""));
			}
			ret.setResult(result);
		} catch (SolrServerException e) {
			logger.error("SOLR查询出错:" + q.toString(), e);
			throw new SoQuickException("SOLR查询出错:" + q.toString(), e);
		}
		return ret;
	}
	/*-public void QueryFacet() {
		try {
			SolrServer server = getSolrServer();  
			 SolrQuery solrQuery = new  SolrQuery().  
			               setQuery("*:*").  
			               setFacet(true).  
			               setFacetMinCount(1).  
			               setFacetLimit(8).  
			               addFacetQuery("测试1").
			               addFacetField("F_Table.F_content").  
			               addFacetField("F_Table.F_title");    
			 QueryResponse rsp = solrServer.query(solrQuery);
			 logger.info(rsp);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}*/

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

	/**
	 * 与SOLR SYSTEM PING, 主要是检测solr是否down掉
	 * 
	 * @return String
	 */
	public static String ping() {
		try {
			return solrServer.ping().getResponse().toString();
		} catch (Exception e) {
			logger.error("PING SOLR服务器出错", e);
			throw new SoQuickException("PING SOLR服务器出错", e);
		}
	}
}
