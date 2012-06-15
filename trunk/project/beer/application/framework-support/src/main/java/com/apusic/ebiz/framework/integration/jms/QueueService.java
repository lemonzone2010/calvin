package com.apusic.ebiz.framework.integration.jms;

public interface QueueService {
	void send(String queueName, Object message);
}
