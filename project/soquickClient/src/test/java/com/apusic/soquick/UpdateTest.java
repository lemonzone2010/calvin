package com.apusic.soquick;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

public class UpdateTest {

	public static void main(String[] args) throws Exception {
		SolrServer server = new CommonsHttpSolrServer("http://localhost:8082/solr-server");
		UpdateRequest req = new UpdateRequest("/updateschema");
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("type111", "{\"name\":\"xxx\",\"type\":\"maxWord\",\"indexed\":true,\"stored\":false,\"termVectors\":false,\"termPositions\":false,\"termOffsets\":false}");

		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc1);
		req.add(docs);
		UpdateResponse rsp = req.process(server);
	}
}
