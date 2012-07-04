package com.xia.search.solr;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

public class UpdateTest {

	public static void main(String[] args) throws Exception {
		SolrServer server = new HttpSolrServer("http://localhost:8082/solr-server");
		UpdateRequest req = new UpdateRequest("/updateschema");
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("type111", "{\"entityName\":\"DummyBook\",\"fieldName\":\"xxx\",\"storeTermVector\":false,\"stored\":true,\"indexed\":true,\"tokenized\":false}");

		req.add(doc1);
		UpdateResponse rsp = req.process(server);
	}
}
