package com.apusic.ebiz.navigation.service;

import java.util.List;

import com.apusic.ebiz.model.navigation.ApplicationInfo;

public interface ApplicationInfoService {
    List<ApplicationInfo> findAll();
    
    List<ApplicationInfo> findByExample(ApplicationInfo app);
    
    ApplicationInfo findById(int id);
    
    void update(ApplicationInfo app);
    
    void add(ApplicationInfo app);
    
    void delete(ApplicationInfo app);
    
    void deleteById(int id);
    
    List<ApplicationInfo> findData(ApplicationInfo app);
    
    void disable(int id);
    
    void enable(int id);
    
    String findNameByRoot(String root);

    void updateSequence(int[] ids, int[] numbers);
}
