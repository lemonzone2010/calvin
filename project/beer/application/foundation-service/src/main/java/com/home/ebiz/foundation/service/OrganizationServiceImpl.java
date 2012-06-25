package com.home.ebiz.foundation.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	@Transactional(readOnly = true)
	public Page<Organization> findNoChild() {
		//criteria.add(Restrictions.isNotEmpty("childs"));
		 Page<Organization> page=new Page<Organization>();
		 Organization root = retrieve(Organization.class, new Integer(1));
		 List<Organization> list=new ArrayList<Organization>();
		 list.add(root);
		 
		 page.setRows(list);
		return page;
	}

	@Override
	@Transactional
	public void deleteAll(Collection<Organization> e) {
		for (Organization organization : e) {
			deleteChild(organization);
		}
	}

	private void deleteChild(Organization child) {
		List<Organization> childs = child.getChilds();
		if (childs.size() > 0) {
			for (Organization c : childs) {
				deleteChild(c);
			}
		}
		delete(child);
	}
}
