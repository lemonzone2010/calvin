package com.apusic.ebiz.framework.integration.jms;

import javax.jms.ConnectionFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class QueueMessageListenerContainer extends
		DefaultMessageListenerContainer implements ApplicationContextAware {

	private ArgumentExtractor argumentExtractor;

	private String invokeMethod;

	private String queueName;

	private ApplicationContext applicationContext;

	private MessageListenerSpringBeanAdaptor messageListenerSpringBeanAdaptor;

	@Autowired
	@Qualifier("ebiz_SingleConnectionFactory")
	public void setConnectionFactory(ConnectionFactory cf) {
		super.setConnectionFactory(cf);
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
		super.setDestinationName(queueName);
	}

	public void setArgumentExtractor(ArgumentExtractor argumentExtractor) {
		this.messageListenerSpringBeanAdaptor.setArgumentExtractor(argumentExtractor);
		this.argumentExtractor = argumentExtractor;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setInvokeMethod(String invokeMethod) {
		this.messageListenerSpringBeanAdaptor.setInvokeMethod(invokeMethod);
		this.invokeMethod = invokeMethod;
	}

	@Autowired
	@Qualifier("ebiz_MessageListener")
	public void setMessageListener(Object messageListener) {
		this.messageListenerSpringBeanAdaptor = (MessageListenerSpringBeanAdaptor) messageListener;
		super.setMessageListener(messageListener);
	}
}
