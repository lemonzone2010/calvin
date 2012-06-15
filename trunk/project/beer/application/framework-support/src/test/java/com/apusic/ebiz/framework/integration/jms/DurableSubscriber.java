package com.apusic.ebiz.framework.integration.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:apusic-ebiz-framework-integration-jms.xml",
		"classpath:apusic-ebiz-framework-integration-jms-test.xml" })
public class DurableSubscriber {
	
	@Autowired
	@Qualifier("ebiz_SingleConnectionFactory")	
	private ConnectionFactory connectionFactory;

	@Test
	public void send() throws JMSException{
		Connection conn = connectionFactory.createConnection();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("testTopic");
		session.createDurableSubscriber((Topic) topic, "durablename");
	}
	
	
	
	

}
