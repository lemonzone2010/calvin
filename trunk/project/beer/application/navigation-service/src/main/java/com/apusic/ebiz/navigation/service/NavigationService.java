package com.apusic.ebiz.navigation.service;

import java.util.List;

import com.apusic.ebiz.model.navigation.Navigation;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public interface NavigationService {
    List<Navigation> findAll();
    
    @Cacheable(cacheName = "NavigationService")
    List<Navigation> findByLevel(int level);
    
    @TriggersRemove(cacheName="NavigationService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void addNavigation(Navigation navigation);
    
    @TriggersRemove(cacheName="NavigationService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void updateNavigation(Navigation navigation);
    
    @TriggersRemove(cacheName="NavigationService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void deleteNavigation(Navigation navigation);
    
    @TriggersRemove(cacheName="NavigationService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void deleteById(int id);
    
    @Cacheable(cacheName = "NavigationService")
    Navigation findById(int id);
    
    List<Navigation> findByExample(Navigation nav);
    
    @TriggersRemove(cacheName="NavigationService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    boolean updateStatus(int id,String status);

    @TriggersRemove(cacheName="NavigationService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void updateSequence(int[] ids, int[] numbers);
}
