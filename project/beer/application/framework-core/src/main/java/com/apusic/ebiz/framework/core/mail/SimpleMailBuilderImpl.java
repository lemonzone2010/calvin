package com.apusic.ebiz.framework.core.mail;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;

public class SimpleMailBuilderImpl extends AbstractMailBuilder{

	public MailMessage build(Map context) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		String text = this.getTemplateWrapper().mergeIntoString(context);
		BeanUtils.copyProperties(this, simpleMailMessage);
		simpleMailMessage.setText(text);
		return simpleMailMessage;
	}

	public boolean isSimpleMailBuilder() {
		return true;
	}
}
