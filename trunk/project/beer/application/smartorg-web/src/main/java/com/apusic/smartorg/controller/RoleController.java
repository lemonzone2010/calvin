package com.apusic.smartorg.controller;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.web.controller.AbstractAjaxRestfulController;
import com.apusic.ebiz.model.user.Role;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends AbstractAjaxRestfulController<Role> {

	@Override
	protected String getShowPage() {
		return "role/roleManager";
	}

	/*
	 * @RequestMapping(value = "/showError") public String showError() { return
	 * "error"; }
	 */
	@Override
	public Page<Role> gridQuery(HttpServletRequest arg0) {
		Page<Role> gridQuery = super.gridQuery(arg0);
		/*ObjectMapper mapper = new ObjectMapper();
		StringWriter w = new StringWriter();
		try {
			mapper.writeValue(w, gridQuery);
			System.out.println(w);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return gridQuery;
	}
}
