package com.apusic.md.emarket.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class AttributeTest {
	@Test
	public void parseAttributeTest(){
		String attributes="颜色:红色|蓝色|黑色;尺码:L|M|XL|XXL";
		List<Attribute> attrs=Attribute.parseAttribute(attributes);
		Assert.assertEquals("颜色", attrs.get(0).getName());
		Assert.assertEquals("XL", attrs.get(1).getItems()[2]);
		Assert.assertEquals(2, attrs.size());
		Assert.assertEquals(3, attrs.get(0).getItems().length);

		attributes="颜色:红色;";
		attrs=Attribute.parseAttribute(attributes);
		Assert.assertEquals("颜色", attrs.get(0).getName());
		Assert.assertEquals("红色", attrs.get(0).getItems()[0]);
	}
}
