package com.apusic.ebiz.navigation.dao;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.navigation.ApplicationInfo;

@Repository("navigation_ApplicationDao")
public class ApplicationInfoDaoImpl implements ApplicationInfoDao {
	@Autowired
	private QueryService queryService;

	public List<ApplicationInfo> findData(ApplicationInfo app) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ApplicationInfo.class);
		if (null != app) {
			if (app.getId() > 0) {
				criteria.add(Restrictions.eq("id", app.getId()));
			}
			if (StringUtils.isNotEmpty(app.getApplicationName())) {
				criteria.add(Restrictions.eq("applicationName", app.getApplicationName()));
			}
			if (StringUtils.isNotEmpty(app.getApplicationRoot())) {
				criteria.add(Restrictions.eq("applicationRoot", app.getApplicationRoot()));
			}
			if (StringUtils.isNotEmpty(app.getStatus())) {
				criteria.add(Restrictions.eq("status", app.getStatus()));
			}
		}
		criteria.addOrder(Order.asc("sequence"));
		criteria.addOrder(Order.asc("id"));
		return queryService.findBy(criteria);
	}

	public List<ApplicationInfo> findByIds(int[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ApplicationInfo.class);
		criteria.add(Restrictions.in("id", ArrayUtils.toObject(ids)));
		return queryService.findBy(criteria);
	}
}
