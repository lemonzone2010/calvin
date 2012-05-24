package com.xia.jobs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {
	public static String toString(InputStream is, String charset) throws IOException {
		if (charset == null) {
			charset = "UTF-8";
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(is, charset));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
}
