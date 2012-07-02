package com.xia.search.solr.util;

import org.junit.Assert;
import org.junit.Test;

import com.xia.search.solr.xml.SchemaConfig.Field;

public class JasonUtilTest {

	@Test
	public void toJsonString() {
		Field f = Field.newField("xxx", "maxWord");
		String jsonString = JasonUtil.toJsonString(f);
		System.out.println(jsonString);
		Assert.assertEquals(jsonString, "{\"name\":\"xxx\",\"type\":\"maxWord\",\"indexed\":true,\"stored\":false,\"termVectors\":false,\"termPositions\":false,\"termOffsets\":false}");
	}

	@Test
	public void toObjectFromJson() {
		String jsonString = "{\"name\":\"xxx\",\"type\":\"maxWord\",\"indexed\":true,\"stored\":false,\"termVectors\":false,\"termPositions\":false,\"termOffsets\":false}";
		Field f = JasonUtil.toObjectFromJson(jsonString, Field.class);
		System.out.println(f);
		Assert.assertEquals(f.getName(), "xxx");
	}
}
