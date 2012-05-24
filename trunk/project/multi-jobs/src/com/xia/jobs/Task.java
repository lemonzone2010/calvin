package com.xia.jobs;

/**
 * 每个子任务
 * @author xiayong
 *
 * @param <E>
 */
public interface Task<E extends WorkItem> extends Runnable {
	public Response<E> getResult();
}
