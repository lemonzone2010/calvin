package com.xia.jobs;

/**
 * ÿ��������
 * @author xiayong
 *
 * @param <E>
 */
public interface Task<E extends WorkItem> extends Runnable {
	public Response<E> getResult();
}
