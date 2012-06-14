package com.apusic.ebiz.framework.core.workflow.osworklfow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.apusic.ebiz.framework.core.workflow.Workflow;
import com.apusic.ebiz.framework.core.workflow.WorkflowContext;
import com.apusic.ebiz.framework.core.workflow.WorkflowException;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.StepDescriptor;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.spi.Step;

public class WorkflowImpl implements Workflow{

	private com.opensymphony.workflow.Workflow workflow;
	private WorkflowDescriptor wfd;
	private long id;


    WorkflowImpl(com.opensymphony.workflow.Workflow workflow, long id){
		this.workflow = workflow;
		this.id = id;
	}

	public List<String> getAvaiableActions(WorkflowContext context) {
		int[] actionIds = workflow.getAvailableActions(id, context.getInnerContext());
		List<String> actionNames = new ArrayList<String>();
		for (int id : actionIds){
			actionNames.add(this.getActionName(id));
		}
		return actionNames;
	}

	public long getId() {
		return this.id;
	}

	public void performAction(String actionName, WorkflowContext context) throws WorkflowException {
		WorkflowDescriptor wfd = getWorkflowDescriptor();
		List<String> availableActions = this.getAvaiableActions(context);
		if (!availableActions.contains(actionName)){
			throw new WorkflowException("The action name" + actionName + " is invalid");
		}

		int[] actionIds = workflow.getAvailableActions(id, context.getInnerContext());
		try {
			for (int actionId : actionIds){
				ActionDescriptor ad = wfd.getAction(actionId);
				if (StringUtils.equals(ad.getName(), actionName)){
					workflow.doAction(id, actionId, context.getInnerContext());
					break;
				}
			}
		} catch (InvalidInputException e) {
			throw new WorkflowException(e);
		} catch (com.opensymphony.workflow.WorkflowException e) {
			throw new WorkflowException(e);
		}
	}

	public String getCurrentState() {
		List<Step> currentSteps = workflow.getCurrentSteps(id);
		return this.getStepState(currentSteps.get(0).getStepId());
	}

	private WorkflowDescriptor getWorkflowDescriptor(){
		String wfName = workflow.getWorkflowName(id);
		if (wfd != null){
			return wfd;
		}
		WorkflowDescriptor wfd = workflow.getWorkflowDescriptor(wfName);
		return wfd;
	}

	private String getStepState(int stepId){
		String wfName = workflow.getWorkflowName(id);
		WorkflowDescriptor wfd = workflow.getWorkflowDescriptor(wfName);
		StepDescriptor sd = wfd.getStep(stepId);
		return sd.getName();
	}

	private String getActionName(int actionId){
		WorkflowDescriptor wfd = getWorkflowDescriptor();
		return wfd.getAction(actionId).getName();
	}

	public List<com.apusic.ebiz.framework.core.workflow.Step> getHistorySteps() {
		List<Step> steps = (List<Step>) workflow.getHistorySteps(id);
		List<com.apusic.ebiz.framework.core.workflow.Step> historySteps =
			new ArrayList<com.apusic.ebiz.framework.core.workflow.Step>();

		for(Step step : steps){
			com.apusic.ebiz.framework.core.workflow.Step historyStep
				= new com.apusic.ebiz.framework.core.workflow.Step();
			historyStep.setOperateTime(step.getFinishDate());
			historyStep.setOperator(step.getCaller());
			historyStep.setState(getStepState(step.getStepId()));
			String operation = getActionName(step.getActionId());
			historyStep.setOperation(operation);
			historySteps.add(historyStep);
		}
		return historySteps;
	}
}
