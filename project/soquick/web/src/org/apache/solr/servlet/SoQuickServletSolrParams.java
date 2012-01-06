package org.apache.solr.servlet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.params.MultiMapSolrParams;

import com.apusic.ebiz.soquick.admin.SoQuickShardConfig;

/**
 * 对现有的参数分析，且转发到别的服务器处理
 * @author xiayong
 *
 */
public class SoQuickServletSolrParams extends MultiMapSolrParams {

	public SoQuickServletSolrParams(ServletRequest req) {
		super(getMap(req.getParameterMap(),req));
	}

	static Map getMap(Map<?, ?> a,ServletRequest req) {
		Map ret = new HashMap();
		for (Map.Entry map : a.entrySet()) {
			ret.put(map.getKey(), map.getValue());
		}
		String path = ((HttpServletRequest) req).getServletPath();
		addSharding4Select(ret, path);
		return Collections.unmodifiableMap(ret);
	}

	private static void addSharding4Select(Map ret, String path) {
		if (SoQuickShardConfig.isNeedShard()) {
			if (StringUtils.equals(path, "/select")) {
				String readMachines = com.apusic.ebiz.soquick.admin.SoQuickShardConfig
						.getReadMachines();
				if (StringUtils.isNotBlank(readMachines)) {
					ret.put("shards", new String[] { readMachines });
				}
			}
		}
	}

	public String get(String name) {
		String[] arr = map.get(name);
		if (arr == null)
			return null;
		String s = arr[0];
		if (s.length() == 0)
			return null; // screen out blank parameters
		return s;
	}
}
