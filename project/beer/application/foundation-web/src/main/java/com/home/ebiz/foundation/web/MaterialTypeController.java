package com.home.ebiz.foundation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apusic.ebiz.framework.web.controller.AbstractAjaxRestfulController;
import com.apusic.ebiz.model.foundation.MaterialType;
import com.home.ebiz.foundation.service.MaterialTypeService;
@Controller
@RequestMapping(value="/materialType")
public class MaterialTypeController extends AbstractAjaxRestfulController<MaterialType> {
	//以下赋值可有可无,for扩展
	@Autowired
	MaterialTypeService materialTypeService;
	public MaterialTypeController() {
		super();
		ajaxRestService=materialTypeService;
	}
	@Override
	protected String getShowPage() {//TODO:config serve,and run.include,js,jsp,xml
		return "foundation/materialType";
	}

}
