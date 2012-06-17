package com.home.ebiz.foundation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apusic.ebiz.framework.web.controller.AbstractAjaxRestfulController;
import com.apusic.ebiz.model.foundation.MaterialType;
import com.apusic.ebiz.model.foundation.TestEntity;
@Controller
@RequestMapping(value="/entityTest")
public class EntityTestController extends AbstractAjaxRestfulController<TestEntity> {

	@Override
	protected String getShowPage() {//TODO:config serve,and run.include,js,jsp,xml
		return "materialType/index";
	}

}
