package com.apusic.ebiz.framework.integration.jms;

import junit.framework.Assert;

public class SayHello {
	public void say(String name){
		System.out.println("hello " + name);
		//Assert.assertEquals("The purpose of life is a life of purpose.", name);
	}

	public void say(long id){
		System.out.println("hello id " + id);
	}

}
