package com.xia.jobs.ws;

import java.util.Map;

import org.apache.log4j.Logger;

import com.xia.jobs.Query;
import com.xia.jobs.ws.workitem.CategoryEnum;

public class WsQuery implements Query {
	private final Logger logger=Logger.getLogger(getClass());
	private CategoryEnum categoryEnum;
	private String category;
	public int getStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getUserId() {
		// TODO Auto-generated method stub
		return "00";
	}

	public String getIdCardNo() {
		// TODO Auto-generated method stub
		return "111";
	}

	public void parse(Map<String, String[]> requestParams) {
		// TODO Auto-generated method stub
		//TODO category from here
		category="todo";
		categoryEnum=CategoryEnum.getByName(category);

	}

	public CategoryEnum getCategoryEnum() {
		return categoryEnum;
	}

	public String getCategory() {
		return category;
	}

}
