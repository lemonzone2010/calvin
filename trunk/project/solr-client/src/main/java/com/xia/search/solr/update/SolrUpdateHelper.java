package com.xia.search.solr.update;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.UpdateParams;

import com.xia.search.solr.schema.FieldAdaptor;
import com.xia.search.solr.schema.SolrSchemaDocument;
import com.xia.search.solr.util.JasonUtil;

public class SolrUpdateHelper {
	private static Log logger=LogFactory.getLog(SolrUpdateSchemaHelper.class);
	private static String solrServerUrl = "http://localhost:8080/solr-server";

	public static void updateSchema(SolrSchemaDocument document) throws SolrServerException, IOException {
		logger.info("正在请求:"+solrServerUrl+"/udpate");
		SolrServer server = new HttpSolrServer(solrServerUrl);
		UpdateRequest req = new UpdateRequest("/update");

		SolrInputDocument solrInputDoc = convert(document);

		req.add(solrInputDoc);
		req.setParam(UpdateParams.COMMIT, "true");
		UpdateResponse rsp = req.process(server);
		System.out.println(rsp);
	}

	private static SolrInputDocument convert(SolrSchemaDocument document) {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", UUID.randomUUID());
		for (FieldAdaptor field : document.getFields()) {
			String name = field.getEntityName()+"."+field.getFieldName();
			doc.addField(name, field.getFieldsData());
		}
		return doc;
	}
}
