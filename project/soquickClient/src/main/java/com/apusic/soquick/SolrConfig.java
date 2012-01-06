package com.apusic.soquick;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class SolrConfig {
	enum SolrType {
		ReadOnly, WriteOnly
	}

	static Map<String, SolrType> list = new HashMap<String, SolrType>();
	static{
		//localhost:7001/web,localhost:8081/solr
		list.put("localhost:7001/web", SolrType.WriteOnly);
		list.put("localhost:8081/solr", SolrType.ReadOnly);
	}
	private static SolrType getType(String url){
		if(url.startsWith("http://")){
			url=StringUtils.substringAfter("http://", url);
		}
		return list.get(url);
	}
	public static boolean isWriteable(String url){
		return SolrType.WriteOnly.equals(getType(url));
	}
	public static boolean isReadable(String url){
		return SolrType.ReadOnly.equals(getType(url));
	}
	public static String getReadMachines() {
		String ret="";
		for (Entry<String, SolrType> map : list.entrySet()) {
			if(isReadable(map.getKey())) {
				ret+=map.getKey()+",";
			}
		}
		if(StringUtils.isNotBlank(ret)) {
			ret=StringUtils.substringBeforeLast(ret, ",");
		}
		return ret;
	}
	public static void main(String[] args) {
		System.out.println(isReadable("localhost:8081/solr"));
		System.out.println(isReadable("http://localhost:8081/solr"));
		System.out.println(getReadMachines());
	}
}
