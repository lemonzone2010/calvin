package com.xia.search.solr.xml;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.xia.search.solr.schema.FieldAdaptor;

public class SchemaConfigTest {
	SchemaConfig config = new SchemaConfig();

	@Test
	public void getFields() {
		List<FieldAdaptor> fields = config.getFields();
		Assert.assertTrue(fields.size() > 1);
	}

	@Test
	public void getDynamicFields() {
		List<FieldAdaptor> fields = config.getDynamicFields();
		//Assert.assertTrue(fields.size() > 1);//May be not defined denamic fields
	}

	@Test
	public void saveSchemaConfig() {
		List<FieldAdaptor> fields = SolrConfig.getSchemaConfig().getFields();
		Assert.assertTrue(fields.size() > 1);
		FieldAdaptor newFile=FieldAdaptor.newField("ages_xiass", "text");
		SolrConfig.getSchemaConfig().addField(newFile);
		SolrConfig.getSchemaConfig().save();
	}
	
	@Test
	public void addField() {
		List<FieldAdaptor> fields = SolrConfig.getSchemaConfig().getFields();
		int size = fields.size();
		Assert.assertTrue(size > 1);
		FieldAdaptor newFile=FieldAdaptor.newField("ages___---", "text");
		
		SolrConfig.getSchemaConfig().addField(newFile);		
		Assert.assertEquals(size+1, fields.size());
		
		//add the same name field ,do not add into list
		SolrConfig.getSchemaConfig().addField(newFile);
		Assert.assertEquals(size+1, fields.size());
		
	}
}
