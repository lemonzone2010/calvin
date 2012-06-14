package com.apusic.ebiz.framework.core.workflow.condition;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;


@Component("userIsSubmitterCondition")
public class UserIsSubmitterCondition implements Condition{

	public boolean passesCondition(Map arg0, Map arg1, PropertySet arg2)
			throws WorkflowException {
		// TODO Auto-generated method stub
		return true;
	}

}
