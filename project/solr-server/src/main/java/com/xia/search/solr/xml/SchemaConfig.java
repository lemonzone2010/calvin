package com.xia.search.solr.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.core.SolrResourceLoader;
import org.dom4j.Attribute;
import org.dom4j.Node;
import org.dom4j.tree.DefaultElement;

import com.xia.search.solr.schema.FieldAdaptor;
import com.xia.search.solr.util.XmlUtil;

/**
 * solr schema的操作
 * 
 * @author calvinx
 * 
 */
public class SchemaConfig {
	private final Log logger = LogFactory.getLog(getClass());
	private static final String SCHEMA_FILE=SolrResourceLoader.locateSolrHome() + "conf/schema.xml";
	private List<FieldAdaptor> fields = new ArrayList<FieldAdaptor>();
	private List<FieldAdaptor> dynamicFields = new ArrayList<FieldAdaptor>();
	private XmlUtil xmlUtil;

	public SchemaConfig() {
		xmlUtil = new XmlUtil(new File(SCHEMA_FILE));
		init();
	}

	private void init() {
		List<Node> nodes = xmlUtil.getNodes(FieldAdaptor.FIELD);
		convert(nodes, fields);

		nodes = xmlUtil.getNodes(FieldAdaptor.DYMAIC_FIELD);
		convert(nodes, dynamicFields);

	}

	private void convert(List<Node> nodes, List<FieldAdaptor> fields) {
		for (Node node : nodes) {
			DefaultElement element = (DefaultElement) node;
			String name = element.attribute("name").getValue();
			String type = element.attribute("type").getValue();
			Boolean indexed = getBoolean(element, "indexed");
			Boolean stored = getBoolean(element, "stored");
			fields.add(FieldAdaptor.newField(name, type, indexed, stored));
		}
	}

	private Boolean getBoolean(DefaultElement element, String name) {
		Attribute attribute = element.attribute(name);
		if (attribute == null) {
			return false;
		}
		return Boolean.valueOf(attribute.getValue());
	}

	public void addField(FieldAdaptor field) {
		if(!fields.contains(field)) {
			logger.info("Add new Field:"+field);
			fields.add(field);
		}
	}

	public List<FieldAdaptor> getFields() {
		return Collections.unmodifiableList(fields);
	}

	public List<FieldAdaptor> getDynamicFields() {
		return Collections.unmodifiableList(dynamicFields);
	}

	public FieldAdaptor getFieldByName(String name) {
		for (FieldAdaptor f : fields) {
			if (StringUtils.equals(f.getFieldName(), name)) {
				return f;
			}
		}
		return null;
	}

	public void save() {
		for (FieldAdaptor f : fields) {
			DefaultElement parentNode = (DefaultElement) xmlUtil.getSingleNode(FieldAdaptor.FIELD_PARENT);
			if(!xmlUtil.isExistsAttribute(FieldAdaptor.FIELD+"/@name", f.getFieldName())) {
				//write Field if not exists
				DefaultElement childElement = new DefaultElement(FieldAdaptor.FIELD_NAME);
				childElement.addAttribute("name", f.getFieldName());
				childElement.addAttribute("type", f.getType());
				childElement.addAttribute("indexed", ""+f.isIndexed());
				childElement.addAttribute("stored", ""+f.isStored());
				childElement.addAttribute("termVectors", ""+f.isStoreTermVector());
				//childElement.setParent(parentNode);
				parentNode.add(childElement);
			}
		}
		xmlUtil.wirteToFile("D:/a.xml","UTF-8");//tmp file for observer
		xmlUtil.wirteToFile(SCHEMA_FILE,"UTF-8");
	}


}
