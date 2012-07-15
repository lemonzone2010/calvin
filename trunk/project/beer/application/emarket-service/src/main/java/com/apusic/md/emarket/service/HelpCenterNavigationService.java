package com.apusic.md.emarket.service;

import java.util.List;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

import com.apusic.md.model.emarket.HelpCenterContent;
import com.apusic.md.model.emarket.HelpCenterNavigation;

public interface HelpCenterNavigationService {

	@TriggersRemove(cacheName="HelpCenterNavigationService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void create(HelpCenterNavigation hng);  
	
	@TriggersRemove(cacheName="HelpCenterNavigationService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void deleteById(int id); 
	
	@TriggersRemove(cacheName="HelpCenterNavigationService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void updateSerialNumber(int[] ids, int[] numbers);
	
	@TriggersRemove(cacheName="HelpCenterNavigationService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void updateEntity(HelpCenterNavigation hng);
	
	List<HelpCenterNavigation> findByExample(HelpCenterNavigation hng);
	
	List<HelpCenterNavigation> findAll();
	
	@Cacheable(cacheName = "HelpCenterNavigationService")
	HelpCenterNavigation findById(int id);
	
	@Cacheable(cacheName = "HelpCenterNavigationService")
	List<HelpCenterNavigation> findByLevel(int level);
	
	HelpCenterContent findContentByTitleId(int id);
	
	HelpCenterContent createContent(HelpCenterContent hcc);
	
	void updateContent(HelpCenterContent hcc);
}
