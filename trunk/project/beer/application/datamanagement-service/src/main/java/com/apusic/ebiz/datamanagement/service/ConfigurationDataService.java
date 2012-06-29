package com.apusic.ebiz.datamanagement.service;

import java.util.List;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.datamanagement.ConfigurationData;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public interface ConfigurationDataService extends ConfigService{
	@TriggersRemove(cacheName="ConfigurationDataService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
	void addConfigurationData(ConfigurationData data);

	@TriggersRemove(cacheName="ConfigurationDataService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
	void deleteConfigurationData(int id);

	@TriggersRemove(cacheName="ConfigurationDataService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
	void updateConfigurationData(ConfigurationData data);

	ConfigurationData getConfigurationData(int id);
	List<ConfigurationData> getConfigurationDatasByCategoryId(int id);
	Page<ConfigurationData> getConfigurationDatasByCategoryIdForPage(int id) ;
	/**
	 * 获取指定key的值
	 * @param key
	 * @return
	 */
	@Cacheable(cacheName = "ConfigurationDataService")
	String getValue(String key);

	@TriggersRemove(cacheName="ConfigurationDataService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
	void deleteDatas(int[] ids);
	
	ConfigurationData getDataByKey(String string);
	
	List<ConfigurationData> getConfigurationDatasByCategoryName(String name);
}