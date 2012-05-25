package com.xia.jobs.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import com.xia.jobs.Query;
import com.xia.jobs.WorkItem;
import com.xia.jobs.ws.WsContext.ServiceProviderConfig;
import com.xia.jobs.ws.workitem.CategoryEnum;

public class ServiceProviderHelper {
	private final static Logger logger = Logger.getLogger(ServiceProviderHelper.class);

	public static List<WorkItem> processRequest(Query query, ServiceProviderConfig providerConfig) {
		WsQuery wsQuery = (WsQuery) query;
		CategoryEnum category = wsQuery.getCategoryEnum();
		Object request = category.reverse2Params(query);
		//Service service
		try {
			URL wsdlURL = new URL(providerConfig.getWsdlUrl());
			return category.processRequest(wsdlURL, request);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	
}
