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
	 * 解析从request.getParameterMap()里的东西
	 * 
	 * @param requestParams
	 */
	void parse(final Map<String,String[]> requestParams);
}
