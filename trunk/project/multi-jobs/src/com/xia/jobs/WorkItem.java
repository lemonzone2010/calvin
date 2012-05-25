package com.xia.jobs;

import java.net.URL;
import java.util.List;


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
	Object reverse2Params(Query query);
	
	/**
	 * ��������Ĺ���
	 * @param wsdlURL
	 * @param request
	 * @return
	 */
	List<WorkItem> request(URL wsdlURL,Object request);
}
