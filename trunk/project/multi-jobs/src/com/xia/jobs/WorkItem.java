package com.xia.jobs;

import java.net.URL;
import java.util.List;


public interface WorkItem {
	/**
	 * 将返回的结果解析成自己的属性
	 * @param responseOneData
	 * @return
	 */
	WorkItem convert(Object responseOneData);
	/**
	 * 将自己转换成可以使用的参数
	 * @return
	 */
	Object reverse2Params(Query query);
	
	/**
	 * 处理请求的过程
	 * @param wsdlURL
	 * @param request
	 * @return
	 */
	List<WorkItem> request(URL wsdlURL,Object request);
}
