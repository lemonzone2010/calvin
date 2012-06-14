package com.apusic.ebiz.framework.core.workflow;

import static org.junit.Assert.*;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springmodules.workflow.osworkflow.OsWorkflowContextHolder;

import com.apusic.ebiz.framework.core.exception.BaseBussinessException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-osworkflow.xml",
		"classpath:apusic-ebiz-framework-core-user.xml"})
public class WorkflowServiceTest {

	@Autowired
	@Qualifier("ebiz_testOrderBookWorkflowService")
	private WorkflowService workflowService;

	private Workflow workflow;

	@Test
	public void createWorkflow(){
		WorkflowContext context = new WorkflowContext();
		OsWorkflowContextHolder.clearWorkflowContext();
		OsWorkflowContextHolder.getWorkflowContext().setCaller("achen");
		workflow = workflowService.createWorkflow(context);
	}

	@Test
	public void testGetAvailableActions() throws BaseBussinessException {
		Workflow workflow = workflowService.getWorkflowById(1l);
		WorkflowContext context = new WorkflowContext();
		OsWorkflowContextHolder.clearWorkflowContext();
		OsWorkflowContextHolder.getWorkflowContext().setCaller("achen");
		List<String> availableActionNames = workflow.getAvaiableActions(context);
		assertTrue(availableActionNames.size() > 0);
	}

	@Test
	public void testPerformAction() throws BaseBussinessException, WorkflowException {
		Workflow workflow = workflowService.getWorkflowById(1);
		WorkflowContext context = new WorkflowContext();
		OsWorkflowContextHolder.clearWorkflowContext();
		OsWorkflowContextHolder.getWorkflowContext().setCaller("achen");
		workflow.performAction("confirm", context);
		String currentState = workflow.getCurrentState();
		assertEquals("Placed", currentState);
		List history = workflow.getHistorySteps();
		assertTrue(history.size() > 0);
	}

}
