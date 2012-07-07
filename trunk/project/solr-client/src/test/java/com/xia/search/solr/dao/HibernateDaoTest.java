package com.xia.search.solr.dao;

import static junit.framework.Assert.assertNotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.hibernate.Query;
import org.junit.Test;

/**
 * This test case is intended to do smoke test on the mapping.
 * Besides, this test case will isolate the unit test from Oracle by
 * leverage Derby as the database
 * @author apusican
 *
 */
public class HibernateDaoTest extends AbstractHibernateTest{
	@Test
	public void query() throws Exception{

		List list=doInTranstaction(new Callable<List>() {

			@Override
			public List call() throws Exception {
				Query query = session.createQuery("from DummyBook");
				List list = query.list();
				return list;
			}
		});
		assertNotNull(list);

	}
	@Test
	public void insert() throws Exception{
		
		Serializable list=doInTranstaction(new Callable<Serializable>() {
			
			@Override
			public Serializable call() throws Exception {
				DummyBook book=new DummyBook();
				book.setTitle("我是你大爷夏勇先生一天二天三天11111");
				book.setSubtitle("是你哦，啊昌听");
				book.setPublicationDate(new Date());		
				return session.save(book);
			}
		});
		assertNotNull(list);
		
	}
	@Test
	public void insertN() throws Exception{
		
		Serializable list=doInTranstaction(new Callable<Serializable>() {
			
			@Override
			public Serializable call() throws Exception {
				for (int i = 0; i < 15; i++) {
					
				DummyBook book=new DummyBook();
				book.setTitle("夏勇是什么"+i);
				book.setSubtitle("test"+i);
				book.setPublicationDate(new Date());		
				session.save(book);
				}
			 return null;
			}
		});
		
	}


}
