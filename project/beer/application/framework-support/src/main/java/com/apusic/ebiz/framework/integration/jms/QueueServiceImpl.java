package com.apusic.ebiz.framework.integration.jms;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service("ebiz_QueueService")
public class QueueServiceImpl implements QueueService {
	private JmsTemplate jmsTemplate;

	@Autowired
	@Qualifier("ebiz_SingleConnectionFactory")
	public void setConnectionFactory(ConnectionFactory cf) {
		this.jmsTemplate = new JmsTemplate(cf);
	}

	public void send(String queueJndiName, Object message) {
		jmsTemplate.convertAndSend(queueJndiName, message);
	}
}
