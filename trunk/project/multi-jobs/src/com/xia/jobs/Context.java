package com.xia.jobs;

import java.util.List;

public interface Context<E extends WorkItem> {

	/**
	 * �õ���ֳ�ÿ�������������ķ�����ṩ��
	 * @return
	 */
	public List<ServiceProvider<E>> getServiceProviders();

}
