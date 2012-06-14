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

        String text = "<html><body><h3>Hi Alex Chen, welcome to the Chipping Sodbury On-the-Hill message boards!</h3><div></div></body></html>";

        //Actuall result
        String actualResult = ((SimpleMailMessage) mailMessage).toString();

        //Exepcted result
        StringBuffer expectedResult = new StringBuffer("SimpleMailMessage: ");
		expectedResult.append("from=").append("chenmingdong@apusic.net").append("; ");
		expectedResult.append("replyTo=").append("huangtanqin@apusic.net").append("; ");
		expectedResult.append("to=").append(StringUtils.arrayToCommaDelimitedString(new String[]{"chenmingdong@apusic.net"})).append("; ");
		expectedResult.append("cc=").append(StringUtils.arrayToCommaDelimitedString(new String[]{"chenmingdong@apusic.net", "huangtanqian@apusic.net"})).append("; ");
		expectedResult.append("bcc=").append(StringUtils.arrayToCommaDelimitedString(new String[]{"chenmingdong@apusic.net", "huangtanqian@apusic.net"})).append("; ");
		expectedResult.append("sentDate=").append(new Date()).append("; ");
		expectedResult.append("subject=").append("hell world!").append("; ");
		expectedResult.append("text=").append(text);

		assertEquals(expectedResult.toString(), actualResult);
	}
}
