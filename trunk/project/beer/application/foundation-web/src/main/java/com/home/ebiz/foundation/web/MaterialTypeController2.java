package com.home.ebiz.foundation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apusic.ebiz.framework.web.controller.AbstractAjaxRestfulController;
import com.apusic.ebiz.model.foundation.MaterialTypeEntity;
@Controller
@RequestMapping(value="/materialType2")
public class MaterialTypeController2 extends AbstractAjaxRestfulController<MaterialTypeEntity> {
	@Override
	protected String getShowPage() {//TODO:config serve,and run.include,js,jsp,xml
		return "foundation/materialType2";
	}

}
