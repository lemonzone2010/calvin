package com.apusic.ebiz.framework.core.service;

import org.springframework.stereotype.Service;

import com.apusic.ebiz.framework.core.model.IdEntity;

@Service(value="ajaxRestService")
public class AjaxRestServiceImpl<T  extends IdEntity> extends DefaultAjaxRestServiceImpl<T> implements AjaxRestService<T> {

}
