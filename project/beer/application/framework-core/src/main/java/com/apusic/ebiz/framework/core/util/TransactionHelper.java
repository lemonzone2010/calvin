package com.apusic.ebiz.framework.core.util;

import java.util.concurrent.Callable;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class TransactionHelper {
	public static <T> T doInTransaction(TransactionTemplate transactionTemplate, final Callable<T> call) {
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		return transactionTemplate.execute(new TransactionCallback<T>(){

			@Override
			public T doInTransaction(TransactionStatus status) {
				try {
					return call.call();
				} catch (Exception e) {
					throw new RuntimeException("事务执行出错:"+e.getMessage(), e);
				}
			}
			
		});
	}
}
