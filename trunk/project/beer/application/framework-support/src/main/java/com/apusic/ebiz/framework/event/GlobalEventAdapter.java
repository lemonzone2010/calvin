package com.apusic.ebiz.framework.event;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.apusic.ebiz.framework.core.context.ApplicationContextHolder;


/**
 * Adapts the JMS Message to a Global Event
 * 
 * @author achen
 */
public class GlobalEventAdapter implements MessageListener, InitializingBean {
	Log logger = LogFactory.getLog(GlobalEventAdapter.class);
	@Autowired
	@Qualifier("ebiz_SingleConnectionFactory")
	private ConnectionFactory connectionFactory;

	public void onMessage(Message message) {
		ObjectMessage om = (ObjectMessage) message;
		try {
			Object sourceObject = om.getObject();
			GlobalEvent gEvent = (GlobalEvent) sourceObject;
			if (gEvent.getSource()==null) {
				return;
			}
			ApplicationContextHolder.getApplicationContext()
					.publishEvent(gEvent);
			  
		} catch (JMSException e) {
			throw new GlobalEventException(
					"Error occurs when receiving global event", e);
		}catch(Exception e){  
			logger.error("接收消息异常", e);
		}  
	}

	public void afterPropertiesSet() throws Exception {
		//create a durable subscriber
		Connection conn = connectionFactory.createConnection();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("eventTopic");
		session.createConsumer((Topic) topic, "eventDurable");
		//session.createDurableSubscriber((Topic) topic, "eventDurable");
	}
}
