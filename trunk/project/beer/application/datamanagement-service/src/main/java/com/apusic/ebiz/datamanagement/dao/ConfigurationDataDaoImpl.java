package com.apusic.ebiz.datamanagement.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.datamanagement.Category;
import com.apusic.ebiz.model.datamanagement.ConfigurationData;

@Repository("datamanagement_ConfigurationDataDao")
public class ConfigurationDataDaoImpl implements ConfigurationDataDao {

	@Autowired
	private QueryService queryService;

	public ConfigurationData getConfigurationDatabyKey(String key) {
		ConfigurationData c = new ConfigurationData();
		c.setKey(key);
		List<ConfigurationData> findByExample = queryService.findByExample(c);
		if(findByExample!=null && findByExample.size() > 0){
			return findByExample.get(0);
		}
		return null;
	}

	public List<ConfigurationData> getConfigurationDatasByCategory(
			Category category) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ConfigurationData.class);
		criteria.add(Restrictions.eq("category", category));
		criteria.addOrder(Order.desc("id"));
		return queryService.findBy(criteria);
	}

	public Page<ConfigurationData> getConfigurationDatasByCategoryIdForPage(
			Category category, int size) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ConfigurationData.class);
		criteria.add(Restrictions.eq("category", category));
		criteria.addOrder(Order.desc("id"));
		return queryService.findPageBy(criteria, size);
	}
}
