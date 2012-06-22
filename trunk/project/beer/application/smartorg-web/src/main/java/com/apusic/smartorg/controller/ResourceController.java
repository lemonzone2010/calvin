package com.apusic.smartorg.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apusic.ebiz.framework.web.controller.AbstractAjaxRestfulController;
import com.apusic.ebiz.framework.web.controller.Result;
import com.apusic.ebiz.model.user.Resource;
import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.smartorg.service.ResourceService;
import com.apusic.ebiz.smartorg.service.RoleResourceService;
import com.apusic.ebiz.smartorg.service.RoleService;

@Controller
@RequestMapping(value = "/resource")
public final class ResourceController extends AbstractAjaxRestfulController<Resource> {

	@Autowired
	@Qualifier("smartorg_RoleService")
	private RoleService roleService;

	@Autowired
	@Qualifier("smartorg_ResourceService")
	private ResourceService resourceService;

	@Autowired
	@Qualifier("smartorg_RoleResourceService")
	private RoleResourceService roleResourceService;

	@Override
	protected String getShowPage() {
		return "resource/resourceManager";
	}

	/** 授权,以及取消授权，资源对角色的授权 */
	@RequestMapping(value = "grant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Result> grant(@RequestParam("roleids[]") Integer[] roleids,
			@RequestParam("resourceIds[]") Integer[] resourceIds, @RequestParam("type") String type) {
		logger.debug("request grant/ungrant resource,id:" + Arrays.toString(roleids) + "," + type);
		try {
			List<Role> list = new ArrayList<Role>();
			for (Integer id : roleids) {
				list.add(roleService.getRoleById(id));
			}
			for (Role role : list) {

				List<Resource> resources = new ArrayList<Resource>();
				for (Integer resourceId : resourceIds) {
					Resource resource = resourceService.getResourceById(resourceId);
					resources.add(resource);
					if (StringUtils.equals(type, "grant")) {
						roleResourceService.authorizationForResources(resources, role);
					} else {
						roleResourceService.unAuthorizationForResources(resources, role);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}
}
