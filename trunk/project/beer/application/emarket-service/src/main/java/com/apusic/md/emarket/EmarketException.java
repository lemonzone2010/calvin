package com.apusic.md.emarket;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;

public class EmarketException extends FrameworkRuntimeException {

	public EmarketException(String msg) {
		super(msg);
	}

	public EmarketException(String msg, Exception e){
		super(msg, e);
	}
}