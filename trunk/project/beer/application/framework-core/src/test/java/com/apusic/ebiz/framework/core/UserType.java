package com.apusic.ebiz.framework.core;

public enum UserType {
	INTERNAL("INT"), EXTERNAL("EXT");
	private String type;

	private UserType(String type){
		this.type = type;
	}

}
