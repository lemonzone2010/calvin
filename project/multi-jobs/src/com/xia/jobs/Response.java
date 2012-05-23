package com.xia.jobs;

import java.util.List;

public interface Response  {

	public String getRequestUrl();

	public void setRequestUrl(String requestUrl);

	/**
	 * ��ǰ���ݼ�¼�������ߵ�ID����������Ϣ�Ǹ�˭�ġ�
	 * 
	 */
	public String getOwerId();

	/**
	 * ����������ͣ�����֮һ��{@link com.apusic.eac.umc.Category}
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
	 * ��ѯʱ�䣬����
	 * 
	 * @return
	 */
	public int getQueryTime();

	public void setQueryTime(int queryTime);
}