package com.xia.search.solr.schema.event;

import org.hibernate.event.service.spi.DuplicationStrategy;
import org.hibernate.event.service.spi.DuplicationStrategy.Action;

public class DuplicationStrategyImpl implements DuplicationStrategy {
	private final Class checkClass;

	public DuplicationStrategyImpl(Class checkClass) {
		this.checkClass = checkClass;
	}

	@Override
	public boolean areMatch(Object listener, Object original) {
		// not isAssignableFrom since the user could subclass
		return checkClass == original.getClass() && checkClass == listener.getClass();
	}

	@Override
	public Action getAction() {
		return Action.KEEP_ORIGINAL;
	}
}