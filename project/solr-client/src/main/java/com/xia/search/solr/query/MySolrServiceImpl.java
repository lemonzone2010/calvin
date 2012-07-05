package com.xia.search.solr.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
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
import org.hamcrest.core.IsEqual;
import org.hibernate.SessionFactory;
import org.hibernate.search.query.engine.spi.EntityInfo;

import com.xia.search.solr.Query;
import com.xia.search.solr.SoQuickException;
import com.xia.search.solr.hibernate.HibernateContext;
import com.xia.search.solr.schema.DocumentHelper;
import com.xia.search.solr.schema.FieldAdaptor;
import com.xia.search.solr.schema.SolrSchemaDocument;

public class MySolrServiceImpl implements MySolrService{
	protected static final Log logger = LogFactory.getLog(MySolrServiceImpl.class);
	private static HttpSolrServer solrServer;
	private DocumentHelper documentHelper;
	private SessionFactory sessionFactory;

	public MySolrServiceImpl(String url,SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
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
			documentHelper=new DocumentHelper(sessionFactory.getCurrentSession());

		} catch (Exception e) {
			logger.error("初始化SOLR服务器出错", e);
			throw new SoQuickException("初始化SOLR服务器出错", e);
		}

	}
	@Override
	public Result update(Object... entitys) throws Exception {
		//FIXME 如果已存在,只更新,否则新增
		UpdateRequest req = new UpdateRequest("/update");
		for (Object object : entitys) {
			SolrSchemaDocument document = documentHelper.getDocument(object);
			req.add(convert(document));
		}
		req.setParam(UpdateParams.COMMIT, "true");
		UpdateResponse response = req.process(solrServer);
		
		return Result.getResult(response.getStatus()==0, response.getElapsedTime());
	}
	private SolrInputDocument convert(SolrSchemaDocument document) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		Object id = getIdFromSolr(document);
		if(null==id) {
			doc.addField("id", UUID.randomUUID());
		}else {
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
		query.setQuery(field.getSolrName()+":"+field.getFieldsData());
		QueryResponse rsp = solrServer.query(query);
		SolrDocumentList results = rsp.getResults();
		return results.size()>0?results.get(0).getFieldValue("id"):null;
	}

	@Override
	public Result updateSchema(Object... entity) throws Exception {
		// TODO Auto-generated method stub
		
	return	Result.getSuccessResult();
	}

	@Override
	public List<?> query(Query query) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
