package com.xia.jobs;

/**
 * �����ṩ��
 * @author xiayong
 *
 * @param <E>
 */
public interface ServiceProvider<E extends WorkItem> {
	public Response<E> getResult(Query query);
}
