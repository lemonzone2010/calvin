package com.apusic.ebiz.framework.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * Log the exception to the specific places
 * @author Alex Chen
 *
 */
@Component("handler_ExceptionLoggingHandler")
public class ExceptionLoggingHandler implements ExceptionHandler{

	private Log logger = LogFactory.getLog(ExceptionLoggingHandler.class);

	public void handle(HttpServletRequest request,
			HttpServletResponse response, Exception exception) {
		logger.error(exception.getMessage());
	}
}
