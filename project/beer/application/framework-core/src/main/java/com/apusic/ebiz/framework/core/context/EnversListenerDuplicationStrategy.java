package com.apusic.ebiz.framework.core.context;

import org.hibernate.event.service.spi.DuplicationStrategy;

/**
 * Event listener duplication strategy for envers
 *
 * @author Steve Ebersole
 */
public class EnversListenerDuplicationStrategy implements DuplicationStrategy {
	public static final EnversListenerDuplicationStrategy INSTANCE = new EnversListenerDuplicationStrategy();

	@Override
	public boolean areMatch(Object listener, Object original) {
		return listener.getClass().equals( original ) ;
	}

	@Override
	public Action getAction() {
		return Action.KEEP_ORIGINAL;
	}
}