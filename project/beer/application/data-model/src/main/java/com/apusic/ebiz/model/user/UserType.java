package com.apusic.ebiz.model.user;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum UserType {

	SUPERUSER("SUP"), INTERNAL("INT"), EXTERNAL("EXT");

	private static final Map<String,UserType> lookup
    	= new HashMap<String,UserType>();

	static {
	    for(UserType s : EnumSet.allOf(UserType.class)){
	    	lookup.put(s.getValue(), s);
	    }

	}

	private String type;

	private UserType(String type){
		this.type = type;
	}

	public String getValue(){
		return this.type;
	}
}
