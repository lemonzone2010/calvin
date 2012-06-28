package com.apusic.ebiz.framework.core.fulltextsearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.lucene.search.SortField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.DummyBook;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.fulltextsearch.filter.DoubleRangeFilter;
import com.apusic.ebiz.framework.core.fulltextsearch.filter.Filter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class FullTextQueryServiceTest {

	private static Logger log = LoggerFactory.getLogger(FullTextQueryServiceTest.class);


	@Autowired
	private IndexService indexService;

	@Autowired
	private FullTextQueryService fullTextQueryService;

	@Autowired
	private CrudService crudService;

	private boolean isIndexCreated = false;

	public void setup(){
		//Index...
		log.debug("Indexing the book data");
		indexService.index(DummyBook.class);
		log.debug("End of indexing the book data");
		this.isIndexCreated = true;
	}

	@Test
	@Transactional
	public void find() {
		if (!isIndexCreated){
			setup();
		}

		//Search term 'hibernate' on 'title'
		FullTextQueryParameter<DummyBook> param = new FullTextQueryParameter<DummyBook>(
				DummyBook.class, new String[]{"hibernate"}, new String[] { "title"});
		List<DummyBook> books = fullTextQueryService.find(param, 0, 10);
		assertEquals("Should find one book", 1, books.size());
		assertEquals("Right title", "Java Persistence with Hibernate", books.get(0).getTitle());

		//Searc term 'hibernate' on 'title' and 'alex chen' on 'subtitle'
		param = new FullTextQueryParameter<DummyBook>(
					DummyBook.class, new String[]{"hibernate", "alex chen"}, new String[] { "title",
							"subtitle"});
		books = fullTextQueryService.find(param, 0, 10);
		assertEquals("Should find no books", 0, books.size());

		//Search term 'Programmer' on 'title'
		param.setMatching(new String[]{"Programmer"});
		param.setFields(new String[]{"title"});
		books = fullTextQueryService.find(param, 0, 10);
		assertEquals("Should find 4 books", 4, books.size());



		param.setMatching(new String[]{"Programmer", "alex chen"});
		param.setFields(new String[]{"subtitle", "authors.name"});

		books = fullTextQueryService.find(param, 0, 10);

		assertEquals("Should find 1 book", 1, books.size());


		//pagination test 分页测试
		param.setMatching(new String[]{"Programmer"});
		param.setFields(new String[]{"title"});
		books = fullTextQueryService.find(param, 0, 3);
		assertEquals("Should find 3 boos", 3, books.size());

		param.setMatching(new String[]{"Programmer"});
		param.setFields(new String[]{"title"});
		books = fullTextQueryService.find(param, 1, 10);
		assertEquals("Should find 3 books", 3, books.size());


		param.setMatching(new String[]{"Programmer"});
		param.setFields(new String[]{"title"});
		books = fullTextQueryService.find(param, 2, 4);
		assertEquals("Should find 2 book", 2, books.size());

		//sorting test 排序测试
		param.setMatching(new String[]{"Programmer"});
		param.setFields(new String[]{"title"});
		SortField sortFieldAsc = new SortField("publicationDate", SortField.STRING);
		param.setSortFields(new SortField[]{sortFieldAsc});
		books = fullTextQueryService.find(param, 0, 3);
		boolean isDesc = books.get(0).getPublicationDate().before(books.get(1).getPublicationDate());

		param.setMatching(new String[]{"Programmer"});
		param.setFields(new String[]{"title"});
		SortField sortFieldDesc = new SortField("publicationDate", SortField.STRING, true);
		param.setSortFields(new SortField[]{sortFieldDesc});
		books = fullTextQueryService.find(param, 0, 3);
		boolean isAsc = books.get(0).getPublicationDate().before(books.get(1).getPublicationDate());
		//Reverse
		assertTrue(isDesc^isAsc);


		//Find a term on mutiple fields
		param.setMatching(new String[]{"Programmer"});
		param.setFields(new String[]{"title,subtitle"});
		books = fullTextQueryService.find(param, 0, 20);
		assertEquals("Should find 9 books", 9, books.size());

		//Filter 过滤器测试
		//http://svn.apache.org/viewvc/lucene/java/trunk/src/test/org/apache/lucene/search/TestNumericRangeQuery64.java?view=markup&pathrev=919731
		Filter filter = new DoubleRangeFilter("priceFilter", "price", 100.00d, 200.0d);
		param.setMatching(new String[]{"Programmer"});
		param.setFields(new String[]{"title,subtitle"});
		param.setFilters(new Filter[]{filter});
		books = fullTextQueryService.find(param, 0, 20);
		assertEquals("Should find 2 books", 2, books.size());


		//Reserved keywords
		param.setMatching(new String[]{"\\"});
		param.setFields(new String[]{"title,subtitle"});
		books = fullTextQueryService.find(param, 0, 20);
		assertEquals("Should find 0 books", 0, books.size());

		param.setMatching(new String[]{"ALEX AND"});
		param.setFields(new String[]{"title,subtitle"});
		books = fullTextQueryService.find(param, 0, 20);
		assertEquals("Should find 0 books", 0, books.size());

		param.setMatching(new String[]{"去旅游 OR"});
		param.setFields(new String[]{"title,subtitle"});
		books = fullTextQueryService.find(param, 0, 20);
		assertEquals("Should find 0 books", 0, books.size());


		//CJK tokenizer and analyser
		param = new FullTextQueryParameter<DummyBook>(
				DummyBook.class, new String[]{"陈栋"}, new String[] { "authors.name"});
		books= fullTextQueryService.find(param, 0, 10);

		assertEquals("Should find 0 book", 0, books.size());

		param = new FullTextQueryParameter<DummyBook>(
				DummyBook.class, new String[]{"陈明栋"}, new String[] { "authors.name"});
		books= fullTextQueryService.find(param, 0, 10);

		assertEquals("Should find 1 book",1 , books.size());

		param = new FullTextQueryParameter<DummyBook>(
				DummyBook.class, new String[]{"陈明"}, new String[] { "authors.name"});
		books= fullTextQueryService.find(param, 0, 10);

		assertEquals("Should find 1 book",1 , books.size());
	}



	//@Test
	@Transactional
	public void findPage() {
		if (!isIndexCreated){
			setup();
		}
		FullTextQueryParameter<DummyBook> param = new FullTextQueryParameter<DummyBook>(
				DummyBook.class, new String[]{"100ml"}, new String[] { "title",
						"subtitle", "authors.name" });

		Page page = fullTextQueryService.find(param, 10);
		long rowCnt = page.getTotal();
		assertEquals("Should find 9 books", 9, rowCnt);
	}

	@Test
	@Transactional
	public void getRowCount() {
		if (!isIndexCreated){
			setup();
		}
		FullTextQueryParameter<DummyBook> param = new FullTextQueryParameter<DummyBook>(
				DummyBook.class, new String[]{"Programmer"}, new String[] { "title" });
		long rowCount = fullTextQueryService.getRowCount(param);
		assertEquals("Should find 4 books", 4, rowCount);
	}

}