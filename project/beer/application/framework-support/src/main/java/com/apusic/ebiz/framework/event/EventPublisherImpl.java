package com.apusic.ebiz.framework.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.apusic.ebiz.framework.integration.jms.QueueService;

@Service("ebiz_EventPublisher")
public class EventPublisherImpl implements EventPublisher{
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired 
	private QueueService queueService;
	
	public void publish(ApplicationEvent event) {
		if (event instanceof GlobalEvent){
			GlobalEvent gEvent = (GlobalEvent) event;
			queueService.send(gEvent.getEventTopic(), gEvent);
		}else{
			applicationEventPublisher.publishEvent(event);
		}
	}

	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
