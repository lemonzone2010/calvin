package com.apusic.ebiz.framework.core.mail;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

@Service("ebiz_MailService")
public class MailServiceImpl implements MailService {

	@Autowired
	@Qualifier("ebiz_MailSender")
	private JavaMailSender mailSender;

	@Autowired
	@Qualifier("ebiz_TaskExecutor")
	private TaskExecutor taskExecutor;// 注入Spring封装的异步执行器

	public void send(MailBuilder mailBuilder, Map context) throws MailException {
		if (mailBuilder.isSimpleMailBuilder()) {
			mailSender
					.send((SimpleMailMessage) ((AbstractMailBuilder) mailBuilder)
							.build(context));
		} else {
			MimeMailMessage mimeMailMessage = (MimeMailMessage) ((AbstractMailBuilder) mailBuilder)
					.build(context);
			mailSender.send(mimeMailMessage.getMimeMessage());
		}
	}

	public void sendAsynchronously(final MailBuilder mailBuilder,
			final Map context) throws MailException {
		taskExecutor.execute(new Runnable() {
			public void run() {
				send(mailBuilder, context);
			}
		});
	}
}
