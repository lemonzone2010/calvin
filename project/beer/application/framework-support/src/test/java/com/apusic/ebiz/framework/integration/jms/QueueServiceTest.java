package com.apusic.ebiz.framework.integration.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:apusic-ebiz-framework-integration-jms.xml",
		"classpath:apusic-ebiz-framework-integration-jms-test.xml" })
public class QueueServiceTest {

	@Autowired
	private QueueService queueService;

	@Test
	public void send(){
		String words = "The purpose of life is a life of purpose.";
		queueService.send("testQueue", words);
		queueService.send("testTopic", "This is a topic");
	}
}
