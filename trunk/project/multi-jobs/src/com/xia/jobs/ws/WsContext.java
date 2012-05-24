package com.xia.jobs.ws;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xia.jobs.Context;
import com.xia.jobs.ServiceProvider;
import com.xia.jobs.WorkItem;

public class WsContext  implements Context<WorkItem>{
	private final Logger logger=Logger.getLogger(getClass());
	public List<ServiceProvider<WorkItem>> getServiceProviders() {
		List<ServiceProvider<WorkItem>> ret = new ArrayList<ServiceProvider<WorkItem>>();
		ret.add(new WsServiceProvider(new ServiceProviderConfig("http://www.baidu.com")));
		ret.add(new WsServiceProvider(new ServiceProviderConfig("http://www.google.com")));
		return ret;
	}
	
	
	public static class ServiceProviderConfig{
		private String wsdlUrl;

		public ServiceProviderConfig(String wsdlUrl) {
			this.wsdlUrl = wsdlUrl;
		}

		public String getWsdlUrl() {
			return wsdlUrl;
		}
	}
}