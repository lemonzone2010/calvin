package com.apusic.ebiz.navigation.dao;

import java.util.List;

import com.apusic.ebiz.model.navigation.Navigation;

public interface NavigationDao {
    
    List<Navigation> findByLevel(int level);
    
    List<Navigation> findByIds(int[] ids);
    
    List<Navigation> findByExample(Navigation nav);

}
