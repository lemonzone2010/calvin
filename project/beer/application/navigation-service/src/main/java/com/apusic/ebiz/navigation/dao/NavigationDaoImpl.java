package com.apusic.ebiz.navigation.dao;

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
import com.apusic.ebiz.model.navigation.Navigation;

@Repository("navigation_NavigationDaoImpl")
public class NavigationDaoImpl implements NavigationDao {

    @Autowired
    private QueryService queryService;

    public List<Navigation> findByIds(int[] ids) {
        if(ArrayUtils.isEmpty(ids)){
            return new ArrayList<Navigation>(0);
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Navigation.class);
        criteria.add(Restrictions.in("id", ArrayUtils.toObject(ids)));
        return queryService.findBy(criteria);
    }

    public List<Navigation> findByLevel(int level) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Navigation.class);
        criteria.add(Restrictions.eq("level", Integer.valueOf(level)));
        criteria.add(Restrictions.eq("status", "Y"));
        criteria.addOrder(Order.asc("sequence"));
        criteria.addOrder(Order.asc("id"));
        return queryService.findBy(criteria);
    }

    public List<Navigation> findByExample(Navigation nav) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Navigation.class);
        if(nav.getApplicationId() != null){
            criteria.add(Restrictions.eq("applicationId", nav.getApplicationId()));
        }
        if(StringUtils.isNotEmpty(nav.getName())){
            criteria.add(Restrictions.eq("name", nav.getName()));
        }
        if(StringUtils.isNotEmpty(nav.getStatus())){
            criteria.add(Restrictions.eq("status", nav.getStatus()));
        }
        if(nav.getLevel() != null){
            criteria.add(Restrictions.eq("level", nav.getLevel()));
        }
        criteria.addOrder(Order.asc("sequence"));
        criteria.addOrder(Order.asc("id"));
        return queryService.findBy(criteria);
    }

}
