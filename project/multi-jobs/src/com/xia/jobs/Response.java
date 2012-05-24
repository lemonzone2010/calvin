package com.xia.jobs;

import java.util.List;

/**
 * ���ؽ������
 * @author xiayong
 *
 * @param <E>
 */
public interface Response<E extends WorkItem>  {

	public String getRequestUrl();

	public void setRequestUrl(String requestUrl);

	/**
	 * ��ǰ���ݼ�¼�������ߵ�ID����������Ϣ�Ǹ�˭�ġ�
	 * 
	 */
	public String getOwerId();

	/**
	 * �����������
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
	 * ��ѯʱ�䣬����
	 * 
	 * @return
	 */
	public int getQueryTime();

	public void setQueryTime(int queryTime);
}