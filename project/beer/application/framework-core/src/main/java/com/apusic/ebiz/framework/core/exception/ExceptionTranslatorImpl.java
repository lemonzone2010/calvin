package com.apusic.ebiz.framework.core.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;



@Service("ebiz_ExceptionTranslator")
public class ExceptionTranslatorImpl implements ExceptionTranslator, MessageSourceAware{

	@Autowired
	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String translate(BaseBussinessException yourBusinessException,
			Object... args) {
		String code = yourBusinessException.getCode();
		return messageSource.getMessage(code, args, null);
	}

}
