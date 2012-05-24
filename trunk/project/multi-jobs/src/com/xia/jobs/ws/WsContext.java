package com.xia.jobs.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.xia.jobs.Context;
import com.xia.jobs.ServiceProvider;
import com.xia.jobs.WorkItem;

public class WsContext implements Context<WorkItem> {
	private final Logger logger = Logger.getLogger(getClass());
	List<ServiceProvider<WorkItem>> serviceProviderList = new ArrayList<ServiceProvider<WorkItem>>();

	public WsContext() {
		readConfigFile();
	}

	private void readConfigFile() {
		Properties pro = new Properties();
		try {
			pro.load(this.getClass().getClassLoader().getResourceAsStream("ws.properties"));
		} catch (IOException e) {
			throw new RuntimeException("∂¡≈‰÷√Œƒº˛≥ˆ¥Ì¿≤£∫"+e);
		}
		Set<Entry<Object, Object>> entrySet = pro.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			String key = entry.getKey().toString().trim();
			if (key.endsWith(".url")) {
				String wsdlUrl = entry.getValue().toString().trim();
				serviceProviderList.add(new WsServiceProvider(new ServiceProviderConfig(wsdlUrl)));
			}
		}
		logger.info("Readed config file ws.properties,get urls:"+serviceProviderList.size());
	}

	public List<ServiceProvider<WorkItem>> getServiceProviders() {
		return serviceProviderList;
	}

	public static class ServiceProviderConfig {
		private String wsdlUrl;

		public ServiceProviderConfig(String wsdlUrl) {
			this.wsdlUrl = wsdlUrl;
		}

		public String getWsdlUrl() {
			return wsdlUrl;
		}
	}
}