package com.apusic.soquick;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;

public class SolrServiceTest {
	// String url = "http://172.20.1.87:8083/solr";
	String url = "http://localhost:8081/solr";
	SolrService solrService = new SolrService(url);

	@Test
	public void testQuery() throws SolrServerException, IOException {
		Page<Item> result = solrService.query("下午",Item.class);
		System.out.println(result);
		assertNotNull(result);
		assertNotNull(result.getResult());
		assertTrue("返回的结果数大于0", result.getResult().size()>0);
	}

	@Test
	public void testAdd() throws IOException, SolrServerException {
		Item item = new Item();
		item.setId("1230");
		item.setHost("test");
//		item.setPublishedDate(new Date());
		item.setTitle("我是你");
		item.setUrl("http://localhost:8081");
		item.setContent("1日下午5时30分左右，武汉市雄楚大街关山中学旁的建设银行网点门前发生爆炸。据武汉市公安局介绍，爆炸造成过路群众2人死亡、10余人受伤。湖北警方已组成专班，开展案件侦破工作。");
		solrService.add(item);
	}
	@Test
	public void testAddHibernateItem() throws IOException, SolrServerException {
		ItemH item = new ItemH();
		item.setId("1230");
		item.setTitle("测试1111");
		item.setPublishedDate(new Date());
		item.setContent("1日下午5时30分左右，武汉市雄楚大街关山中学旁的建设银行网点门前发生爆炸。据武汉市公安局介绍，爆炸造成过路群众2人死亡、10余人受伤。湖北警方已组成专班，开展案件侦破工作。");
		solrService.add(item);
	}
	@Test
	public void testAddHibernateItem1() throws IOException, SolrServerException {
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
	public void testQueryHibernateItem() throws SolrServerException, IOException {
		Page<ItemH> result = solrService.query("武汉",ItemH.class);
		System.out.println(result);
		assertNotNull(result);
		assertNotNull(result.getResult());
		assertTrue("返回的结果数大于0", result.getResult().size()>0);
	}
	@Test
	public void testQueryHibernateItem_Querys() throws SolrServerException, IOException {
		Query query=new Query(ItemH.class);
		query.and("content", "武汉")
		.and("title", "测试222");
		
		Page<ItemH> result = solrService.query(query);
		System.out.println(result);
		assertNotNull(result);
		assertNotNull(result.getResult());
		assertTrue("返回的结果数大于0", result.getResult().size()>0);
	}

}
