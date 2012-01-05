package com.xia.quartz.job;

import java.util.Observable;
import java.util.Observer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xia.quartz.model.JobEntity;

public abstract class JobExceptionListener  implements Observer{
	protected final static Log logger = LogFactory.getLog(JobExceptionListener.class);
	public abstract void update(JobEntity jobEntity);
	@Override
	public final void update(Observable o, Object arg) {
			update((JobEntity) arg);	
	}
}
