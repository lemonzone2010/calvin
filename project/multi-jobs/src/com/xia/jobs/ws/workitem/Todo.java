package com.xia.jobs.ws.workitem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.xia.jobs.Query;
import com.xia.jobs.WorkItem;
import com.xia.jobs.ws.autogen.workbench.GetPendingTaskRequest;
import com.xia.jobs.ws.autogen.workbench.GetPendingTaskResponse;
import com.xia.jobs.ws.autogen.workbench.PendingTask;
import com.xia.jobs.ws.autogen.workbench.WorkbenchService;
import com.xia.jobs.ws.autogen.workbench.WorkbenchServicePortType;

public class Todo extends WorkItemImpl{

	public WorkItem convert(Object responseOneData) {
		// TODO Auto-generated method stub
		System.out.println("TODO:"+responseOneData);
		PendingTask pendingTask=(PendingTask) responseOneData;
		setSummary(pendingTask.getTaskName());
		//setCategory(Category.PEND_TASK);
		//setOwerId(userId);
		//setSpIdentity(this.spIdentity);
		//
		//setCreateTime(toDate(pendingTask.getCreateTime()));
		setCreator(pendingTask.getCreatorName());
		//setExpireTime(toDate(pendingTask.getExpireTime()));
		setPriority(pendingTask.getPriority());
		setWorkId(pendingTask.getTaskId());
		setSummary(pendingTask.getTaskName());
		setType(pendingTask.getTypeName());
		//setUrl(this.urlPrefix + pendingTask.getUrl());
		//data.add(pt);
		//setOwerId(userId);
		return this;
	}

	public GetPendingTaskRequest reverse2Params(Query query) {
		GetPendingTaskRequest request=new GetPendingTaskRequest();
		request.setIdcardNo(query.getIdCardNo());
		request.setUserId(query.getUserId());
		request.setPageSize(100);
		request.setPageNo(1);
		return request;
	}

	public List<WorkItem> request(URL wsdlURL, Object request) {
	GetPendingTaskRequest wsRequest = (GetPendingTaskRequest) request;
		
		List<WorkItem> items = new ArrayList<WorkItem>();
		try {
			WorkbenchServicePortType port = new WorkbenchService(wsdlURL).getWorkbenchServicePort();
			GetPendingTaskResponse wsResponse = port.getPendingTask(wsRequest);
			int result = wsResponse.getResult();
			if (result == 0) {
				for (PendingTask myAttention : wsResponse.getTask()) {
					items.add(convert(myAttention));
				}
			} else if (result == 1) {
				logger.error("Retrieve MyAttention Items from :" + wsdlURL + " fails, no such a user: " + "");
			} else if (result == 2) {
				logger.error("Retrieve MyAttention Items from :" + wsdlURL + " fails, user :" + ""
						+ " do not have permission.");
			} else if (result == 3) {
				logger.error("Retrieve MyAttention Items from :" + wsdlURL + " fails, system error.");
			}
		} catch (Exception e) {
			logger.error("Retrieve MyAttention Items from :" + wsdlURL + " fails : ", e);
		}
		return items;
	}

}
