package com.xia.quartz.jaas;

import java.security.Principal;

public class MyPrincipal implements Principal {
	String name;
	
	public MyPrincipal(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
