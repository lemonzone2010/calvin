package com.xia.job.ws;

import com.xia.jobs.Context;
import com.xia.jobs.Query;
import com.xia.jobs.Response;
import com.xia.jobs.TaskFactory;
import com.xia.jobs.WorkItem;
import com.xia.jobs.ws.WsContext;
import com.xia.jobs.ws.WsQuery;
import com.xia.jobs.ws.WsTaskFactory;

public class TestTask {
	public static void main(String[] args) {
		Context<WorkItem> context = new WsContext();//�õ�������������
		
		Query query = new WsQuery();//��ѯ�ṹ��
		query.parse(null);//��request���������
		
		TaskFactory<WorkItem> taskFactory = new WsTaskFactory();//�õ����񹤳�
		
		taskFactory.exceute(context, query);//ִ������

		Response<WorkItem> result = taskFactory.getResult();//�õ�������
		
		
		System.out.println(result+","+result.getQueryTime()+",size:"+result.getSize());
		for (WorkItem item : result.getData()) {
			System.out.println(item);
		}
		
		TaskFactory.executor.shutdown();
	}
}
