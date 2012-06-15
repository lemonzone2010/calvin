package com.apusic.ebiz.framework.core.mail;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import com.apusic.ebiz.framework.core.DummyUser;
import com.apusic.ebiz.framework.core.mail.AbstractMailBuilder;
import com.apusic.ebiz.framework.core.mail.MailBuilder;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:apusic-ebiz-framework-core.xml"})
@Ignore
public class SimpleMailBuilderImplTest {

	@Autowired
	@Qualifier("md_SimpleMailBuilder")
	private MailBuilder mailBuilder;

	@Test
	public void build(){
		mailBuilder.setFrom("hsiayong@163.com");
		mailBuilder.setTo("hsiayong@163.com");
		mailBuilder.setBcc("hsiayong@163.com", "hsia@163.com");
		mailBuilder.setCc("hsiayong@163.com", "hsia@163.com");
		mailBuilder.setSentDate(new Date());
		mailBuilder.setSubject("hell world!");
		mailBuilder.setTemplateLocation("email-template.vm");
		mailBuilder.setReplyTo("hsia@163.com");

		DummyUser user = new DummyUser();
		user.setUserName("Alex Chen");
		user.setEmailAddress("hsiayong@163.com");
		Map context = new HashMap();
        context.put("user", user);
        MailMessage mailMessage = ((AbstractMailBuilder)mailBuilder).build(context);

        String text = "<html><body><h3>Hi Alex Chen, welcome to the Chipping Sodbury On-the-Hill message boards!</h3><div></div></body></html>";

        //Actuall result
        String actualResult = ((SimpleMailMessage) mailMessage).toString();

        //Exepcted result
        StringBuffer expectedResult = new StringBuffer("SimpleMailMessage: ");
		expectedResult.append("from=").append("hsiayong@163.com").append("; ");
		expectedResult.append("replyTo=").append("hsia@163.com").append("; ");
		expectedResult.append("to=").append(StringUtils.arrayToCommaDelimitedString(new String[]{"hsiayong@163.com"})).append("; ");
		expectedResult.append("cc=").append(StringUtils.arrayToCommaDelimitedString(new String[]{"hsiayong@163.com", "hsia@163.com"})).append("; ");
		expectedResult.append("bcc=").append(StringUtils.arrayToCommaDelimitedString(new String[]{"hsiayong@163.com", "hsia@163.com"})).append("; ");
		expectedResult.append("sentDate=").append(new Date()).append("; ");
		expectedResult.append("subject=").append("hell world!").append("; ");
		expectedResult.append("text=").append(text);

		assertEquals(expectedResult.toString(), actualResult);
	}
}
