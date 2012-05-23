package com.xia.jobs;

import java.util.ArrayList;
import java.util.List;

import com.xia.jobs.ws.WsServiceProvider;

public class Context {
	
	public List<ServiceProvider> getServiceProviders() {
		List<ServiceProvider> ret = new ArrayList<ServiceProvider>();
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
