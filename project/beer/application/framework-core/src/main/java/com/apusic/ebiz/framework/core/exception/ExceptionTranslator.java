package com.apusic.ebiz.framework.core.exception;


public interface ExceptionTranslator {

	String translate(BaseBussinessException baseBusinessException, Object... args);

}
