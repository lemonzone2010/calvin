package org.wltea.analyzer.seg;

public class CharHelper {

	public static void print(char[] charArray , int begin, int length){
		String t="";
		for (int i = 0; i < length; i++) {
			t+=charArray[begin+i];
		}
		System.out.println("正在处理的字为:"+t);
	}
}
