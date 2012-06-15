package com.apusic.ebiz.framework.cache;

import org.springframework.stereotype.Service;

@Service("test_CacheObject")
public class CacheObject implements Cachable{

	private int invocationTimes = 0 ;
	
	public String getValue(String key) {
		invocationTimes ++;
		return "NoSQL datastores like Bigtable " +
				"and CouchDB are moving from margin to center in the Web 2.0 rea.";
	}

	public int getInvocationTimes() {
		return invocationTimes;
	}

	public String getValue(int key) {
		invocationTimes ++;
		return "NoSQL datastores like Bigtable " +
		"and CouchDB are moving from margin to center in the Web 2.0 rea.";
	}

	public void updateValue(int key) {
		System.out.println("NoSQL datastores like Bigtable");
	}

	public void updateValue(String key) {
		System.out.println("NoSQL datastores like Bigtable");
	}
}
