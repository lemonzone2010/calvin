package com.apusic.ebiz.framework.web.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.apusic.ebiz.framework.core.mail.MailBuilder;
import com.apusic.ebiz.framework.core.mail.MailService;

/**
 * Notify the exception to specific system adminstrator
 *
 * @author Alex Chen
 *
 */
@Component("handler_ExceptionNotificationHandler")
public class ExceptionNotificationHandler implements ExceptionHandler{

	@Autowired
	@Qualifier("ebiz_MailService")
	private MailService mailService;

	@Autowired
    @Qualifier("ebiz_ExceptionSendMailBuild")
	private MailBuilder mailBuilder;


	public void handle(HttpServletRequest request,
			HttpServletResponse response, Exception exception) {
		Map exceptionInfo = new HashMap();
		exceptionInfo.put("exceptionMsg", exception.getMessage());
		mailService.send(mailBuilder, exceptionInfo);
		throw (RuntimeException) exception;
	}

	public void setMailBuilder(MailBuilder mailBuilder) {
		this.mailBuilder = mailBuilder;
	}
}
