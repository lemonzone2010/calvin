package com.apusic.smartorg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apusic.ebiz.framework.web.controller.AbstractAjaxRestfulController;
import com.apusic.ebiz.model.user.Role;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends AbstractAjaxRestfulController<Role> {

	@Override
	protected String getShowPage() {
		return "role/roleManager";
	}

	/*@RequestMapping(value = "/showError")
	public String showError() {
		return "error";
	}*/

}
