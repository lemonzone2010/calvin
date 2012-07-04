package com.xia.search.solr.schema.event;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class HibernateSolrIntegrator implements Integrator {
	private FullTextIndexEventListener listener;
	private static Configuration configuration;
	@Override
	@SuppressWarnings("unchecked")
	public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		HibernateSolrIntegrator.configuration=configuration;
		
		//SolrEventInitalize.setConfiguration(configuration);


		listener = new FullTextIndexEventListener(  );

		EventListenerRegistry listenerRegistry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
				EventListenerRegistry.class);
		//TODO if the event is duplicated, do not initialize the newly created listener
		listenerRegistry.addDuplicationStrategy( new DuplicationStrategyImpl( FullTextIndexEventListener.class ) );
		listenerRegistry.getEventListenerGroup( EventType.POST_INSERT ).appendListener( listener );
		listenerRegistry.getEventListenerGroup( EventType.POST_UPDATE ).appendListener( listener );
		listenerRegistry.getEventListenerGroup( EventType.POST_DELETE ).appendListener( listener );
		listenerRegistry.getEventListenerGroup( EventType.POST_COLLECTION_RECREATE ).appendListener( listener );
		listenerRegistry.getEventListenerGroup( EventType.POST_COLLECTION_REMOVE ).appendListener( listener );
		listenerRegistry.getEventListenerGroup( EventType.POST_COLLECTION_UPDATE ).appendListener( listener );
		listenerRegistry.getEventListenerGroup( EventType.FLUSH ).appendListener( listener );
		//listenerRegistry.getEventListenerGroup( EventType.LOAD ).appendListener( listener );
		
		
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



	public static Configuration getConfiguration() {
		return configuration;
	}

}
