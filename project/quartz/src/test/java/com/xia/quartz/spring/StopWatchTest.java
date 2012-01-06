package com.xia.quartz.spring;

import org.springframework.util.StopWatch;

public class StopWatchTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		StopWatch stopWatch=new StopWatch("test");
		stopWatch.start("Xia");
		Thread.sleep(1000);
		stopWatch.stop();
		stopWatch.start("Xi1a");
		Thread.sleep(1230);
		stopWatch.stop();
		System.out.println(		stopWatch.prettyPrint());

	}

}
