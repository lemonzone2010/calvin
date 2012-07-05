package com.xia.search.solr.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.hibernate.search.query.engine.spi.EntityInfo;

import com.xia.search.solr.entity.SolrEntityInfoImpl;
import com.xia.search.solr.schema.FieldAdaptor;
import com.xia.search.solr.update.SolrUpdateSchemaHelper;

public class SolrQueryHelper {
	private static Log logger=LogFactory.getLog(SolrUpdateSchemaHelper.class);
	private static String solrServerUrl = "http://localhost:8080/solr-server";

	public static List<EntityInfo>  query(String queryString) throws SolrServerException, IOException, ClassNotFoundException {
		logger.info("正在请求:"+solrServerUrl+"/udpate");
		SolrServer server = new HttpSolrServer(solrServerUrl);
		SolrQuery query = new SolrQuery();
		query.setStart(0);
		query.setRows(20);
		query.setQuery(queryString);
		QueryResponse rsp = server.query(query);
		SolrDocumentList results = rsp.getResults();
		List<EntityInfo> result=new ArrayList<EntityInfo>();
		for (SolrDocument solrDocument : results) {
			result.add(convert(solrDocument));
		}
	System.out.println(result);
	return result;
	}
	public static void main(String[] args) throws SolrServerException, IOException, ClassNotFoundException {
		SolrQueryHelper.query("*:*");
	}

	private static EntityInfo convert(SolrDocument doc) throws ClassNotFoundException {
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
}
