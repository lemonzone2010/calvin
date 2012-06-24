package com.home.ebiz.foundation.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.web.controller.AbstractAjaxRestfulController;
import com.apusic.ebiz.framework.web.controller.Result;
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "gridtree", method = RequestMethod.POST)
	@ResponseBody
	public List<Organization> gridTreeQuery(HttpServletRequest request) {
		logger.debug("request gridtreeQuery(POST)");
		Page<Organization> p = new Page<Organization>().setRequestMap(request.getParameterMap());
		p.setPageSize(999);
		logger.debug("need query:" + p);
		try {
			p = organizationService.findNoChild();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		logger.debug("get query result:"+p);
		return p.getRows();
	}
	
	/**
	 * 保存新增
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@Override
	public Map<String, Result> create(@RequestBody Organization model) {
		logger.debug("request create one(POST),model:" + model);
		try {
			Integer parentId = model.getParentId();
			if(parentId!=null) {
				Organization parent = organizationService.retrieve(entityClass, parentId);
				parent.addChild(model);
			}
			ajaxRestService.create(model);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}
}
