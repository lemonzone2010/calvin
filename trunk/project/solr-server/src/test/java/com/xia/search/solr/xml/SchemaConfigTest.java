package com.xia.search.solr.xml;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.xia.search.solr.xml.SchemaConfig.Field;

public class SchemaConfigTest {
	SchemaConfig config = new SchemaConfig();

	@Test
	public void getFields() {
		List<Field> fields = config.getFields();
		Assert.assertTrue(fields.size() > 1);
	}

	@Test
	public void getDynamicFields() {
		List<Field> fields = config.getDynamicFields();
		Assert.assertTrue(fields.size() > 1);
	}

	@Test
	public void getSchemaConfig() {
		List<Field> fields = SolrConfig.getSchemaConfig().getFields();
		Assert.assertTrue(fields.size() > 1);
		
		SolrConfig.getSchemaConfig().save();
	}
}
