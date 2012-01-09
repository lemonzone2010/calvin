package com.xia.quartz.spring;

import org.springframework.stereotype.Component;

@Component
public class NopService {
	
	public void test() {
		System.out.println("NopBean.test");
	}
}
