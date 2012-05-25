package com.xia.jobs.ws;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.xia.jobs.Query;
import com.xia.jobs.ws.workitem.CategoryEnum;

public class WsQuery implements Query {
	//private final Logger logger=Logger.getLogger(getClass());
	private CategoryEnum categoryEnum;
	private String category;
	private String userId;
	private String idCardNo;
	private Integer start;
	private Integer limit;
	

	public void parse(Map<String, String[]> requestParams) {
		// TODO Auto-generated method stub
		//TODO category from here
		category=get(requestParams.get("category"));
		userId = get(requestParams.get("owerId"));
		idCardNo = get(requestParams.get("idCardNo"));
		start = getInt(requestParams.get("start"));
		limit = getInt(requestParams.get("limit"));
		categoryEnum=CategoryEnum.getByName(category);
	}
	private String get(String[] input) {
		if(null==input)return "";
		return StringUtils.trim(input[0]);
	}
	private Integer getInt(String[] input) {
		if(null==input)return null;
		return Integer.valueOf(StringUtils.trim(input[0]));
	}

	public CategoryEnum getCategoryEnum() {
		return categoryEnum;
	}

	public String getCategory() {
		return category;
	}
	public String getUserId() {
		return userId;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public Integer getStart() {
		return start;
	}
	public Integer getLimit() {
		return limit;
	}

}
