package com.xia.search.solr.schema;

import java.util.ArrayList;
import java.util.List;

public final class SolrSchemaDocument {
	private String entityName;
	private List<FieldAdaptor> fields = new ArrayList<FieldAdaptor>();

	public SolrSchemaDocument() {
	}

	public String getIdValue() {
		Object fieldsData = getField("id").getFieldsData();
		return fieldsData == null ? null : fieldsData.toString();
	}

	public final void add(FieldAdaptor field) {
		fields.add(field);
	}

	public FieldAdaptor getField(String fieldName) {
		for (FieldAdaptor field : fields) {
			if (field.getFieldName().equals(fieldName))
				return field;
		}
		return null;
	}

	public final List<FieldAdaptor> getFields() {
		return fields;
	}

	@Override
	public final String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("Document<");
		for (int i = 0; i < fields.size(); i++) {
			FieldAdaptor field = fields.get(i);
			buffer.append(field.toString());
			if (i != fields.size() - 1)
				buffer.append(" ");
		}
		buffer.append(">");
		return buffer.toString();
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
}