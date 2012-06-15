package com.apusic.ebiz.framework.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisherAware;

public interface EventPublisher extends ApplicationEventPublisherAware {
	
	void publish(ApplicationEvent event);
	
}
