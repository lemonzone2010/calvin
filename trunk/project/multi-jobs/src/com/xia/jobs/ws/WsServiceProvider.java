package com.xia.jobs.ws;

import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;

import com.xia.jobs.Query;
import com.xia.jobs.Response;
import com.xia.jobs.ResponseStatus;
import com.xia.jobs.ServiceProvider;
import com.xia.jobs.WorkItem;
import com.xia.jobs.util.Util;
import com.xia.jobs.ws.WsContext.ServiceProviderConfig;
import com.xia.jobs.ws.workitem.CategoryEnum;

public class WsServiceProvider implements ServiceProvider<WorkItem>{
	private final Logger logger=Logger.getLogger(getClass());
	private ServiceProviderConfig providerConfig;
	
	
	public WsServiceProvider(ServiceProviderConfig providerConfig) {
		this.providerConfig = providerConfig;
	}

	public Response<WorkItem> getResult(Query query) {// maybe is a list,return
		WsQuery wsQuery=(WsQuery) query;
		WorkResponse response=new WorkResponse();
		response.setStatus(ResponseStatus.getSuccessStatus());
		response.setRequestUrl(providerConfig.getWsdlUrl());
		logger.info("Trying get result from:"+providerConfig.getWsdlUrl());
		try {
			CategoryEnum category = wsQuery.getCategoryEnum();
			//Object submitParams = category.reverse2Params();
			//logger.info(submitParams);
			URL url = new URL(providerConfig.getWsdlUrl());
			InputStream input = url.openStream();
			String msg = Util.toString(input, "UTF-8");
			logger.info(msg);
			//wsdl.submit submtParams
			//Thread.sleep(1000);
			
			//goto wsdl,get result
			//for each:list
			// newList.add category.convert(each)
			//response.add newList
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(ResponseStatus.getFailStatus(e.getMessage()));
		}
		logger.info("finished get result from:"+providerConfig.getWsdlUrl());
		return response;
	}
}
