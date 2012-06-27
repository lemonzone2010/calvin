package com.apusic.smartorg.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apusic.ebiz.framework.web.controller.AbstractAjaxRestfulController;
import com.apusic.ebiz.framework.web.controller.Result;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.RoleService;
import com.apusic.ebiz.smartorg.service.UserRoleService;
import com.apusic.ebiz.smartorg.service.UserService;

@Controller
@RequestMapping(value = "/user")
public final class UserController extends	AbstractAjaxRestfulController<User> {
	@Autowired
	@Qualifier("smartorg_RoleService")
	private RoleService roleService;


	@Autowired
	@Qualifier("smartorg_UserService")
    private UserService userService;

	@Autowired
	@Qualifier("smartorg_UserRoleService")
    private UserRoleService userRoleservice;
    
	@Override
	protected String getShowPage() {
		return "user/userManager";
	}
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Result> create(@RequestBody User model) {
		logger.debug("request create one(POST),model:" + model);
		try {
			userService.addUser(model);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Result> update(@RequestBody User model) {
		logger.debug("request delete one(PUT),model:" + model);
		try {
			userService.updateUser(model);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}
	
	/** 授权,以及取消授权，资源对角色的授权 */
	@RequestMapping(value = "grant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Result> grant(@RequestParam("roleids[]") int[] roleids,
			@RequestParam("userIds[]") int[] userIds, @RequestParam("type") String type) {
		logger.debug("request grant/ungrant resource,id:" + Arrays.toString(roleids) + "," + type);
		try {
			if (StringUtils.equals(type, "grant")) {
				userRoleservice.grantRolesToUsers(roleids,userIds);
			}else {
				userRoleservice.revokeRolesFromUsers(roleids,userIds);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}
}
