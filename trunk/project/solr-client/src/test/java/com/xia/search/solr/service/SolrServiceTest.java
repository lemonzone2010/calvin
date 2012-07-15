package com.xia.search.solr.service;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.xia.search.solr.dao.AbstractHibernateTest;
import com.xia.search.solr.dao.DummyBook;
import com.xia.search.solr.dao.HibernateUtil;
import com.xia.search.solr.hibernate.HibernateContext;
import com.xia.search.solr.hibernate.HibernateContext.HibernateCallback;
import com.xia.search.solr.query.FacetQuery;
import com.xia.search.solr.query.FacetQuery.DateRange;
import com.xia.search.solr.query.FacetQuery.NumericRange;
import com.xia.search.solr.query.Page;
import com.xia.search.solr.query.Result;
import com.xia.search.solr.service.SolrService;
import com.xia.search.solr.service.SolrServiceImpl;

public class SolrServiceTest extends AbstractHibernateTest{
	SolrService mySolrService=new SolrServiceImpl("http://localhost:8080/solr-server",HibernateUtil.getSessionFactory());
	
	@Test
	public void indexAll() throws Exception {
		doInTranstaction(new Callable<List>() {

			@Override
			public List call() throws Exception {
				Query query = session.createQuery("from DummyBook");
				List list = query.list();
				//索引所有
				Result result = mySolrService.indexSaveOrUpdate(list.toArray());
				
				assertTrue(result.isStatus());
				
			//	query = session.createQuery("from DummyAuthor");
			//	List list1 = query.list();
				//索引所有
			//	result = mySolrService.indexSaveOrUpdate(list1.toArray());
				
				assertTrue(result.isStatus());
				
				
				return null;
			}
		});
	}
	@Test
	public void update() throws Exception {
		List list=doInTranstaction(new Callable<List>() {

			@Override
			public List call() throws Exception {
				Query query = session.createQuery("from DummyBook");
				List list = query.list();
				//索引所有
				Result result = mySolrService.indexSaveOrUpdate(list.toArray());
				
				assertTrue(result.isStatus());
				
				query = session.createQuery("from DummyAuthor");
				List list1 = query.list();
				//索引所有
				result = mySolrService.indexSaveOrUpdate(list1.toArray());
				
				assertTrue(result.isStatus());
				
				
				return list;
			}
		});
		
		//-----更新
		String id = mySolrService.getSolrId((DummyBook) list.get(0));
		final DummyBook book = (DummyBook) list.get(0);
		doInTranstaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				book.setTitle("New Title:夏勇");
				session.update(book);
				return null;
			}
		});


		//-----删除 
		final DummyBook book1 = (DummyBook) list.get(1);
		doInTranstaction(new Callable<Object>() {
			
			@Override
			public Object call() throws Exception {
				session.delete(book1);
				return null;
			}
		});
		
	}
	@Test
	public void isExists() throws Exception {
		List list=doInTranstaction(new Callable<List>() {
			
			@Override
			public List call() throws Exception {
				Query query = session.createQuery("from DummyBook");
				List list = query.list();
				return list;
			}
		});
		Object exists = ((SolrServiceImpl)mySolrService).getSolrId(list.get(0));
		System.out.println(exists);
	}
	@Test
	public void query() throws Exception {
		Page<DummyBook> page=doInTranstaction(new Callable<Page<DummyBook>>() {
			
			@Override
			public Page<DummyBook> call() throws Exception {
				com.xia.search.solr.query.Query query = new com.xia.search.solr.query.Query (DummyBook.class, "*", "*");
				return mySolrService.query(query);
			}
		});
		assertTrue(page.getNumFound()>0);
		System.out.println(page);
	}
	
	@Test
	public void getIdFromSolr() throws SolrServerException, IOException {
		DummyBook book=new DummyBook();
		book.setId(2);
		String id = mySolrService.getSolrId(book);
		assertNotNull(id);
		id = mySolrService.getSolrId(book);//just get from mapping.
		assertNotNull(id);
	}
	@Test
	public void suggest() throws Exception {
		List<String> page = mySolrService.suggest("夏");
		assertNotNull(page);
		System.out.println(page);
		for (String s : page) {
			System.out.println(s);
		}
	}
	
	@Test
	public void facetQuery() throws Exception {
		FacetQuery query=new FacetQuery(DummyBook.class, "title", "lucene");
		query.facetFields.add("{!key=作者}"+query.getFieldName("authors.name"));
		query.numericRangeFields.add(new NumericRange(query.getFieldName("price"), 0, 500, 50));
		Calendar startCal = Calendar.getInstance();
		startCal.add(Calendar.YEAR, -5);
		Date startDate = startCal.getTime();
		Date endDate = Calendar.getInstance().getTime();
		query.dateRangeFields.add(new DateRange(query.getFieldName("publicationDate"),  startDate, endDate, "+1MONTH"));
		query.highlightFields.add(query.getFieldName("title"));
		QueryResponse facetQuery = mySolrService.facetQuery(query);
		System.out.println(facetQuery);
	}
}
