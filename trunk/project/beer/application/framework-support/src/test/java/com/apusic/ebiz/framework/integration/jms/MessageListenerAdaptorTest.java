package com.apusic.ebiz.framework.integration.jms;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:apusic-ebiz-framework-integration-jms.xml",
		"classpath:apusic-ebiz-framework-integration-jms-test.xml" })
public class MessageListenerAdaptorTest {


	private MessageListenerSpringBeanAdaptor messageListenerAdaptor;


	public void onMessage() throws JMSException {
		messageListenerAdaptor
				.setArgumentExtractor(new StringArgumentExtractor());
		TextMessage message = Mockito.mock(TextMessage.class);
		Mockito.when(message.getStringProperty(Mockito.anyString()))
				.thenReturn("good boy");
		messageListenerAdaptor.onMessage(message);

		TextMessage anotherMessage = Mockito.mock(TextMessage.class);
		Mockito.when(message.getLongProperty(Mockito.anyString())).thenReturn(
				123l);
		messageListenerAdaptor.onMessage(message);
	}
}
