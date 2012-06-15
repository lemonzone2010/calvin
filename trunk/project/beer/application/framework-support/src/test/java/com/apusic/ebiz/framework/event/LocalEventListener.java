package com.apusic.ebiz.framework.event;

import junit.framework.Assert;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component("ebiz_testLocalEventListener")
public class LocalEventListener implements ApplicationListener{

	public void onApplicationEvent(ApplicationEvent event) {
		
		if (event instanceof LocalEvent){
			System.out.println("An Local Event occurs");
			Assert.assertEquals("refresh", ((String) event.getSource()));
		}
	}

}
