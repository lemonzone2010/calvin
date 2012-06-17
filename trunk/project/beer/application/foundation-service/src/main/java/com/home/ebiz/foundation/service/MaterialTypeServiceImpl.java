package com.home.ebiz.foundation.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.apusic.ebiz.framework.core.service.DefaultAjaxRestServiceImpl;
import com.apusic.ebiz.model.foundation.MaterialType;
@Service
@Component("materialTypeService")
public class MaterialTypeServiceImpl extends DefaultAjaxRestServiceImpl<MaterialType> implements MaterialTypeService{
}
