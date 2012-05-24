package com.xia.jobs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ��������(Query)+�����Ļ����������ּ���������񣬸�����ɺ󷵻ؽ��
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
	// ������ִ����
	public final static ExecutorService executor = Executors.newFixedThreadPool(10);

	/**
	 * ��������õ��ĸ���������
	 */
	TaskFactory<E> exceute(Context<E> context, Query query);

	/**
	 * �õ���������������ɺ�Ľ��
	 * 
	 * @return
	 */
	Response<E> getResult();
	
}