package com.xia.jobs;

public interface WorkItem {
	/**
	 * �����صĽ���������Լ�������
	 * @param responseOneData
	 * @return
	 */
	WorkItem convert(Object responseOneData);
	/**
	 * ���Լ�ת���ɿ���ʹ�õĲ���
	 * @return
	 */
	Object reverse2Params();
}
