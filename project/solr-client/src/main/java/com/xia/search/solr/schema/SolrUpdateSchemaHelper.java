package com.xia.search.solr.schema;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import com.xia.search.solr.util.JasonUtil;


public class SolrUpdateSchemaHelper {
	private static String solrServerUrl = "http://localhost:8080/solr-server";

	public static void updateSchema(SolrSchemaDocument document) throws SolrServerException, IOException {
		SolrServer server = new CommonsHttpSolrServer(solrServerUrl);
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
