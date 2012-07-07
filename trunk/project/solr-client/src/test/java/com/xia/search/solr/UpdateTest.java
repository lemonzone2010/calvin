package com.xia.search.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

public class UpdateTest {
//http://wiki.apache.org/solr/Suggester
	static SolrServer server = new HttpSolrServer("http://localhost:8080/solr-server");
	public static void main(String[] args) throws Exception {
		SolrQuery query = new SolrQuery();
		query.setQueryType("/suggest");
		//query.setTerms(true);
		//query.addTermsField(q.getSearchFields());
		//query.setTermsPrefix(q.getSearchPrefix());
		//query.setTermsSortString(q.isIndexSort()?"index":"count");
		query.setQuery("夏");
		QueryResponse rsp = server.query(query);
		System.out.println(rsp);
	}

	private static void update() throws SolrServerException, IOException {
		UpdateRequest req = new UpdateRequest("/updateschema");
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("type111", "{\"entityName\":\"DummyBook\",\"fieldName\":\"xxx\",\"storeTermVector\":false,\"stored\":true,\"indexed\":true,\"tokenized\":false}");

		req.add(doc1);
		UpdateResponse rsp = req.process(server);
	}
}
