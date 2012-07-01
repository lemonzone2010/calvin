package com.xia.search.solr.xml;

import java.io.File;
import java.util.List;

import org.apache.solr.core.SolrResourceLoader;
import org.dom4j.Attribute;
import org.dom4j.Node;
import org.dom4j.tree.DefaultElement;

import com.xia.search.solr.util.XmlUtil;

public enum SolrXml {
	SCHEMA("conf/schema.xml"), SOLR_CONFIG("conf/solrconfig.xml");

	private XmlUtil xmlUtil;

	private SolrXml(String fileName) {
		xmlUtil = new XmlUtil(new File(SolrResourceLoader.locateSolrHome() + fileName));
	}

	public String getValue(String path) {
		return xmlUtil.getString(path);
	}
	
	public List<Node> getFieldTypes() {
		return xmlUtil.getNodes("//schema/fields/field/@name");
	}

	// define as a enum
	public static void main(String[] args) {
		//String value = SolrXml.SCHEMA.getValue("//schema/types");
		//System.out.println(value);
		List<Node> fieldTypes = SolrXml.SCHEMA.getFieldTypes();
		for (Node node : fieldTypes) {
			DefaultElement element=(DefaultElement) node;
			Attribute attribute = element.attribute("name");
			System.out.println(node);
		}
	}
}
