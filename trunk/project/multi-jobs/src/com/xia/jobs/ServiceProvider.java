package com.xia.jobs;

import com.xia.jobs.ws.WsContext.ServiceProviderConfig;

/**
 * 服务提供者
 * @author xiayong
 *
 * @param <E>
 */
public interface ServiceProvider<E extends WorkItem> {
	public Response<E> getResult(Query query);
	public ServiceProviderConfig getServiceProviderConfig();
}
