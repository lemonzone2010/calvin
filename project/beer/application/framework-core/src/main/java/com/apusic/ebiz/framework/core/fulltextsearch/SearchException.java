package com.apusic.ebiz.framework.core.fulltextsearch;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;

public class SearchException extends FrameworkRuntimeException{

	public SearchException(String msg, Exception e) {
		super(msg, e);
	}

}
