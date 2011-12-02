package com.apusic.soquick;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;

public class SolrServiceTest extends BaseTestCase {
	// String url = "http://172.20.1.87:8083/solr";
	String url = "http://localhost:8081/solr";
	SolrService solrService = new SolrService(url);

	@Test
	public void testAddHibernateItem()  {
		ItemH item = new ItemH();
		item.setId("1230");
		item.setTitle("测试1111");
		item.setPublishedDate(new Date());
		item.setContent("1日下午5时30分左右，武汉市雄楚大街关山中学旁的建设银行网点门前发生爆炸。据武汉市公安局介绍，爆炸造成过路群众2人死亡、10余人受伤。湖北警方已组成专班，开展案件侦破工作。");
		solrService.add(item);
	}

	@Test
	public void testAddHibernateItem1()  {
		ItemH item = new ItemH();
		item.setId("1231");
		item.setTitle("测试2222");
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(2010, 1, 1);
		Date start = calendar.getTime();
		item.setPublishedDate(start);
		item.setContent("夏勇abc");
		solrService.add(item);
	}

	@Test
	public void testQueryHibernateItem_Querys(){
		testAddHibernateItem();
		
		Query query = new Query(ItemH.class, "content", "武汉");
		// query.and("title", "测试");
		// query.not("title", "测试");

		Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.set(2010, 1, 1);
		Calendar calendar1 = Calendar.getInstance(Locale.US);
		calendar1.set(2013, 2, 1);
		query.andRange("publishedDate", calendar.getTime(), calendar1.getTime());
		// query.andRange("publishedDate", calendar.getTime());

		Page<ItemH> result = solrService.query(query);
		logger.info(result);
		assertNotNull(result);
		assertNotNull(result.getResult());
		assertTrue("返回的结果数大于0", result.getResult().size() > 0);
	}
	@Test
	public void testDelete() {
		Query query = new Query(ItemH.class, "content", "武汉");
		solrService.delete(query);
	}
}
