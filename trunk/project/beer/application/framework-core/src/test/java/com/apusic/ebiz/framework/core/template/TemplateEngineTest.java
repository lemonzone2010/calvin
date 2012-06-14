package com.apusic.ebiz.framework.core.template;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.ebiz.framework.core.DummyUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml","classpath:apusic-ebiz-framework-core-user.xml" })
public class TemplateEngineTest {

	@Autowired
	@Qualifier("ebiz_TemplateEngine")
	private TemplateEngine templateEngine;
	
	@Autowired
	@Qualifier("ebiz_TemplateEngine2")
	private TemplateEngine templateEngine2;
	
	@Test
	public void mergeUsringOriginalTemplate() throws ResourceNotFoundException,
			ParseErrorException, MethodInvocationException, IOException {

		Context context = new VelocityContext();
		context.put("userName", "Alex Chen");
		Writer writer = new StringWriter();

		// Using original template
		templateEngine.acceptTemplate("test-template.vm").merge(
				context, writer);
		String mergedString = writer.toString();
		assertEquals(
				"Alex Chen, welcome to use Template Engine! Hello, ${user.userName}!",
				mergedString);
	}

	@Test
	public void mergeUsingEnchanedTemplate(){
		Map contextMap = new HashMap();
		DummyUser user = new DummyUser();
		user.setUserName("Alex Chen");
		contextMap.put("userName", "Alex Chen");
		contextMap.put("user", user);
		String mergedString = templateEngine
				.acceptTemplate("test-template.vm")
				.mergeIntoString(contextMap);
		assertEquals(
				"Alex Chen, welcome to use Template Engine! Hello, Alex Chen!",
				mergedString);
	}

	@Test
	public void mergeIntoFile() throws IOException {
		Map contextMap = new HashMap();
		DummyUser user = new DummyUser();
		user.setUserName("Alex Chen");
		contextMap.put("userName", "Alex Chen");
		contextMap.put("user", user);

		File htmlFile = new File("test.html");
		if (htmlFile.exists()) {
			htmlFile.createNewFile();
		}
		templateEngine.acceptTemplate("test-template.vm").mergeIntoFile(
				contextMap, htmlFile);
		
		File htmlFile2 = new File("test2.html");
		if (htmlFile2.exists()) {
			htmlFile2.createNewFile();
		}
		templateEngine2.acceptTemplate("md_test_templateResourceBean").mergeIntoFile(contextMap, htmlFile2);
		
	}
	
	

}
