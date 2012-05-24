package com.xia.jobs;

import java.util.List;

public interface Context<E extends WorkItem> {

	/**
	 * 得到拆分成每个子任务的任务的服务的提供者
	 * @return
	 */
	public List<ServiceProvider<E>> getServiceProviders();

}
