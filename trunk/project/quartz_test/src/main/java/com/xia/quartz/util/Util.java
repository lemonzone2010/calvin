package com.xia.quartz.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Util {
	/**
	 * 得到堆栈信息
	 * 
	 * @param t
	 * @return xiayong @2011-4-21
	 */
	public static String getStackTrack(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
