package com.home.ebiz.foundation.web;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apusic.ebiz.framework.web.controller.AbstractAjaxRestfulController;
import com.apusic.ebiz.model.foundation.Organization;
import com.home.ebiz.foundation.service.OrganizationService;

@Controller
@RequestMapping(value = "/organization")
public final class OrganizationController extends AbstractAjaxRestfulController<Organization> implements InitializingBean{

	@Autowired
	private OrganizationService organizationService;

	@Override
	protected String getShowPage() {
		return "foundation/OrganizationManager";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ajaxRestService = organizationService;
	}
}
