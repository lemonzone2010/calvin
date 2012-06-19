package com.apusic.ebiz.smartorg.dao;

import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.user.Resource;

@Repository("smartorg_ResourceDao")
public class ResourceDaoImpl implements ResourceDao {

	@Autowired
	private QueryService queryService;

	public Resource getResourceByContextAndValue(String context, String value) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Resource.class);
		criteria.add(Restrictions.eq("resContext", context));
		criteria.add(Restrictions.eq("resValue", value));
		List<Resource> resources = queryService.findBy(criteria);
		if(resources!=null && resources.size()>0){
			return resources.get(0);
		}
		return null;
	}

	public List<Resource> findresourceByRoleId(int id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Resource.class);
		criteria.createAlias("roles", "role").add(Restrictions.eq("id", id));
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return queryService.findBy(criteria);
	}
}
