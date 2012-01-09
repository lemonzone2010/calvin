package com.xia.quartz.spring;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

public class MyBean {
	private NopService nopService;

	public void hello() {
		System.out.println("MyBean.Hello" + nopService);
	}

	@Resource
	public void setNopService(NopService nopService) {
		this.nopService = nopService;
	}

	public NopService getNopService() {
		return nopService;
	}
}