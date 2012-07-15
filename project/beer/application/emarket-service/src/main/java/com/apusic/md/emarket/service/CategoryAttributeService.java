package com.apusic.md.emarket.service;

import java.util.List;

import com.apusic.md.model.emarket.CategoryAttr;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public interface CategoryAttributeService {
	@Cacheable(cacheName = "CategoryAttributeService")
	List<CategoryAttr> getAttibutesByCategoryId(Integer categoryId);

	@TriggersRemove(cacheName="CategoryAttributeService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
	void createAttr(CategoryAttr attr);
    
    @Cacheable(cacheName = "CategoryAttributeService")
    CategoryAttr findById(int id);

    @TriggersRemove(cacheName="CategoryAttributeService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void deleteById(int id);
    
    @Cacheable(cacheName = "CategoryAttributeService")
	List<CategoryAttr> findAll();
}
