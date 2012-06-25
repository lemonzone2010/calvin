package com.apusic.ebiz.navigation.dao;

import java.util.List;

import com.apusic.ebiz.model.navigation.ApplicationInfo;

public interface ApplicationInfoDao {
    List<ApplicationInfo> findData(ApplicationInfo app);
    
    List<ApplicationInfo> findByIds(int[] ids);
}
