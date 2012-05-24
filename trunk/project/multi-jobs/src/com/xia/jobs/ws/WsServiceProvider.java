package com.xia.jobs.ws;

import com.xia.jobs.Query;
import com.xia.jobs.Response;
import com.xia.jobs.ResponseStatus;
import com.xia.jobs.ServiceProvider;
import com.xia.jobs.WorkItem;
import com.xia.jobs.ws.WsContext.ServiceProviderConfig;
import com.xia.jobs.ws.workitem.CategoryEnum;

public class WsServiceProvider implements ServiceProvider<WorkItem>{
	private ServiceProviderConfig providerConfig;
	
	
	public WsServiceProvider(ServiceProviderConfig providerConfig) {
		this.providerConfig = providerConfig;
	}

	public Response<WorkItem> getResult(Query query) {// maybe is a list,return
		WsQuery wsQuery=(WsQuery) query;
		WorkResponse response=new WorkResponse();
		response.setStatus(ResponseStatus.getSuccessStatus());
		response.setRequestUrl(providerConfig.getWsdlUrl());
		System.out.println("Trying get result from:"+providerConfig.getWsdlUrl());
		try {
			CategoryEnum category = wsQuery.getCategoryEnum();
			Object submitParams = category.reverse2Params();
			System.out.println(submitParams);
			//wsdl.submit submtParams
			Thread.sleep(1000);
			
			//goto wsdl,get result
			//for each:list
			// newList.add category.convert(each)
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
