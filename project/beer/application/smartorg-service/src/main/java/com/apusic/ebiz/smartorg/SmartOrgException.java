package com.apusic.ebiz.smartorg;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;

public class SmartOrgException extends FrameworkRuntimeException {

	public SmartOrgException(String msg){
		super(msg);
	}

	public SmartOrgException(String msg,Exception e){
		super(msg,e);
	}
}
