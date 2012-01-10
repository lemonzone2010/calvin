package collection;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ListTest {

	static long start = System.currentTimeMillis();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String>  arrayList = addList(1000000, new ArrayList<String>());
		printUsedTime("ArrayList");
		arrayList = addList(100000, new ArrayList<String>());
		printUsedTime("ArrayList");
		List<String> linkedList = addList(1000000, new LinkedList<String>());
		printUsedTime("LinkedList");
		linkedList = addList(100000, new LinkedList<String>());
		printUsedTime("LinkedList");
		Map map;
		Hashtable t;
	}

	public static List<String> addList(int total, List<String> list) {
		for (int i = 0; i < total; i++) {
			list.add("it is:" + i);
		}
		return list;
	}

	public static void resetStartTime() {
		start = System.currentTimeMillis();
	}

	public static void printUsedTime(String msg) {
		long end = System.currentTimeMillis();
		System.out.println(StringUtils.center(msg+"用时:" + (end - start) + "s", 50, "*"));
		resetStartTime();
	}

}
abstract interface A{
	
}
