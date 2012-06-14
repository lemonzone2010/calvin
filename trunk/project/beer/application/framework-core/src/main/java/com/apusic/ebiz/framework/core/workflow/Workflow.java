package com.apusic.ebiz.framework.core.workflow;

import java.util.List;

public interface Workflow {
	List<String> getAvaiableActions(WorkflowContext context);
	void performAction(String actionName, WorkflowContext context) throws WorkflowException;
	String getCurrentState();
	long getId();
	List<Step> getHistorySteps();
}
