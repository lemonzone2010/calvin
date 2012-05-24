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

	public WorkItem covert(Object responseOneData) {
		if(null==responseParseClass) {
			throw new IllegalAccessError(name()+"û�����ÿ�ִ�е�class");
		}
		try {
			WorkItem newInstance = responseParseClass.newInstance();
			return newInstance.convert(responseOneData);
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
