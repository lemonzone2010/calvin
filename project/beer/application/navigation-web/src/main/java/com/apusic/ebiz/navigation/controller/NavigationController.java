package com.apusic.ebiz.navigation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * 更新
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="updatestatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Result> updateNaviStatus(@RequestBody Navigation model) {
		logger.debug("request update one(POST),model:" + model);
		try {
			boolean success = navigationService.updateStatus(model.getId(), model.getStatus());
			if (success) {
				return getSuccessResult();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}
	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Result> updateNavi(@RequestBody Navigation model) {
		logger.debug("request update one(POST),model:" + model);
		Integer hideId=model.getId();
		try {
			 Navigation nav = null;
		        if(model.getId()!=null&&model.getId()>0){
		            nav = navigationService.findById(model.getId());
		        }else{
		            nav = new Navigation();
		            hideId=model.getParentId();
		        }

		        nav.setLevel(model.getLevel());
		        nav.setName(model.getName());
		        nav.setUrl(model.getUrl());
		        nav.setApplicationId(model.getApplicationId());
		        nav.setSequence(model.getSequence());
		        nav.setStatus(model.getStatus());
		        if(model.getParentId() !=null && model.getParentId()>0){
		            Navigation parent = navigationService.findById(model.getParentId());
		            nav.setParent(parent);
		            Set<Navigation> childrens = parent.getChildrens();
		            childrens.add(nav);
		            parent.setChildrens(childrens);
		        }
		        if(model.getId()!=null&&model.getId()>0){
		            navigationService.updateNavigation(nav);
		        }else{
		            navigationService.addNavigation(nav);
		        }
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult(hideId);
	}

	//排序的移动的保存
	@RequestMapping(value = "move", method = RequestMethod.POST)
	@ResponseBody
	public void move(@RequestParam("changeIds[]") int[] changeIds,@RequestParam("changeNumbers[]") int[] changeNumbers) {
		navigationService.updateSequence(changeIds, changeNumbers);
	}
	
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
