package com.apusic.ebiz.navigation.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.apusic.ebiz.model.navigation.ApplicationInfo;
import com.apusic.ebiz.model.navigation.Navigation;
import com.apusic.ebiz.navigation.service.ApplicationInfoService;

@Controller
@RequestMapping(value = "/applicationInfo")
public final class ApplicationInfoController extends	AbstractAjaxRestfulController<ApplicationInfo> {
	@Autowired
	@Qualifier("navigation_ApplicationInfoService")
	private ApplicationInfoService appService;
	@Override
	public String index(HttpServletRequest request) {
		request.setAttribute("applications", appService.findAll());
		return super.index(request);
	}
	@Override
	protected String getShowPage() {
		return "applicationManager";
	}
	
	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Result> updateApp(@RequestBody ApplicationInfo model) {
		logger.debug("request update app(POST),model:" + model);
		try {
			ApplicationInfo nav = null;
		        if(model.getId()!=null&&model.getId()>0){
		            nav = appService.findById(model.getId());
		        }else{
		            nav = new ApplicationInfo();
		        }
		        nav.setStatus(model.getStatus());
		        nav.setApplicationName(model.getApplicationName());
		        nav.setSequence(model.getSequence());
		        if(model.getId()!=null&&model.getId()>0){
		        	 appService.update(nav);
		        } else {
		            appService.add(nav);
		        }
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}
	@RequestMapping(value="updatestatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Result> updateNaviStatus(@RequestParam String type,@PathVariable Integer id) {
		logger.debug("request updateNaviStatus(POST),type:" + type);
		try {
			if (StringUtils.equals(type, "enable")) {
				appService.enable(id);
			} else {
				appService.disable(id);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getFailResult(e.getMessage());
		}
		return getSuccessResult();
	}
	
	//排序的移动的保存
		@RequestMapping(value = "move", method = RequestMethod.POST)
		@ResponseBody
		public void move(@RequestParam("changeIds[]") int[] changeIds,@RequestParam("changeNumbers[]") int[] changeNumbers) {
			appService.updateSequence(changeIds, changeNumbers);
		}
}
