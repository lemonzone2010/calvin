package com.xia.search.solr.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.core.SolrResourceLoader;
import org.dom4j.Attribute;
import org.dom4j.Node;
import org.dom4j.tree.DefaultElement;

import com.xia.search.solr.util.XmlUtil;

/**
 * solr schema的操作
 * 
 * @author calvinx
 * 
 */
public class SchemaConfig {
	private List<SchemaConfig.Field> fields = new ArrayList<SchemaConfig.Field>();
	private List<SchemaConfig.Field> dynamicFields = new ArrayList<SchemaConfig.Field>();
	private XmlUtil xmlUtil;

	public SchemaConfig() {
		xmlUtil = new XmlUtil(new File(SolrResourceLoader.locateSolrHome() + "conf/schema.xml"));
		init();
	}

	private void init() {
		List<Node> nodes = xmlUtil.getNodes(Field.FIELD);
		convert(nodes, fields);

		nodes = xmlUtil.getNodes(Field.DYMAIC_FIELD);
		convert(nodes, dynamicFields);

	}

	private void convert(List<Node> nodes, List<SchemaConfig.Field> fields) {
		for (Node node : nodes) {
			DefaultElement element = (DefaultElement) node;
			String name = element.attribute("name").getValue();
			String type = element.attribute("type").getValue();
			Boolean indexed = getBoolean(element, "indexed");
			Boolean stored = getBoolean(element, "stored");
			fields.add(new Field(name, type, indexed, stored));
		}
	}

	private Boolean getBoolean(DefaultElement element, String name) {
		Attribute attribute = element.attribute(name);
		if (attribute == null) {
			return false;
		}
		return Boolean.valueOf(attribute.getValue());
	}

	public void addField(SchemaConfig.Field field) {
		fields.add(field);
	}

	public List<SchemaConfig.Field> getFields() {
		return fields;
	}

	public List<SchemaConfig.Field> getDynamicFields() {
		return dynamicFields;
	}

	public SchemaConfig.Field getFieldByName(String name) {
		for (Field f : fields) {
			if (StringUtils.equals(f.getName(), name)) {
				return f;
			}
		}
		return null;
	}

	public void save() {
		for (Field f : fields) {
			if(!xmlUtil.isExistsAttribute(Field.FIELD+"/@name", f.getName())) {
				//write Field if not exists
			}
			xmlUtil.wirteToFile("D:/a.xml","UTF-8");
		}
	}

	public static class Field {
		// <field name="includes" type="text_general" indexed="true"
		// stored="true" termVectors="true" termPositions="true"
		// termOffsets="true" />
		public static final String FIELD = "//schema/fields/field";
		public static final String DYMAIC_FIELD = "//schema/fields/dynamicField";
		private String name;
		private String type;
		private boolean indexed = false;
		private boolean stored = false;
		private boolean termVectors = false;
		private boolean termPositions = false;
		private boolean termOffsets = false;

		public Field(String name, String type) {
			this(name, type, false, false, false, false, false);
		}

		public Field(String name, String type, boolean indexed, boolean stored) {
			this(name, type, indexed, stored, false, false, false);
		}

		public Field(String name, String type, boolean indexed, boolean stored, boolean termVectors,
				boolean termPositions, boolean termOffsets) {
			this.name = name;
			this.type = type;
			this.indexed = indexed;
			this.stored = stored;
			this.termVectors = termVectors;
			this.termPositions = termPositions;
			this.termOffsets = termOffsets;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean isIndexed() {
			return indexed;
		}

		public void setIndexed(boolean indexed) {
			this.indexed = indexed;
		}

		public boolean isStored() {
			return stored;
		}

		public void setStored(boolean stored) {
			this.stored = stored;
		}

		public boolean isTermVectors() {
			return termVectors;
		}

		public void setTermVectors(boolean termVectors) {
			this.termVectors = termVectors;
		}

		public boolean isTermPositions() {
			return termPositions;
		}

		public void setTermPositions(boolean termPositions) {
			this.termPositions = termPositions;
		}

		public boolean isTermOffsets() {
			return termOffsets;
		}

		public void setTermOffsets(boolean termOffsets) {
			this.termOffsets = termOffsets;
		}
	}

}
