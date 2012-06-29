package com.apusic.ebiz.datamanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.datamanagement.DatamanagementException;
import com.apusic.ebiz.datamanagement.dao.ConfigurationDataDao;
import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.datamanagement.Category;
import com.apusic.ebiz.model.datamanagement.ConfigurationData;

@Service("datamanagement_ConfigurationDataService")
public class ConfigurationDataServiceImpl implements ConfigurationDataService {

	@Autowired
	private CrudService crudService;

	@Autowired
	private ConfigurationDataDao configurationDataDao;

	@Autowired
	private CategoryService categoryService;
	  @Autowired
		@Qualifier("ebiz_ConfigService")
		private ConfigService configService;
	@Transactional
	public void addConfigurationData(ConfigurationData data) {
		this.crudService.create(data);
	}

	@Transactional
	public void deleteConfigurationData(int id) {
		this.crudService.delete(ConfigurationData.class,id);

	}

	@Transactional(readOnly=true)
	public ConfigurationData getConfigurationData(int id) {
		return this.crudService.retrieve(ConfigurationData.class, id);
	}

	@Transactional
	public void updateConfigurationData(ConfigurationData data) {
		this.crudService.update(data);
	}

	@Transactional(readOnly=true)
	public String getValue(String key) {
		ConfigurationData configurationData = configurationDataDao.getConfigurationDatabyKey(key);
		if(configurationData==null){
			throw new DatamanagementException("DataManagement-Err-002",new Object[]{key});
		}
		return configurationData.getValue();
	}

	@Transactional(readOnly=true)
	public int getIntegerValueByKey(String key) {
		String value = this.getValue(key);
		return Integer.parseInt(value);
	}

	@Transactional(readOnly=true)
	public String getValueByKey(String key) {
		return this.getValue(key);
	}

	@Transactional(readOnly=true)
	public List<ConfigurationData> getConfigurationDatasByCategoryId(int id) {
		Category category = categoryService.getCategory(id);
		if(category==null){
			return null;
		}else{
			return configurationDataDao.getConfigurationDatasByCategory(category);
		}
	}

	@Transactional
	public void deleteDatas(int[] ids) {
		if (ids==null || ids.length==0){
			return;
		}
		for(int i : ids){
			this.deleteConfigurationData(i);
		}
	}

  	@Transactional(readOnly = true)
	public Page<ConfigurationData> getConfigurationDatasByCategoryIdForPage(
			int id) {
		Category category = categoryService.getCategory(id);
		if (category == null) {
			return null;
		} else {
			return configurationDataDao.getConfigurationDatasByCategoryIdForPage(category,
							configService.getIntegerValueByKey("pageSize"));
		}
	}

	public ConfigurationData getDataByKey(String key) {
		ConfigurationData configurationData = configurationDataDao.getConfigurationDatabyKey(key);
		return configurationData;
	}

	public List<ConfigurationData> getConfigurationDatasByCategoryName(String name) {
		Category category = categoryService.getCategoryByName(name);
		return configurationDataDao.getConfigurationDatasByCategory(category);
		
	}
}
