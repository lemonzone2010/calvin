package com.apusic.ebiz.framework.core.solr;

import javax.annotation.PostConstruct;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.DuplicationStrategy;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.FlushEvent;
import org.hibernate.event.spi.FlushEventListener;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;
import org.hibernate.event.spi.PostCollectionRecreateEvent;
import org.hibernate.event.spi.PostCollectionRecreateEventListener;
import org.hibernate.event.spi.PostCollectionRemoveEvent;
import org.hibernate.event.spi.PostCollectionRemoveEventListener;
import org.hibernate.event.spi.PostCollectionUpdateEvent;
import org.hibernate.event.spi.PostCollectionUpdateEventListener;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.integrator.spi.IntegratorService;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.search.util.logging.impl.Log;
import org.hibernate.search.util.logging.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Integrates Hibernate Search into Hibernate Core by registering its needed listeners
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 * @author Steve Ebersole
 */
@Component
public class HibernateEventWiring {

	private static final Log log = LoggerFactory.make();
	public static final String AUTO_REGISTER = "hibernate.search.autoregister_listeners";

	private FullTextIndexEventListener listener;
	@Autowired
	private SessionFactory sessionFactory;

	@PostConstruct
	public void integrate() {

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
		listenerRegistry.getEventListenerGroup( EventType.LOAD ).appendListener( listener );
		
		
		IntegratorService integratorService = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
				IntegratorService.class);

	}


	public static class DuplicationStrategyImpl implements DuplicationStrategy {
		private final Class checkClass;

		public DuplicationStrategyImpl(Class checkClass) {
			this.checkClass = checkClass;
		}

		@Override
		public boolean areMatch(Object listener, Object original) {
			// not isAssignableFrom since the user could subclass
			return checkClass == original.getClass() && checkClass == listener.getClass();
		}

		@Override
		public Action getAction() {
			return Action.KEEP_ORIGINAL;
		}
	}
	
	public class FullTextIndexEventListener implements PostDeleteEventListener,
	PostInsertEventListener, PostUpdateEventListener,
	PostCollectionRecreateEventListener, PostCollectionRemoveEventListener,
	PostCollectionUpdateEventListener, FlushEventListener,LoadEventListener{

		@Override
		public void onFlush(FlushEvent event) throws HibernateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPostUpdateCollection(PostCollectionUpdateEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPostRemoveCollection(PostCollectionRemoveEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPostRecreateCollection(PostCollectionRecreateEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPostUpdate(PostUpdateEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPostInsert(PostInsertEvent event) {
			// TODO Auto-generated method stub
			System.out.println(event);
		}

		@Override
		public void onPostDelete(PostDeleteEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onLoad(LoadEvent event, LoadType loadType) throws HibernateException {
			// TODO Auto-generated method stub
			
			System.out.println(event);
		}
		
	}

}
