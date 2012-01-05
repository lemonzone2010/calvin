package com.xia.quartz.service;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Component
public class TrascationHelper {

	@Transactional(rollbackFor = Exception.class)
	public <T> T execute(Callable<T> callable) throws Exception {
		return callable.call();
	}
	
}
