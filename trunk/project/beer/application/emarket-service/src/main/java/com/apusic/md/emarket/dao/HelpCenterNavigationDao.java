package com.apusic.md.emarket.dao;

import java.util.List;

import com.apusic.md.model.emarket.HelpCenterNavigation;

public interface HelpCenterNavigationDao {

	List<HelpCenterNavigation> findByIds(int[] ids);   
  
	List<HelpCenterNavigation> findByExample(HelpCenterNavigation hcn); 
}
