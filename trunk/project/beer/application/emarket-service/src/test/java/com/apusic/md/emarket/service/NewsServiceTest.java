package com.apusic.md.emarket.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.md.model.emarket.News;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-datamanagement-service.xml",
		"classpath*:apusic-md-emarket-service.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-md-emarket-service-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class NewsServiceTest {

	@Autowired
	private NewsService newsService;

	@Autowired
	private CrudService crudService;

	@Test
	public void createNews() {
		News news1 = new News();
		news1.setName("name1");
		news1.setContent("nameName");
		news1.setCreateTime(new Date());

		News news2 = new News();
		news2.setName("name2");
		news2.setContent("nameName");
		news2.setCreateTime(new Date());

		News news3 = new News();
		news3.setName("asdf");
		news3.setContent("nameName");
		news3.setCreateTime(new Date());

		newsService.createNews(news1);
		newsService.createNews(news2);
		newsService.createNews(news3);
		Assert.assertTrue(news1.getId()>0);
		Assert.assertTrue(news2.getId()>0);
		Assert.assertTrue(news3.getId()>0);
	}

	@Test
	@Transactional
	public void updateNews(){
		News news = crudService.retrieve(News.class, 1);
		Assert.assertTrue(news.getId()>0);
		news.setContent("123456");
		newsService.updateNews(news);
		News news2 = crudService.retrieve(News.class, 1);
		Assert.assertEquals("123456",news2.getContent());
	}

	@Test
	public void findNews4Page() {
		GenericQueryObject queryObject = new GenericQueryObject(News.class);
		queryObject.like("name", "name", MatchMode.ANYWHERE);
		Page<News> newsPage = newsService.findNews4Page(queryObject);
		Assert.assertTrue(newsPage.getTotal()==2);
		for(News news:newsPage.getObjects()){
			Assert.assertTrue(news.getId()>0);
		}
	}

	@Test
	public void getAllNews() {
		Page<News> newsPage = newsService.getAllNews();
		Assert.assertTrue(newsPage.getTotal()==3);
		for(News news:newsPage.getObjects()){
			Assert.assertTrue(news.getId()>0);
		}
	}

	@Test
	public void getTopNews(){
		List<News> newsList = newsService.getTopNews();
		Assert.assertTrue(newsList.size()==3);
	}


	@Test
	public void deleteNewsById(){
		newsService.deleteNewsById(1);
		News news = crudService.retrieve(News.class, 1);
		Assert.assertNull(news);
	}




}
