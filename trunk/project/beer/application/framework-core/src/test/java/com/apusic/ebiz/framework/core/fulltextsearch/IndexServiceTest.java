package com.apusic.ebiz.framework.core.fulltextsearch;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.DummyBook;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.QueryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class IndexServiceTest {

	@Autowired
	private IndexService indexService;

	@Autowired
	private CrudService crudService;

	@Autowired
	private FullTextQueryService fullTextQueryService;
	@Autowired
	@Qualifier("queryRepository")
	QueryService queryService;

	@Test
	@Transactional
	public void indexAll(){
		 indexService.index(DummyBook.class);
		 //If no error occurs then index success, so we don't need an assertion here
	}

	@Test
	@Transactional
	public void index(){
		List<DummyBook> findAll = queryService.findAll(DummyBook.class);
		//Before index
		DummyBook book = crudService.retrieve(DummyBook.class, 1);
		//indexService.purge(DummyBook.class, 1);

		FullTextQueryParameter param = new FullTextQueryParameter<DummyBook>(
				DummyBook.class, new String[]{"世界"}, new String[] { "title"});
		List<DummyBook> books= fullTextQueryService.find(param, 0, 10);
		//Assert.assertEquals(0, books.size());

		//Reindex and try again
		book.setTitle("活着就是为了改变世界");
		//crudService.update(book);
		indexService.index(DummyBook.class, 1);
		books= fullTextQueryService.find(param, 0, 10);
		Assert.assertEquals(1, books.size());


	}



	@Test
	@Transactional
	public void purge(){

		DummyBook book = crudService.retrieve(DummyBook.class, 2);
		indexService.purge(DummyBook.class, 2);
		FullTextQueryParameter param = new FullTextQueryParameter<DummyBook>(
				DummyBook.class, new String[]{"Kochkunst."}, new String[] { "title"});
		List<DummyBook> books= fullTextQueryService.find(param, 0, 10);
		Assert.assertEquals(0, books.size());
	}

	@Test
	@Transactional
	public void purgeAll(){
		indexService.purgeAll(DummyBook.class);
	}
}
