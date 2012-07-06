package com.xia.search.solr.service;

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

import com.xia.search.solr.XiaSolrException;
import com.xia.search.solr.entity.SolrEntityInfoImpl;
import com.xia.search.solr.entity.SolrObjectLoaderHelper;
import com.xia.search.solr.query.IdMappingMap;
import com.xia.search.solr.query.Page;
import com.xia.search.solr.query.Query;
import com.xia.search.solr.query.Result;
import com.xia.search.solr.schema.FieldAdaptor;
import com.xia.search.solr.schema.SolrDocumentHelper;
import com.xia.search.solr.schema.SolrSchemaDocument;
import com.xia.search.solr.util.JasonUtil;

public class SolrServiceImpl implements SolrService {
	protected static final Log logger = LogFactory.getLog(SolrServiceImpl.class);
	private static IdMappingMap idMapping=new IdMappingMap();
	private static HttpSolrServer solrServer;
	private SolrDocumentHelper documentHelper;
	private SessionFactory sessionFactory;

	public SolrServiceImpl(String url, SessionFactory sessionFactory) {
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
			throw new XiaSolrException("初始化SOLR服务器出错", e);
		}

	}

	@Override
	public Result indexSaveOrUpdate(Object... entitys) throws Exception {
		UpdateRequest req = new UpdateRequest("/update");
		for (Object object : entitys) {
			SolrSchemaDocument document = documentHelper.getDocument(object);
			req.add(convertForIndex(document));
		}
		req.setParam(UpdateParams.COMMIT, "true");
		UpdateResponse response = req.process(solrServer);

		return Result.getResult(response.getStatus() == 0, response.getElapsedTime());
	}

	private SolrInputDocument convertForIndex(SolrSchemaDocument document) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		Object id = getSolrId(document);
		if (null == id) {
			UUID idUUID = UUID.randomUUID();
			doc.addField("id", idUUID);
			idMapping.put(document.getIdValue(), idUUID.toString());
		} else {
			doc.addField("id", id);
		}
		for (FieldAdaptor field : document.getFields()) {
			doc.addField(field.getSolrName(), field.getFieldsData());
		}
		return doc;
	}
	public Result indexDelete(Object... entitys) throws Exception{
		UpdateRequest req = new UpdateRequest("/update");
		for (Object object : entitys) {
			req.deleteById(getSolrId(object));
			//SolrSchemaDocument document = documentHelper.getDocument(object);
			//req.add(convertForIndex(document));
		}
		req.setParam(UpdateParams.COMMIT, "true");
		UpdateResponse response = req.process(solrServer);

		return Result.getResult(response.getStatus() == 0, response.getElapsedTime());
	}

	public String getSolrId(Object enitty) throws SolrServerException, IOException {
		return getSolrId(documentHelper.getDocument(enitty));
	}

	private String getSolrId(SolrSchemaDocument document) throws SolrServerException, IOException {
		String ret=idMapping.get(document.getIdValue());
		if(StringUtils.isNotEmpty(ret)) {
			return ret;
		}
		FieldAdaptor field = document.getField("id");
		SolrQuery query = new SolrQuery();
		query.setStart(0);
		query.setRows(20);
		query.setQuery(field.getSolrName()+":"+field.getFieldsData());
		QueryResponse rsp = solrServer.query(query);
		SolrDocumentList results = rsp.getResults();
		ret=results.size() > 0 ? results.get(0).getFieldValue("id").toString() : null;
		if(StringUtils.isNotEmpty(ret)) {
			idMapping.put(document.getIdValue(), ret);
		}
		return ret;
	}

	@Override
	public Result updateSchema(Object... entitys) throws Exception {
		UpdateRequest req = new UpdateRequest("/updateschema");
		for (Object object : entitys) {
			SolrSchemaDocument document = documentHelper.getSchemaDocument(object);
			req.add(convertForUpdateSchema(document));
		}
		UpdateResponse response = req.process(solrServer);

		return Result.getResult(response.getStatus() == 0, response.getElapsedTime());
	}

	private SolrInputDocument convertForUpdateSchema(SolrSchemaDocument document) {
		SolrInputDocument doc = new SolrInputDocument();
		for (FieldAdaptor field : document.getFields()) {
			doc.addField(field.getFieldName(), JasonUtil.toJsonString(field));
		}
		return doc;
	}

	@SuppressWarnings("unchecked")
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
			throw new XiaSolrException("SOLR查询出错:" + q.toString(), e);
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
