package com.xia.jobs.ws;

import java.util.Map;

import com.xia.jobs.CategoryEnum;
import com.xia.jobs.Query;

public class WsQuery implements Query {
	private CategoryEnum categoryEnum;

	public int getStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	public CategoryEnum getCategory() {
		return categoryEnum;
	}

	public String getUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIdCardNo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void parse(Map<String, String[]> requestParams) {
		// TODO Auto-generated method stub

	}

}
