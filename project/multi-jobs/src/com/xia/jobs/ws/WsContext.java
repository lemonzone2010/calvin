package com.xia.jobs.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xia.jobs.Context;
import com.xia.jobs.Query;
import com.xia.jobs.ServiceProvider;
import com.xia.jobs.WorkItem;
import com.xia.jobs.ws.WsContext.ServiceProviderConfig.ProvideType;
import com.xia.jobs.ws.workitem.CategoryEnum;

public class WsContext implements Context<WorkItem> {
	private final Logger logger = Logger.getLogger(getClass());
	List<ServiceProvider<WorkItem>> serviceProviderList = new ArrayList<ServiceProvider<WorkItem>>();

	public WsContext() {
		readConfigFile();
	}

	private void readConfigFile() {
		Properties pro = new Properties();
		try {
			pro.load(this.getClass().getClassLoader().getResourceAsStream("ws.properties"));
		} catch (IOException e) {
			throw new RuntimeException("读配置文件出错啦：" + e);
		}
		Set<Entry<Object, Object>> entrySet = pro.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			String key = entry.getKey().toString().trim();
			String type = StringUtils.substringBefore(key, ".");
			String id = StringUtils.substringBetween(key, ".", ".url");
			if (key.endsWith(".url")) {
				String wsdlUrl = entry.getValue().toString().trim();
				serviceProviderList.add(new WsServiceProvider(new ServiceProviderConfig(wsdlUrl, id, ProvideType
						.getByName(type))));
			}
		}
		logger.info("Readed config file ws.properties,get urls:" + serviceProviderList.size());
	}

	public List<ServiceProvider<WorkItem>> getServiceProviders(Query query) {
		List<ServiceProvider<WorkItem>> ret = new ArrayList<ServiceProvider<WorkItem>>();
		for (ServiceProvider<WorkItem> item : serviceProviderList) {
			// query.getcategory的一些类型都使用work的
			if (getFromCategory(query).equals(item.getServiceProviderConfig().getType())) {
				ret.add(item);
			}
		}
		return ret;
	}

	private ProvideType getFromCategory(Query query) {
		CategoryEnum category = CategoryEnum.getByName(query.getCategory());
		switch (category) {
		case ATTENTION:
			return ProvideType.ATTENTION;

		default:
			return ProvideType.WORK;
		}
	}

	public static class ServiceProviderConfig {
		public enum ProvideType {
			ATTENTION("attention"),
			WORK("work");
			private String name;

			private ProvideType(String name) {
				this.name = name;
			}

			public static ProvideType getByName(String name) {
				for (ProvideType type : values()) {
					if (type.getName().equals(name)) {
						return type;
					}
				}
				return null;
			}

			public String getName() {
				return name;
			}
		};

		private String wsdlUrl;
		private String id;
		private ProvideType type;

		public ServiceProviderConfig(String wsdlUrl, String id, ProvideType type) {
			this.wsdlUrl = wsdlUrl;
			this.id = id;
			this.type = type;
		}

		public String getWsdlUrl() {
			return wsdlUrl;
		}

		public String getId() {
			return id;
		}

		public ProvideType getType() {
			return type;
		}
	}
}