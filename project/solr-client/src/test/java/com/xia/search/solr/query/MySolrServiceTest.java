package com.xia.search.solr.query;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.solr.common.SolrDocument;
import org.hibernate.Query;
import org.junit.Test;

import com.xia.search.solr.dao.AbstractHibernateTest;
import com.xia.search.solr.dao.HibernateUtil;

public class MySolrServiceTest extends AbstractHibernateTest{
	MySolrService mySolrService=new MySolrServiceImpl("http://localhost:8080/solr-server",HibernateUtil.getSessionFactory());
	@Test
	public void update() throws Exception {
		List list=doInTranstaction(new Callable<List>() {

			@Override
			public List call() throws Exception {
				Query query = session.createQuery("from DummyBook");
				List list = query.list();
				return list;
			}
		});
		mySolrService.update(list.toArray());
		System.out.println(12);
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
		Object exists = ((MySolrServiceImpl)mySolrService).getIdFromSolr(list.get(0));
		System.out.println(exists);
	}
}
