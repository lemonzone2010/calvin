package com.xia.search.solr;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.xia.search.solr.BaseTestCase;
import com.xia.search.solr.dao.DummyBook;
import com.xia.search.solr.dao.HibernateUtil;
import com.xia.search.solr.query.Query;

public class QueryTest extends BaseTestCase {

	@Before
	public void setup() {
		HibernateUtil.getSessionFactory().getCurrentSession();
	}
	@Test
	public void testOr() {
		Query query = new Query(DummyBook.class, "title", "武汉");
		logger.info(query.toString());
		assertEquals("DummyBook.title:武汉", query.toString());
		query.or("title", "武汉");
		assertEquals("DummyBook.title:武汉 OR DummyBook.title:武汉", query.toString());
	}
	@Test
	public void testAnd() {
		Query query = new Query(DummyBook.class, "title", "武汉");
		logger.info(query.toString());
		assertEquals("DummyBook.title:武汉", query.toString());
		query.and("title", "武汉");
		assertEquals("DummyBook.title:武汉 AND DummyBook.title:武汉", query.toString());
	}
	@Test
	public void testNot() {
		Query query = new Query(DummyBook.class, "title", "武汉");
		logger.info(query.toString());
		assertEquals("DummyBook.title:武汉", query.toString());
		query.not("title", "武汉");
		assertEquals("DummyBook.title:武汉 NOT DummyBook.title:武汉", query.toString());
	}

	@Test
	public void testRang() {
		Query query = new Query(DummyBook.class, "title", "武汉");
		logger.info(query.toString());
		assertEquals("DummyBook.title:武汉", query.toString());
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(2010, 1, 1);
		query.andRange("title", calendar.getTime());
		//assertEquals("DummyBook.title:武汉 AND RANGE[20100201121011 TO *]", query.toString());
		logger.info(query);
	}
	@Test
	public void testWildChar() {
		try {
			new Query(DummyBook.class, "DummyBook.*", "*");
			Assert.fail("Should throw an error");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query query = new Query(DummyBook.class, "*", "*");
		assertEquals("DummyBook.id:*", query.toString());
	}
	
}
