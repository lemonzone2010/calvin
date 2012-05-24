package com.xia.jobs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 依据输入(Query)+上下文环境，将其拆分几多个子任务，各子完成后返回结果
 * <pre>
 * TaskFactory taskFactory = new WsTaskFactory(context, query);
	taskFactory.exceute();
	Object result = taskFactory.getResult();
 * </pre>
 * 
 * @author xiayong
 * 
 */
public interface TaskFactory<E extends WorkItem> {
	// 子任务执行器
	public final static ExecutorService executor = Executors.newFixedThreadPool(10);

	/**
	 * 处理将输入得到的各个子任务
	 */
	TaskFactory<E> exceute(Context<E> context, Query query);

	/**
	 * 得到各个子任务处理完成后的结果
	 * 
	 * @return
	 */
	Response<E> getResult();
	
}