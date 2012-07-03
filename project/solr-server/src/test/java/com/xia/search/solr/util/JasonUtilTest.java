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
		Assert.assertEquals(jsonString, "{\"entityName\":\"\",\"fieldName\":\"xxx\",\"type\":\"maxWord\",\"storeTermVector\":false,\"stored\":false,\"indexed\":true,\"tokenized\":true}");
	}

	@Test
	public void toObjectFromJson() {
		String jsonString="{\"entityName\":\"DummyBook\",\"fieldName\":\"xxx\",\"storeTermVector\":false,\"stored\":true,\"indexed\":true,\"tokenized\":false}";
		FieldAdaptor f = JasonUtil.toObjectFromJson(jsonString, FieldAdaptor.class);
		System.out.println(f);
		Assert.assertEquals(f.getFieldName(), "xxx");
	}
}
