package com.xia.jobs.ws.workitem;

import java.net.URL;
import java.util.List;

import com.xia.jobs.Query;
import com.xia.jobs.WorkItem;
import com.xia.jobs.ws.attention.MyAttentionItem;

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
			null),
	ATTENTION(	"attention",
				"�ҵĹ�ע",
				MyAttentionItem.class), ;

	private String name;

	private CategoryEnum(String name, String desc, Class<? extends WorkItem> responseParseClass) {
		this.name = name;
		this.responseParseClass = responseParseClass;
	}

	private Class<? extends WorkItem> responseParseClass;

	/**
	 * @param name
	 *            ͨ�����Ƶõ�category,��todo,done
	 * @return
	 */
	public static CategoryEnum getByName(String name) {
		CategoryEnum[] values = values();
		for (CategoryEnum categoryEnum : values) {
			if (categoryEnum.name.equals(name)) {
				return categoryEnum;
			}
		}
		return null;
	}

	public Object reverse2Params(Query query) {
		return getNewWorkItem().reverse2Params(query);
	}

	public WorkItem getNewWorkItem() {
		if (null == responseParseClass) {
			throw new IllegalAccessError(name() + "û�����ÿ�ִ�е�class");
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
		throw new IllegalAccessError(name() + "û�����ÿ�ִ�е�class");
	}
	
	public List<WorkItem> processRequest(URL wsdlURL,Object request){
		return ((WsWorkItem)getNewWorkItem()).request(wsdlURL, request);
	}

}
