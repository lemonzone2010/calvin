package com.xia.jobs.ws;

import com.xia.jobs.Query;
import com.xia.jobs.Response;
import com.xia.jobs.ResponseStatus;
import com.xia.jobs.ServiceProvider;
import com.xia.jobs.WorkItem;
import com.xia.jobs.ws.WsContext.ServiceProviderConfig;

public class WsServiceProvider implements ServiceProvider<WorkItem>{
	private ServiceProviderConfig providerConfig;
	
	
	public WsServiceProvider(ServiceProviderConfig providerConfig) {
		this.providerConfig = providerConfig;
	}

	public Response<WorkItem> getResult(Query query) {// maybe is a list,return
		WorkResponse response=new WorkResponse();
		response.setStatus(ResponseStatus.getSuccessStatus());
		System.out.println("Trying get result from:"+providerConfig.getWsdlUrl());
		try {
			Thread.sleep(1000);
			
			//goto wsdl,get result
			//for each:list
			// newList.add workitem.convert(each)
			//response.add newList
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(ResponseStatus.getFailStatus(e.getMessage()));
		}
		System.out.println("finished get result from:"+providerConfig.getWsdlUrl());
		return response;
	}
}
