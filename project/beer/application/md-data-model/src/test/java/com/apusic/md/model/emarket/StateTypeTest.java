package com.apusic.md.model.emarket;

import junit.framework.Assert;

import org.junit.Test;

public class StateTypeTest {

	@Test
	public void toStateType(){
		String stateType = "ENABLED";
		StateType type = StateType.toStateType(stateType);
		Assert.assertTrue(type == StateType.ENABLED);

		stateType = "DISABLED";

		type = StateType.toStateType(stateType);
		Assert.assertTrue(type == StateType.DISABLED);
	}
}
