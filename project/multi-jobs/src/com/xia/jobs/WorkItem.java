package com.xia.jobs;



public interface WorkItem {
	/**
	 * �����صĽ���������Լ�������
	 * @param responseOneData
	 * @return
	 */
	WorkItem convert(Object responseOneData);
	/**
	 * ���Լ�ת���ɿ���ʹ�õ�request����
	 * @return �����ύ��request����
	 */
	Object reverse2Params(Query query);
	
	
}
