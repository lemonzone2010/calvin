package com.xia.search.solr.util;

import org.junit.Assert;
import org.junit.Test;

import com.xia.search.solr.schema.FieldAdaptor;

public class JasonUtilTest {

	@Test
	public void toJsonString() {
		FieldAdaptor f = FieldAdaptor.newField("xxx", "maxWord");
		String jsonString = JasonUtil.toJsonString(f);
		System.out.println(jsonString);
		Assert.assertEquals(jsonString, "{\"stored\":false,\"indexed\":true,\"tokenized\":true,\"entityName\":\"\",\"storeTermVector\":false,\"fieldsData\":null,\"type\":\"maxWord\",\"fieldName\":\"xxx\"}");
	}

	@Test
	public void toObjectFromJson() {
		String jsonString="{\"stored\":false,\"indexed\":true,\"tokenized\":true,\"entityName\":\"\",\"storeTermVector\":false,\"fieldsData\":null,\"type\":\"maxWord\",\"fieldName\":\"xxx\"}";
		FieldAdaptor f = JasonUtil.toObjectFromJson(jsonString, FieldAdaptor.class);
		System.out.println(f);
		Assert.assertEquals(f.getFieldName(), "xxx");
	}
}
