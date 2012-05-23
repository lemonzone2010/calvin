package com.xia.jobs.ws;

import com.xia.jobs.Context.ServiceProviderConfig;
import com.xia.jobs.Query;
import com.xia.jobs.Response;
import com.xia.jobs.ServiceProvider;

public class WsServiceProvider implements ServiceProvider{
	private ServiceProviderConfig providerConfig;
	
	
	public WsServiceProvider(ServiceProviderConfig providerConfig) {
		this.providerConfig = providerConfig;
	}

	public Response getResult(Query query) {// maybe is a list,return
		System.out.println("Trying get result from:"+providerConfig.getWsdlUrl());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finished get result from:"+providerConfig.getWsdlUrl());
		return null;
	}
}
