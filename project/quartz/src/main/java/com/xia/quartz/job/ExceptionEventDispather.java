package com.xia.quartz.job;

import java.util.Observable;

import com.xia.quartz.model.JobEntity;

public class ExceptionEventDispather extends Observable {
	private static ExceptionEventDispather exceptionDispather = new ExceptionEventDispather();

	private ExceptionEventDispather() {
		super();
	}

	public static ExceptionEventDispather getInstance() {
		return exceptionDispather;
	}

	public void notify(JobEntity jobEntity) {
		setChanged();
		super.notifyObservers(jobEntity);
	}

	@Override
	@Deprecated
	public void notifyObservers() {
		super.notifyObservers();
	}

	@Override
	@Deprecated
	public void notifyObservers(Object arg) {
		super.notifyObservers(arg);
	}

}
