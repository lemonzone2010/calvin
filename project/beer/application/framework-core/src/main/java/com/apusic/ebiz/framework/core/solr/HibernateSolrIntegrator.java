package com.apusic.ebiz.framework.core.solr;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Value;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class HibernateSolrIntegrator implements Integrator{
@Override
	@SuppressWarnings("unchecked")
	public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		
		//generateSolrMapping(configuration);
	}


	@Override
	public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub
		
	}

}
