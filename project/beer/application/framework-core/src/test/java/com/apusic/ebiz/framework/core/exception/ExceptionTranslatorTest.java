package com.apusic.ebiz.framework.core.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.ebiz.framework.core.exception.BaseBussinessException;
import com.apusic.ebiz.framework.core.exception.ExceptionTranslator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml", "classpath*:apusic-ebiz-framework-core-user.xml"})
public class ExceptionTranslatorTest {

	@Autowired
	private ExceptionTranslator translator;

	@Test
	public void translate(){
		BaseBussinessException yourBusinessException = new BaseBussinessException(
				"SmartOrg-001", "exception description");
		String translated = translator.translate(yourBusinessException);
		assertEquals("The user cannot be found!", translated);

		BaseBussinessException anotherBusinessException = new BaseBussinessException(
				"SmartOrg-002", "exception description");
		translated = translator.translate(anotherBusinessException, new Object[]{"Alex", 1});

		assertEquals("Your input was wrong, user name Alex and age 1.", translated);

		BaseBussinessException certificateNull = new BaseBussinessException(
				"BindAuthenticator.emptyPassword", "exception description");

		translated = translator.translate(certificateNull);

		assertEquals("坏的凭证", translated);

	}



}
