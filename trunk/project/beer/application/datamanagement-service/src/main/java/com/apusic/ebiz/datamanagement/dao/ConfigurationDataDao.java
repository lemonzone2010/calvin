package com.apusic.ebiz.datamanagement.dao;

import java.util.List;

import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.datamanagement.Category;
import com.apusic.ebiz.model.datamanagement.ConfigurationData;

public interface ConfigurationDataDao {

	ConfigurationData getConfigurationDatabyKey(String key);

	List<ConfigurationData> getConfigurationDatasByCategory(Category category);
	
	Page<ConfigurationData> getConfigurationDatasByCategoryIdForPage(Category category,final int size);
	
	

}
