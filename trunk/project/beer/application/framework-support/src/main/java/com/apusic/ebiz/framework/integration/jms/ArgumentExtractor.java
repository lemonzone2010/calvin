package com.apusic.ebiz.framework.integration.jms;

import javax.jms.JMSException;
import javax.jms.Message;

public interface ArgumentExtractor {
	Object[] extract(Message message) throws JMSException ;
}
