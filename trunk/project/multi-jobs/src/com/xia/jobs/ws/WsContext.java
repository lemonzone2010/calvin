package com.xia.jobs.ws;

import java.util.ArrayList;
import java.util.List;

import com.xia.jobs.Context;
import com.xia.jobs.ServiceProvider;
import com.xia.jobs.WorkItem;

public class WsContext  implements Context<WorkItem>{
	
	public List<ServiceProvider<WorkItem>> getServiceProviders() {
		List<ServiceProvider<WorkItem>> ret = new ArrayList<ServiceProvider<WorkItem>>();
		ret.add(new WsServiceProvider(new ServiceProviderConfig("http://test1.do?wsdl")));
		ret.add(new WsServiceProvider(new ServiceProviderConfig("http://test2.do?wsdl")));
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