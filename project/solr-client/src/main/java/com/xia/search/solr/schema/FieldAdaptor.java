package com.xia.search.solr.schema;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Field;

public class FieldAdaptor{
	public static final String FIELD = "//schema/fields/field";
	public static final String FIELD_NAME = "field";
	public static final String FIELD_PARENT = "//schema/fields";
	public static final String DYMAIC_FIELD = "//schema/fields/dynamicField";
	public static final String HIBERNATE_CLASS_FLAG = "hibernate_class";
	
	private String entityName = "";
	private String fieldName = "body";
	private String type="text";
	private boolean storeTermVector = false;
	private boolean stored = false;
	private boolean indexed = true;
	private boolean tokenized = true;
	private Object fieldsData = null;

	public FieldAdaptor() {
	}
	public FieldAdaptor(Field field) {
		setFieldName(field.name());
		setStoreTermVector(field.isTermVectorStored());
		setStored(field.isStored());
		setIndexed(field.isIndexed());
		setTokenized(field.isTokenized());
		try {
			java.lang.reflect.Field dataField = field.getClass().getSuperclass().getDeclaredField("fieldsData");
			dataField.setAccessible(true);
			fieldsData= dataField.get(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.contains(fieldName, HIBERNATE_CLASS_FLAG)) {
			indexed=false;
		}
	}
	public static FieldAdaptor newField(String name, String type) {
		return new FieldAdaptor(name, type, true, false, false);
	}

	public static FieldAdaptor newField(String name, String type, boolean indexed, boolean stored) {
		return new FieldAdaptor(name, type, indexed, stored, false);
	}
	public FieldAdaptor(String name, String type, boolean indexed, boolean stored, boolean termVectors) {
		this.fieldName = name;
		this.type = type;
		this.indexed = indexed;
		this.stored = stored;
		this.storeTermVector = termVectors;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getFieldName() {
		return fieldName;
	}
	public String getSolrName() {
		return entityName+"."+fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isStoreTermVector() {
		return storeTermVector;
	}

	public void setStoreTermVector(boolean storeTermVector) {
		this.storeTermVector = storeTermVector;
	}


	public boolean isStored() {
		return stored;
	}

	public void setStored(boolean stored) {
		this.stored = stored;
	}

	public boolean isIndexed() {
		return indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}

	public boolean isTokenized() {
		return tokenized;
	}

	public void setTokenized(boolean tokenized) {
		this.tokenized = tokenized;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof FieldAdaptor))return false;
		FieldAdaptor t=(FieldAdaptor) obj;
		return StringUtils.equals(getFieldName(), t.getFieldName())&&StringUtils.equals(getEntityName(), t.getEntityName());
	}
	@Override
	public String toString() {
		return "FieldAdaptor [entityName=" + entityName + ", fieldName=" + fieldName + ", type=" + type + ", stored="
				+ stored + ", indexed=" + indexed + "]";
	}
	public Object getFieldsData() {
		return fieldsData;
	}
	public void setFieldsData(Object fieldsData) {
		this.fieldsData = fieldsData;
	}

}