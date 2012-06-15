package com.apusic.ebiz.framework.integration.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import com.apusic.ebiz.framework.integration.jms.ArgumentExtractor;

public class StringArgumentExtractor implements ArgumentExtractor{

	private static MessageConverter messageConverter = new SimpleMessageConverter();

	public Object[] extract(Message message) throws JMSException {
		TextMessage textMessage = (TextMessage) message;
		return new Object[]{(String) textMessage.getText()};
	}

}
