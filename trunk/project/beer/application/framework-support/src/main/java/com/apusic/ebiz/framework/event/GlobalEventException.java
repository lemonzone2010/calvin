package com.apusic.ebiz.framework.event;

import org.springframework.core.NestedRuntimeException;

public class GlobalEventException extends NestedRuntimeException{

	public GlobalEventException(String msg) {
		super(msg);
	}

	public GlobalEventException(String msg, Exception e) {
		super(msg, e);
	}
}
