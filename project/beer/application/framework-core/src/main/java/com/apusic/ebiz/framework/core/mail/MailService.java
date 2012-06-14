package com.apusic.ebiz.framework.core.mail;

import java.util.Map;

import org.springframework.mail.MailException;

public interface MailService {
	void send(MailBuilder mailBuilder, Map context) throws MailException;
	void sendAsynchronously(MailBuilder mailBuilder, Map context) throws MailException;
}
