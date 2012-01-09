package com.xia.quartz.spring;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class DomainClass {

	@Resource
	private transient NopService nopService;

	public void test() {
		nopService.test();
	}

	public void setNopService(NopService nopService) {
		this.nopService = nopService;
	}

}
