package com.xia.jobs.ws;

import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import com.xia.jobs.Query;
import com.xia.jobs.Response;
import com.xia.jobs.ResponseStatus;
import com.xia.jobs.ServiceProvider;
import com.xia.jobs.WorkItem;
import com.xia.jobs.ws.WsContext.ServiceProviderConfig;
import com.xia.jobs.ws.workitem.CategoryEnum;

public class WsServiceProvider implements ServiceProvider<WorkItem> {
	private final Logger logger = Logger.getLogger(getClass());
	private ServiceProviderConfig providerConfig;

	public WsServiceProvider(ServiceProviderConfig providerConfig) {
		this.providerConfig = providerConfig;
	}

	public Response<WorkItem> getResult(Query query) {// maybe is a list,return
		WorkResponse response = new WorkResponse();
		response.setStatus(ResponseStatus.getSuccessStatus());
		response.setRequestUrl(providerConfig.getWsdlUrl());
		logger.info("Trying get result from:" + providerConfig.getWsdlUrl());

		try {
			WsQuery wsQuery = (WsQuery) query;
			CategoryEnum category = wsQuery.getCategoryEnum();
			Object request = category.reverse2Params(query);
			URL wsdlURL = new URL(providerConfig.getWsdlUrl());
			
			List<WorkItem> items = category.processRequest(wsdlURL, request);
			response.addData(items);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setStatus(ResponseStatus.getFailStatus(e.getMessage()));
		}
		logger.info("finished get result from:" + providerConfig.getWsdlUrl());
		return response;
	}

	public ServiceProviderConfig getServiceProviderConfig() {
		return providerConfig;
	}
}
