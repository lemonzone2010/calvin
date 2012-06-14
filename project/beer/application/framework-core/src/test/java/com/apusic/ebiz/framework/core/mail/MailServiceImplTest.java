package com.apusic.ebiz.framework.core.mail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.ebiz.framework.core.DummyUser;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:apusic-ebiz-framework-core.xml", "classpath:apusic-ebiz-framework-core-user.xml"})
public class MailServiceImplTest {

	@Autowired
	private MailService mailService;

	@Autowired
	@Qualifier("md_SimpleMailBuilderTest")
	private MailBuilder simpleMailBuilder;
	
	@Autowired
	@Qualifier("md_SimpleMailBuilderTest2")
	private MailBuilder simpleMailBuilder2;

	@Autowired
	@Qualifier("md_MimeMailBuilderTest")
	private MailBuilder mimeMailBuilder;

	@Autowired
	@Qualifier("md_InlineMimeMailBuilderTest")
	private MailBuilder inlineMimeMailBuilder;

	@Test
	public void sendSimpleMail(){
		Map userMap = new HashMap();
		DummyUser user = new DummyUser();
		user.setUserName("Alex Chen");
		userMap.put("userName", "Alex Chen");
		userMap.put("user", user);
		mailService.send(simpleMailBuilder, userMap);
		mailService.send(simpleMailBuilder2, userMap);
	}

	@Test
	public void sendSimpleMailAsynchronously(){
		Map userMap = new HashMap();
		DummyUser user = new DummyUser();
		user.setUserName("Alex Chen");
		userMap.put("userName", "Alex Chen");
		userMap.put("user", user);
		mailService.sendAsynchronously(simpleMailBuilder, userMap);
	}

	@Test
	public void sendMimeMail(){
		Map userMap = new HashMap();
		DummyUser user = new DummyUser();
		user.setUserName("Alex Chen");
		userMap.put("userName", "Alex Chen");
		userMap.put("user", user);
		mailService.send(mimeMailBuilder, userMap);
	}

	@Test
	public void sendMimeMailAsynchronously(){
		Map userMap = new HashMap();
		DummyUser user = new DummyUser();
		user.setUserName("Alex Chen");
		userMap.put("userName", "Alex Chen");
		userMap.put("user", user);
		mailService.sendAsynchronously(mimeMailBuilder, userMap);
	}


	@Test
	public void sendInlineMimeMail(){
		Map userMap = new HashMap();
		DummyUser user = new DummyUser();
		user.setUserName("Alex Chen");
		userMap.put("userName", "Alex Chen");
		userMap.put("user", user);
		mailService.send(inlineMimeMailBuilder, userMap);
	}

	@Test
	public void sendInlineMimeMailAsynchronously(){
		Map userMap = new HashMap();
		DummyUser user = new DummyUser();
		user.setUserName("Alex Chen");
		userMap.put("userName", "Alex Chen");
		userMap.put("user", user);
		mailService.sendAsynchronously(inlineMimeMailBuilder, userMap);
	}

}
