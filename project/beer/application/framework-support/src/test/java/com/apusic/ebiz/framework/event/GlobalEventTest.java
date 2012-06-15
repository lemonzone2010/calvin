package com.apusic.ebiz.framework.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:apusic-ebiz-framework-integration-jms.xml",
		"classpath:apusic-ebiz-framework-core.xml",		
		"classpath:apusic-ebiz-framework-event.xml","classpath:apusic-ebiz-framework-event-test.xml"})
public class GlobalEventTest {
	
	@Autowired
	private EventPublisher eventPublisher;
	
	@Test
	public void publish() {
		GlobalEvent gEvent = new GlobalEvent("ffff");
		eventPublisher.publish(gEvent);
	}
}
