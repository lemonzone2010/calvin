package com.apusic.ebiz.framework.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.model.IdEntity;
import com.apusic.ebiz.framework.core.service.AjaxRestService;
import com.apusic.ebiz.framework.web.util.Reflections;


public abstract class AbstractAjaxRestfulController<T extends IdEntity> {//implements InitializingBean
	//TODO:test case
	protected final static Log logger = LogFactory.getLog(AbstractAjaxRestfulController.class);
	protected final static String RESULT = "result";

	//@Autowired protected Validator validator;//注入spring的JSR validator,貌似对{}的解析不成功，
	protected Class<T> entityClass;
	
	@Autowired
	protected AjaxRestService<T> ajaxRestService;

	public AbstractAjaxRestfulController() {
		generateEntityClass();
	}
	protected void generateEntityClass() {
		this.entityClass = Reflections.getSuperClassGenricType(getClass());
		logger.warn("generateEntityClass:"+entityClass);
	}

	protected T getModel(Integer id) throws InstantiationException, IllegalAccessException {
		if (id == null) {
			return entityClass.newInstance();
		}
		return ajaxRestService.retrieve(entityClass,id);
	}

	/**
	 * 主页的JSP文件，如demo/index,它存在配置好的web-inf/views/demo/index.jsp,
	 * 依赖于spring对前配置
	 * 
	 * @return
	 */
	protected abstract String getShowPage();

	/**
	 * 显示主界面
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		logger.debug("request index(GET)");
		return getShowPage();
	}

	/**
	 * 存在检查
	 * 
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "check/{property}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Result> check(@RequestParam String value, @PathVariable String property) {
		logger.debug("request check(GET):" + value);
		T result = ajaxRestService.findBy(entityClass,property, value);
		logger.debug("get entity:" + result);
		return (null != result) ? getSuccessResult() : getFailResult("");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "grid", method = RequestMethod.POST)
	@ResponseBody
	public Page<T> gridQuery(HttpServletRequest request) {
		logger.debug("request gridQuery(POST)");
		Page<T> p = new Page<T>().setRequestMap(request.getParameterMap());
		logger.debug("need query:" + p);
		try {
			p = ajaxRestService.findPage(entityClass,p);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		logger.debug("get query result:"+p);
		return p;
	}
	/**
	 * 显示单条记录,更新记录时可能要用
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Result> show(@PathVariable Integer id) {
		logger.debug("request show one(GET),id:" + id);
		T t;
		try {
			t = getModel(id);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult(t);
	}

	/**
	 * 保存新增
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Result> create(@RequestBody T model) {
		logger.debug("request create one(POST),model:" + model);
		try {
			prepareModelInsert(model);
			validate(model);
			ajaxRestService.create(model);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}
	
	protected void prepareModelInsert(T model)throws Exception {
		
	}
	private void validate(T model) {
		model.validate();
	}

	protected Map<String, Result> getSuccessResult(Object extend) {
		Result successResult = Result.getSuccessResult();
		successResult.setExtend(extend);
		return Collections.singletonMap(RESULT, successResult);
	}

	protected Map<String, Result> getSuccessResult() {
		return Collections.singletonMap(RESULT, Result.getSuccessResult());
	}

	protected Map<String, Result> getFailResult(String failMsg) {
		return Collections.singletonMap(RESULT, Result.getFailResult(failMsg));
	}

	/**
	 * 删除单个
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Result> delete(@PathVariable Integer id) {
		logger.debug("request delete one(DELETE),id:" + id);
		try {
			ajaxRestService.delete(getModel(id));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}

	/** 批量删除 */
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Result> batchDelete(@RequestParam("items[]") Integer[] items) {
		logger.debug("request delete batch(DELETE),id:" + Arrays.toString(items));
		try {
			List<T> list=new ArrayList<T>();
			for (Integer id : items) {
				list.add(ajaxRestService.retrieve(entityClass,id));
			}
			ajaxRestService.deleteAll(list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}

	/**
	 * 保存更新
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, ? extends Object> update(@RequestBody T model) {
		logger.debug("request delete one(PUT),model:" + model);
		try {
			validate(model);
			ajaxRestService.update(model);// 更新方式
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}

}
