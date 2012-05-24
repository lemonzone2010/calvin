package com.xia.jobs;

/**
 * 服务提供者
 * @author xiayong
 *
 * @param <E>
 */
public interface ServiceProvider<E extends WorkItem> {
	public Response<E> getResult(Query query);
}
