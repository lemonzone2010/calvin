package com.home.ebiz.foundation.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.service.DefaultAjaxRestServiceImpl;
import com.apusic.ebiz.model.foundation.Organization;

@Service("organizationService")
public class OrganizationServiceImpl extends DefaultAjaxRestServiceImpl<Organization> implements OrganizationService {

	
	@Override
	public Organization create(Organization org) {
		Organization org_db = findBy(Organization.class, "orgCode", org.getOrgCode());
		if (org_db != null) {
			throw new RuntimeException("组织编码" + org.getOrgCode() + "已存在，请重新填写.");
		}
		return super.create(org);
	}
	@Transactional(readOnly=true)
	public Page<Organization>  findNoChild() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Organization.class);
		criteria.add(Restrictions.isEmpty("childs"));
		return repositoryFacade.findPageBy(criteria, 999);
	}

}
