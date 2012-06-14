package com.apusic.ebiz.framework.core.workflow;


public interface WorkflowService {
	Workflow createWorkflow(WorkflowContext workflowContext);
	Workflow getWorkflowById(long id);

}
