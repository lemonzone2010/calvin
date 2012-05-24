package com.xia.jobs;

import java.util.Map;

public interface Query {
	//public enum Category{};
	int getStart();

	int getLimit();

	String getCategory();

	String getUserId();

	String getIdCardNo();

	/**
	 * ������request.getParameterMap()��Ķ���
	 * 
	 * @param requestParams
	 */
	void parse(final Map<String,String[]> requestParams);
}
