package com.apusic.ebiz.framework.core;

import org.springframework.core.NestedRuntimeException;

public class FrameworkRuntimeException extends NestedRuntimeException {

	public FrameworkRuntimeException(String msg, Object... keys) {
		super(msg);
	}

	public FrameworkRuntimeException(String msg, Exception e) {
		super(msg, e);
	}
}
