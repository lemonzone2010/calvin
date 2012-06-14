package com.apusic.ebiz.framework.core.workflow.osworklfow;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springmodules.workflow.osworkflow.OsWorkflowContextHolder;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;
import com.apusic.ebiz.framework.core.workflow.WorkflowContext;
import com.apusic.ebiz.framework.core.workflow.WorkflowService;
import com.opensymphony.workflow.InvalidActionException;
import com.opensymphony.workflow.InvalidEntryStateException;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.InvalidRoleException;
import com.opensymphony.workflow.TypeResolver;
import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.config.Configuration;

public class OSWorkflowServiceImpl implements WorkflowService{
	private String workflowName;
 
	@Autowired
	@Qualifier("transactionInterceptor")
	private MethodInterceptor interceptor;

	@Autowired
	@Qualifier("osworkflowConfiguration")
	private Configuration config;

	@Autowired
	@Qualifier("workflowTypeResolver")
	private TypeResolver typeResolver;

	public com.apusic.ebiz.framework.core.workflow.Workflow createWorkflow(WorkflowContext context){
		com.apusic.ebiz.framework.core.workflow.Workflow workflow;
		try {

			Workflow wf =  this.createWorkflow();
			long instanceId = wf.initialize(workflowName, 0, context.getInnerContext());
			workflow = new WorkflowImpl(wf, instanceId);
		} catch (InvalidActionException e) {
			throw new FrameworkRuntimeException("The user invokes a invalid action", e);
		} catch (InvalidRoleException e) {
			throw new FrameworkRuntimeException("The user doesn't has role to invoke", e);
		} catch (InvalidInputException e) {
			throw new FrameworkRuntimeException("The user's input is not correct", e);
		} catch (InvalidEntryStateException e) {
			throw new FrameworkRuntimeException("The entry state is not correct", e);
		} catch (WorkflowException e) {
			throw new FrameworkRuntimeException("The entry state is not correct", e);
		}
		return workflow;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public com.apusic.ebiz.framework.core.workflow.Workflow getWorkflowById(
			long id) {
		OsWorkflowContextHolder.getWorkflowContext().setInstanceId(id);
		Workflow wf = this.createWorkflow();
		return new WorkflowImpl(wf, id);
	}

	private Workflow createWorkflow(){
		String caller = OsWorkflowContextHolder.getWorkflowContext().getCaller();
		//Using dynamic proxy to allow the methods in BasicWorkflow are all transactional
		BasicWorkflow basicWf = new BasicWorkflow(caller);
		basicWf.setConfiguration(config);
		basicWf.setResolver(typeResolver);
		ProxyFactory proxy = new ProxyFactory(basicWf);
		proxy.addInterface(Workflow.class);
		proxy.addAdvice(interceptor);
		Workflow wf =  (Workflow) proxy.getProxy();
		return wf;
	}
}
