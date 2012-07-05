package com.xia.search.solr.event;

import org.hibernate.HibernateException;
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
import org.hibernate.event.spi.LoadEventListener.LoadType;

import com.xia.search.solr.util.SolrContext;

public class FullTextIndexEventListener implements PostDeleteEventListener,
PostInsertEventListener, PostUpdateEventListener,
PostCollectionRecreateEventListener, PostCollectionRemoveEventListener,
PostCollectionUpdateEventListener, FlushEventListener,LoadEventListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		try {
			SolrContext.getMySolrService().update(event.getEntity());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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