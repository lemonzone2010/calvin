package com.apusic.ebiz.framework.integration.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;

/**
 * 1. Parse the message into specific type as parameters
 * 2. Invoke the specific method on specific object(spring managed bean)
 *
 * @author Alex Chen
 */
public class MessageListenerSpringBeanAdaptor implements MessageListener, ApplicationContextAware{

	private ApplicationContext applicationContext;

	private String invokeMethod;

	private ArgumentExtractor argumentExtractor;

	public void onMessage(Message message) {
		try {
			Object[] arg = argumentExtractor.extract(message);
			MethodInvoker.invokeMethod(applicationContext, invokeMethod, arg);
		} catch (JMSException e) {
			throw new FrameworkRuntimeException(
					"The message cannot be converted", e);
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setInvokeMethod(String invokeMethod) {
		this.invokeMethod = invokeMethod;
	}

	public void setArgumentExtractor(ArgumentExtractor argumentExtractor) {
		this.argumentExtractor = argumentExtractor;
	}
}
