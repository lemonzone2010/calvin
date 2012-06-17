package com.apusic.ebiz.framework.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.ebiz.framework.web.FrameworkWebRuntimeException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-web-user.xml","classpath:apusic-ebiz-framework-web.xml"})
public class ExceptionLoggingHandlerTest {

	@Autowired
	private ExceptionLoggingHandler exceptionLogginHandler;

	private HttpServletRequest request;
	private HttpServletResponse response;

	@Before
	public void setup() {
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
	}

	@Test
	public void handle() {
		FrameworkWebRuntimeException exception = new FrameworkWebRuntimeException(
				"This is a runtime exception");
		exceptionLogginHandler.handle(request, response, exception);
	}

}
