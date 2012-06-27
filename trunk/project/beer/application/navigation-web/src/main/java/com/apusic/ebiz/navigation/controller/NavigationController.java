package com.apusic.ebiz.navigation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apusic.ebiz.framework.web.controller.AbstractAjaxRestfulController;
import com.apusic.ebiz.framework.web.controller.Result;
import com.apusic.ebiz.model.navigation.Navigation;
import com.apusic.ebiz.navigation.service.NavigationService;

@Controller
@RequestMapping(value = "/navigation")
public final class NavigationController extends	AbstractAjaxRestfulController<Navigation> {
	@Autowired
	@Qualifier("navigation_NavigationService")
	private NavigationService navigationService;
	
	public List<Navigation> navigations = new ArrayList<Navigation>();
	
	@RequestMapping(value = "app/{appId}", method = RequestMethod.GET)
	public String app(@PathVariable Integer appId,HttpServletRequest request) {
		navigations=queryData(appId);
		request.setAttribute("navigations", navigations);
		request.setAttribute("appId", appId);
		return getShowPage();
	}
	
	/**
	 * 保存新增
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Result> updateNavi(@RequestBody Navigation model) {
		logger.debug("request update one(POST),model:" + model);
		try {
			//prepareModelInsert(model);
			//ajaxRestService.create(model);
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
	/*@RequestMapping(value="saveOrUpdate",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Result> saveOrUpdate(@RequestBody Navigation model) {
		logger.debug("request delete one(PUT),model:" + model);
		try {
			//prepareModelUpdate(model);
			//ajaxRestService.update(model);// 更新方式
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}*/
	private List<Navigation> queryData(int appId){
		Navigation nav = new Navigation();
		nav.setLevel(1);
		nav.setApplicationId(appId);
		return navigationService.findByExample(nav);
	}
	@Override
	protected String getShowPage() {
		return "navigationManager";
	}
}
