package com.xia.jobs;

import java.util.List;

public interface Response  {

	public String getRequestUrl();

	public void setRequestUrl(String requestUrl);

	/**
	 * 当前数据记录的所有者的ID，即这条信息是给谁的。
	 * 
	 */
	public String getOwerId();

	/**
	 * 工作项的类型，其中之一：{@link com.apusic.eac.umc.Category}
	 * 
	 * @return
	 */
	public String getCategory();
	
	public <E> List<E> getData();


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