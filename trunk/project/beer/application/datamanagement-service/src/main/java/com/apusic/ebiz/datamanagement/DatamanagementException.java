package com.apusic.ebiz.datamanagement;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;

public class DatamanagementException extends FrameworkRuntimeException{

	private Object[] keys;

	public DatamanagementException(String msg, Object... keys) {
		super(msg);
		this.keys = keys;
	}

	public DatamanagementException(String msg,Exception e) {
		super(msg, e);
	}
}
