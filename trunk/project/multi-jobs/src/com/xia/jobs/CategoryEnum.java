package com.xia.jobs;

import com.xia.jobs.workitem.Todo;

public enum CategoryEnum {
	TODO(	"todo",
			"代办",
			Todo.class),
	DONE(	"done",
			"已办",
			null),
	TOREAD(	"toread",
			"代阅",
			null),
	READED(	"readed",
			"已阅",
			null);

	private String name;
	//private String desc;

	private CategoryEnum(String name, String desc, Class<? extends WorkItem> responseParseClass) {
		this.name = name;
		//this.desc = desc;
		this.responseParseClass = responseParseClass;
	}

	private Class<? extends WorkItem> responseParseClass;

	public CategoryEnum getByName(String name) {
		
	}

	public WorkItem covert(Object responseOneData) {
		if(null==responseParseClass) {
			throw new IllegalAccessError(name()+"没有配置可执行的class");
		}
		try {
			WorkItem newInstance = responseParseClass.newInstance();
			return newInstance.covert(responseOneData);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
