package com.xia.jobs.ws.workitem;

import com.xia.jobs.WorkItem;

public enum CategoryEnum {
	TODO(	"todo",
			"����",
			Todo.class),
	DONE(	"done",
			"�Ѱ�",
			null),
	TOREAD(	"toread",
			"����",
			null),
	READED(	"readed",
			"����",
			null);

	private String name;

	private CategoryEnum(String name, String desc, Class<? extends WorkItem> responseParseClass) {
		this.name = name;
		this.responseParseClass = responseParseClass;
	}

	private Class<? extends WorkItem> responseParseClass;

	/**
	 * @param name ͨ�����Ƶõ�category,��todo,done
	 * @return
	 */
	public static CategoryEnum getByName(String name) {
		CategoryEnum[] values = values();
		for (CategoryEnum categoryEnum : values) {
			if(categoryEnum.name.equals(name)) {
				return categoryEnum;
			}
		}
		return null;
	}

	public WorkItem covertFromResponse(Object responseOneData) {
		WorkItem newWorkItem = getNewWorkItem();
		if(null==newWorkItem) {
			throw new IllegalAccessError(name()+"û�����ÿ�ִ�е�class");
		}
		return newWorkItem.convert(responseOneData);
	}
	
	public Object reverse2Params() {
		WorkItem newWorkItem = getNewWorkItem();
		if(null==newWorkItem) {
			throw new IllegalAccessError(name()+"û�����ÿ�ִ�е�class");
		}
		return newWorkItem.reverse2Params();
	}
	
	public WorkItem getNewWorkItem() {
		if(null==responseParseClass) {
			throw new IllegalAccessError(name()+"û�����ÿ�ִ�е�class");
		}
		try {
			return responseParseClass.newInstance();
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
