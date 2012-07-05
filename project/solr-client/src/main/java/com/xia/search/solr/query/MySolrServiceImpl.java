package com.xia.search.solr.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.UpdateParams;
import org.hibernate.SessionFactory;
import org.hibernate.search.query.engine.spi.EntityInfo;

import com.xia.search.solr.Page;
import com.xia.search.solr.Query;
import com.xia.search.solr.SoQuickException;
import com.xia.search.solr.entity.SolrEntityInfoImpl;
import com.xia.search.solr.entity.SolrObjectLoaderHelper;
import com.xia.search.solr.schema.FieldAdaptor;
import com.xia.search.solr.schema.SolrDocumentHelper;
import com.xia.search.solr.schema.SolrSchemaDocument;
import com.xia.search.solr.util.JasonUtil;

public class MySolrServiceImpl implements MySolrService {
	protected static final Log logger = LogFactory.getLog(MySolrServiceImpl.class);
	private static HttpSolrServer solrServer;
	private SolrDocumentHelper documentHelper;
	private SessionFactory sessionFactory;

	public MySolrServiceImpl(String url, SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		init(url);
	}

	public SolrServer getSolrServer() {
		return solrServer;
	}

	private void init(String url) {
		try {
			solrServer = new HttpSolrServer(url);
			solrServer.setSoTimeout(1000); // socket read timeout
			solrServer.setConnectionTimeout(100);
			solrServer.setDefaultMaxConnectionsPerHost(100);
			solrServer.setMaxTotalConnections(100);
			solrServer.setFollowRedirects(false); // defaults to false
			solrServer.setAllowCompression(true);
			solrServer.setMaxRetries(1); // defaults to 0. > 1 not recommended.
			solrServer.setParser(new XMLResponseParser());
			documentHelper = new SolrDocumentHelper(sessionFactory.getCurrentSession());

		} catch (Exception e) {
			logger.error("初始化SOLR服务器出错", e);
			throw new SoQuickException("初始化SOLR服务器出错", e);
		}

	}

	@Override
	public Result update(Object... entitys) throws Exception {
		// FIXME 如果已存在,只更新,否则新增
		UpdateRequest req = new UpdateRequest("/update");
		for (Object object : entitys) {
			SolrSchemaDocument document = documentHelper.getDocument(object);
			req.add(convert(document));
		}
		req.setParam(UpdateParams.COMMIT, "true");
		UpdateResponse response = req.process(solrServer);

		return Result.getResult(response.getStatus() == 0, response.getElapsedTime());
	}

	private SolrInputDocument convert(SolrSchemaDocument document) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		Object id = getIdFromSolr(document);
		if (null == id) {
			doc.addField("id", UUID.randomUUID());
		} else {
			doc.addField("id", id);
		}
		for (FieldAdaptor field : document.getFields()) {
			doc.addField(field.getSolrName(), field.getFieldsData());
		}
		return doc;
	}

	public Object getIdFromSolr(Object enitty) throws SolrServerException, IOException {
		return getIdFromSolr(documentHelper.getDocument(enitty));
	}

	private Object getIdFromSolr(SolrSchemaDocument document) throws SolrServerException, IOException {
		FieldAdaptor field = document.getField("id");
		SolrQuery query = new SolrQuery();
		query.setStart(0);
		query.setRows(20);
		query.setQuery(field.getSolrName());
		QueryResponse rsp = solrServer.query(query);
		SolrDocumentList results = rsp.getResults();
		return results.size() > 0 ? results.get(0).getFieldValue("id") : null;
	}

	@Override
	public Result updateSchema(Object... entitys) throws Exception {
		UpdateRequest req = new UpdateRequest("/updateschema");
		for (Object object : entitys) {
			SolrSchemaDocument document = documentHelper.getDocument(object);
			req.add(convert2SolrInput(document));
		}
		UpdateResponse response = req.process(solrServer);

		return Result.getResult(response.getStatus() == 0, response.getElapsedTime());
	}

	private SolrInputDocument convert2SolrInput(SolrSchemaDocument document) {
		SolrInputDocument doc = new SolrInputDocument();
		for (FieldAdaptor field : document.getFields()) {
			doc.addField(field.getFieldName(), JasonUtil.toJsonString(field));
		}
		return doc;
	}

	@Override
	// TODO 分组，分页,ge等参看lucene语法
	//FIXME 将persistence持久解析的document缓存起来
	public <T> Page<T> query(Query q) throws Exception {
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
			List<T> beans=new ArrayList<T>();
			for (SolrDocument solrDocument : rsp.getResults()) {
				T load = (T) SolrObjectLoaderHelper.load(convert2EntityInfo(solrDocument), sessionFactory.getCurrentSession());
				beans.add(load);
			}
			ret.setResult(beans);
		} catch (Exception e) {
			logger.error("SOLR查询出错:" + q.toString(), e);
			throw new SoQuickException("SOLR查询出错:" + q.toString(), e);
		}
		return ret;
	}
	private EntityInfo convert2EntityInfo(SolrDocument doc) throws ClassNotFoundException {
		String className = "";
		Integer id = null;
		for (Entry<String, Object> entry : doc) {
			if (StringUtils.contains(entry.getKey(), FieldAdaptor.HIBERNATE_CLASS_FLAG)) {
				className = entry.getValue().toString();
			}
			if (StringUtils.endsWith(entry.getKey(), ".id")) {
				id = Integer.valueOf(entry.getValue().toString());
			}
		}
		return new SolrEntityInfoImpl(className, "id", id);
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
