package com.xia.search.solr.hibernate;

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
}
