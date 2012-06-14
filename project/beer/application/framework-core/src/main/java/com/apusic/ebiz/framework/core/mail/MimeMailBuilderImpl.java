package com.apusic.ebiz.framework.core.mail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;

public class MimeMailBuilderImpl extends AbstractMailBuilder{

	private static final Log logger = LogFactory.getLog(MimeMailBuilderImpl.class);

	@Autowired
	@Qualifier("ebiz_MailSender")
	private JavaMailSenderImpl sender;

	private List<Attachment> attachments;

	public MailMessage build(Map context) {
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
			if (this.getBcc() != null) {
				helper.setBcc(this.getBcc());
			}
			if (this.getCc() != null) {
				helper.setCc(this.getCc());
			}
			if (this.getReplyTo() != null){
				helper.setReplyTo(this.getReplyTo());
			}
			helper.setFrom(this.getFrom());
			helper.setFrom(this.getFrom());
			helper.setSentDate(new Date());
			helper.setSubject(this.getSubject());
			helper.setText(this.getTemplateWrapper().mergeIntoString(context), true);
			helper.setTo(this.getTo());

			if (this.attachments == null || this.attachments.size() == 0) {
				return new MimeMailMessage(message);
			}

			for (Attachment attachment : this.attachments) {
				if (attachment.isInline()) {
					helper.addInline(attachment.getAttachmentFilename(),
							attachment.getAttachementFile());
				} else {
					helper.addAttachment(attachment.getAttachmentFilename(),
							attachment.getAttachementFile());
				}
			}

		} catch (MessagingException e) {
			logger.error("Error occurs when creating Mime email", e);
			throw new FrameworkRuntimeException(
					"Error occurs when creating Mime email", e);
		}
		return new MimeMailMessage(message);
	}

	public boolean isSimpleMailBuilder() {
		return false;
	}

	public void setAttachements(List<Attachment> attachements) {
		this.attachments = attachements;
	}

	public void addAttachment(Attachment... attachements){
		if (this.attachments != null && this.attachments.size()>1){
			this.attachments.addAll(Arrays.asList(attachements));
		}else{
			this.attachments = Arrays.asList(attachements);
		}
	}
}
