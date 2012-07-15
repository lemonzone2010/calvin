package com.apusic.md.emarket.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.emarket.HelpCenterNavigation;

@Repository("emarket_HelpCenterNavigationDao")
public class HelpCenterNavigationDaoImpl implements HelpCenterNavigationDao {
	
	@Autowired
	private QueryService queryService; 
  
	public List<HelpCenterNavigation> findByIds(int[] ids) {
		if(ArrayUtils.isEmpty(ids)){
            return new ArrayList<HelpCenterNavigation>(0);
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(HelpCenterNavigation.class);
        criteria.add(Restrictions.in("id", ArrayUtils.toObject(ids)));
        return queryService.findBy(criteria);
	}

	public List<HelpCenterNavigation> findByExample(HelpCenterNavigation hcn) {
		DetachedCriteria criteria = DetachedCriteria.forClass(HelpCenterNavigation.class);
		if(hcn.getId() > 0){
			criteria.add(Restrictions.eq("id", hcn.getId()));
		}
		if(hcn.getLevel() != null){
			criteria.add(Restrictions.eq("level", hcn.getLevel()));
		}
		if(StringUtils.isNotEmpty(hcn.getName())){
			criteria.add(Restrictions.eq("name", hcn.getName()));
		}
		criteria.addOrder(Order.asc("serialNumber"));
        criteria.addOrder(Order.asc("id"));
		return queryService.findBy(criteria);
	}

}
