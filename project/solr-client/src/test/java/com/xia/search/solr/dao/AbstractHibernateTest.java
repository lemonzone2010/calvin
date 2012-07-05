package com.xia.search.solr.dao;

import java.util.concurrent.Callable;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

public class AbstractHibernateTest {
	protected Session session;

	@Before
	public void setup(){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	protected <T> T doInTranstaction(Callable<T> call) throws Exception {
		T ret;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		ret=call.call();

		session.getTransaction().commit();
		return ret;
	}

	@After
	public void teardown(){
		if(session!=null){
			try{
				session.close();
			}catch (Exception e) {
			}
		}
	}
}
