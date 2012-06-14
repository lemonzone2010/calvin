package com.apusic.ebiz.framework.core.mail;

import java.util.Date;

public interface MailBuilder {

	void setFrom(String from);

	void setReplyTo(String replyTo);

	void setTo(String... to);

	void setCc(String... cc);

	void setBcc(String... bcc);

	void setSentDate(Date paramDate);

	void setSubject(String paramString);

	void setTemplateLocation(String templateLocation);

	boolean isSimpleMailBuilder();
}
