package com.xia.jobs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface TaskFactory {
	static ExecutorService executor = Executors.newFixedThreadPool(10);
	
	void exceute();

	Response getResult();

}