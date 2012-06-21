package com.apusic.smartorg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apusic.ebiz.framework.web.controller.AbstractAjaxRestfulController;
import com.apusic.ebiz.model.user.Resource;

@Controller
@RequestMapping(value = "/resource")
public final class ResourceController extends	AbstractAjaxRestfulController<Resource> {
	@Override
	protected String getShowPage() {
		return "resource/resourceManager";
	}
}
