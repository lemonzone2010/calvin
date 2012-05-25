package com.xia.jobs;



public interface WorkItem {
	/**
	 * 将返回的结果解析成自己的属性
	 * @param responseOneData
	 * @return
	 */
	WorkItem convert(Object responseOneData);
	/**
	 * 将自己转换成可以使用的request参数
	 * @return 可以提交的request参数
	 */
	Object reverse2Params(Query query);
	
	
}
