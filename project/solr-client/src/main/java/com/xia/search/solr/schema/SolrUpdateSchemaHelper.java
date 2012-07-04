package com.xia.search.solr.schema;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import com.xia.search.solr.util.JasonUtil;


public class SolrUpdateSchemaHelper {
	private static Log logger=LogFactory.getLog(SolrUpdateSchemaHelper.class);
	private static String solrServerUrl = "http://localhost:8082/solr-server";

	public static void updateSchema(SolrSchemaDocument document) throws SolrServerException, IOException {
		logger.info("正在请求:"+solrServerUrl+"/udpateschema");
		SolrServer server = new HttpSolrServer(solrServerUrl);
		UpdateRequest req = new UpdateRequest("/updateschema");

		SolrInputDocument solrInputDoc = convert(document);

		req.add(solrInputDoc);
		UpdateResponse rsp = req.process(server);
		System.out.println(rsp);
	}

	private static SolrInputDocument convert(SolrSchemaDocument document) {
		SolrInputDocument doc = new SolrInputDocument();
		for (FieldAdaptor field : document.getFields()) {
			doc.addField(field.getFieldName(), JasonUtil.toJsonString(field));
		}
		return doc;
	}

}
