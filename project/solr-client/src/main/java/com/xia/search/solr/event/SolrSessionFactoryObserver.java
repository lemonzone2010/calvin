package com.xia.search.solr.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;

import com.xia.search.solr.hibernate.HibernateContext;
import com.xia.search.solr.service.SolrContext;

public class SolrSessionFactoryObserver implements SessionFactoryObserver {
	private static final Log logger = LogFactory.getLog(SolrSessionFactoryObserver.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void sessionFactoryCreated(SessionFactory sessionFactory) {
		if (SolrContext.NEED_UPDATE_SCHEMA_AT_START == false) {
			return;
		}
		// update schema to solr
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		updateSolrSchema(HibernateContext.getConfiguration(), session);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void sessionFactoryClosed(SessionFactory factory) {

	}

	private void updateSolrSchema(Configuration configuration, Session session) {
		Iterator<PersistentClass> classMappings = configuration.getClassMappings();
		try {
			List<Object> list = new ArrayList<Object>();
			while (classMappings.hasNext()) {
				PersistentClass persistentClass = classMappings.next();
				Class<?> clazz = Class.forName(persistentClass.getClassName());
				list.add(clazz.newInstance());
			}
			SolrContext.getMySolrService().updateSchema(list.toArray());
		} catch (Exception e) {
			logger.error("solr update schema error" + e.getMessage(), e);
		}
	}

}
