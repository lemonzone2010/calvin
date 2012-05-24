package com.xia.jobs;

import java.util.List;

/**
 * 返回结果定义
 * @author xiayong
 *
 * @param <E>
 */
public interface Response<E extends WorkItem>  {

	public String getRequestUrl();

	public void setRequestUrl(String requestUrl);

	/**
	 * 当前数据记录的所有者的ID，即这条信息是给谁的。
	 * 
	 */
	public String getOwerId();

	/**
	 * 工作项的类型
	 * 
	 * @return
	 */
	public String getCategory();
	
	public List<E> getData();


	public void validate();

	public int getSize();

	public void setSize(int size);

	public ResponseStatus getStatus();

	/**
	 * 查询时间，毫秒
	 * 
	 * @return
	 */
	public int getQueryTime();

	public void setQueryTime(int queryTime);
}