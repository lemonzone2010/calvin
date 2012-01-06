package com.apusic.soquick;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class QueryTest extends BaseTestCase {

	@Test
	public void testOr() {
		Query query = new Query(ItemH.class, "content", "武汉");
		logger.info(query.toString());
		assertEquals("F_Table.F_content:武汉", query.toString());
		query.or("content", "武汉");
		assertEquals("F_Table.F_content:武汉 OR F_Table.F_content:武汉", query.toString());
	}
	@Test
	public void testAnd() {
		Query query = new Query(ItemH.class, "content", "武汉");
		logger.info(query.toString());
		assertEquals("F_Table.F_content:武汉", query.toString());
		query.and("content", "武汉");
		assertEquals("F_Table.F_content:武汉 AND F_Table.F_content:武汉", query.toString());
	}
	@Test
	public void testNot() {
		Query query = new Query(ItemH.class, "content", "武汉");
		logger.info(query.toString());
		assertEquals("F_Table.F_content:武汉", query.toString());
		query.not("content", "武汉");
		assertEquals("F_Table.F_content:武汉 NOT F_Table.F_content:武汉", query.toString());
	}

	@Test
	public void testRang() {
		Query query = new Query(ItemH.class, "content", "武汉");
		logger.info(query.toString());
		assertEquals("F_Table.F_content:武汉", query.toString());
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(2010, 1, 1);
		query.andRange("content", calendar.getTime());
		//assertEquals("F_Table.F_content:武汉 AND RANGE[20100201121011 TO *]", query.toString());
		logger.info(query);
	}
	@Test
	public void testWildChar() {
		try {
			new Query(ItemH.class, "F_Table.*", "*");
			Assert.fail("Should throw an error");
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Query(ItemH.class, "*", "*");
	}
	
}
