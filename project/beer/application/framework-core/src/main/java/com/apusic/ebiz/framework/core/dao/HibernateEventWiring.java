package com.apusic.ebiz.framework.core.dao;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.search.event.impl.FullTextIndexEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateEventWiring {
	
	AtomicBoolean a=new AtomicBoolean();

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private FullTextIndexEventListener fullTextSearchListener;

	@PostConstruct
	public void registerListeners() {
		if(a.get()) {
			return;
		}
		a.set(true);
		final EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
				EventListenerRegistry.class);
//		registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(
//				(PostInsertEventListener) fullTextSearchListener);
//		registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(
//				(PostDeleteEventListener) fullTextSearchListener);
//		registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(
//				(PostUpdateEventListener) fullTextSearchListener);
	}

}