package com.xia.jobs.ws.workitem;

import java.net.URL;
import java.util.List;

import com.xia.jobs.WorkItem;

public interface WsWorkItem extends WorkItem{
	/**
	 * 处理请求的过程
	 * @param wsdlURL
	 * @param request
	 * @return
	 */
	List<WorkItem> request(URL wsdlURL,Object request);
}
