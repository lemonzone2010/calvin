package com.home.ebiz.foundation.service;

import org.springframework.stereotype.Service;

import com.apusic.ebiz.framework.core.service.DefaultAjaxRestServiceImpl;
import com.apusic.ebiz.model.foundation.MaterialType;
@Service("materialTypeService")
public class MaterialTypeServiceImpl extends DefaultAjaxRestServiceImpl<MaterialType> implements MaterialTypeService{
}
