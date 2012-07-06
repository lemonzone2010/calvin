package com.xia.search.solr.hibernate;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateContext {
	private static Configuration configuration;
	private static SessionFactory sessionFactory;

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(Configuration configuration) {
		HibernateContext.configuration = configuration;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateContext.sessionFactory = sessionFactory;
	}

	public static <T> T doTransaction(HibernateCallback<T> callback) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		T ret = null;
		try {
			ret = callback.doInHibernateTransaction(session);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ret;
	}

	public interface HibernateCallback<T> {
		T doInHibernateTransaction(Session session) throws HibernateException, SQLException;
	}

}
