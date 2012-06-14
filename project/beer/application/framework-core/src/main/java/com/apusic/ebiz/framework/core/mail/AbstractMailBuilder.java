package com.apusic.ebiz.framework.core.mail;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailMessage;

import com.apusic.ebiz.framework.core.template.TemplateEngine;
import com.apusic.ebiz.framework.core.template.TemplateWrapper;

abstract class AbstractMailBuilder implements MailBuilder{

	public String[] getBcc() {
		return bcc;
	}

	public String[] getCc() {
		return cc;
	}

	public Date getSentDate() {
		return sentDate;
	}

	@Autowired
	@Qualifier("ebiz_TemplateEngine")
	private TemplateEngine templateEngine;

	@Autowired
	@Qualifier("ebiz_TemplateEngine2")
	private TemplateEngine templateEngine2;
	
	private String[] bcc;
	private String[] cc;
	private String from;
	private String replyTo;
	private Date sentDate;
	private String subject;
	private String[] to;
	private String templateLocation;
	private String templateResourceBeanName;
	

	protected TemplateWrapper getTemplateWrapper(){
		if(StringUtils.isNotBlank(templateLocation)){
			return (TemplateWrapper) templateEngine.acceptTemplate(templateLocation);
		}
		return (TemplateWrapper) templateEngine2.acceptTemplate(templateResourceBeanName);
	}

	public String getTemplateLocation() {
		return templateLocation;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}

	
	public String getTemplateResourceBeanName() {
		return templateResourceBeanName;
	}

	public void setTemplateResourceBeanName(String templateResourceBeanName) {
		this.templateResourceBeanName = templateResourceBeanName;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String... to){
		this.to = to;
	}


	public String getFrom() {
		return from;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setReplyTo(String replyTo){
		this.replyTo = replyTo;
	}

	public void setSentDate(Date date){
		this.sentDate = date;
	}

	public void setSubject(String subject){
		this.subject = subject;
	}

	public void setBcc(String... bcc) {
		this.bcc = bcc;
	}

	public void setCc(String... cc) {
		this.cc = cc;
	}

	abstract MailMessage build(Map context);
}
