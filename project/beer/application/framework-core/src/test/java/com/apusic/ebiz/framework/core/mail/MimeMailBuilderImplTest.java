package com.apusic.ebiz.framework.core.mail;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.ebiz.framework.core.DummyUser;
import com.apusic.ebiz.framework.core.mail.AbstractMailBuilder;
import com.apusic.ebiz.framework.core.mail.MailBuilder;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:apusic-ebiz-framework-core.xml"})
@Ignore
public class MimeMailBuilderImplTest {

	@Autowired
	@Qualifier("md_MimeMailBuilder")
	private MailBuilder mailBuilder;

	@Test
	public void build(){
		mailBuilder.setFrom("chenmingdong@apusic.net");
		mailBuilder.setTo("chenmingdong@apusic.net");
		mailBuilder.setBcc("chenmingdong@apusic.net", "huangtanqian@apusic.net");
		mailBuilder.setCc("chenmingdong@apusic.net", "huangtanqian@apusic.net");
		mailBuilder.setSentDate(new Date());
		mailBuilder.setSubject("hell world!");
		mailBuilder.setTemplateLocation("email-template.vm");
		mailBuilder.setReplyTo("huangtanqin@apusic.net");

		DummyUser user = new DummyUser();
		user.setUserName("Alex Chen");
		user.setEmailAddress("chenmingdong@apusic.net");
		Map context = new HashMap();
        context.put("user", user);
        MailMessage mailMessage = ((AbstractMailBuilder)mailBuilder).build(context);

        //Hard to test the mime mail content.
        assertNotNull(mailMessage);
	}
}
