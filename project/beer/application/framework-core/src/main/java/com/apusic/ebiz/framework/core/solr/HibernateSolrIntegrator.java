package com.apusic.ebiz.framework.core.solr;

import java.util.Iterator;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class HibernateSolrIntegrator implements Integrator{

	@Override
	public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub
		System.out.println(configuration);
		Iterator<PersistentClass> classMappings = configuration.getClassMappings();
		while(classMappings.hasNext()) {
			PersistentClass next = classMappings.next();
			System.out.println(next);
		}
		//TODO 解析实体类，将@Field定义的实体及相关的属性，提交到solr/addSchema?
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
