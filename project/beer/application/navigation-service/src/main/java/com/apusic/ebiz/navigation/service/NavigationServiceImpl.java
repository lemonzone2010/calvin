package com.apusic.ebiz.navigation.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.navigation.Navigation;
import com.apusic.ebiz.navigation.dao.NavigationDao;

@Service("navigation_NavigationService")
public class NavigationServiceImpl implements NavigationService {

    @Autowired
    private CrudService curdService;

    @Autowired
    private QueryService queryService;
    
    @Autowired
    private NavigationDao navigationDao;

    public List<Navigation> findAll() {
        return queryService.findAll(Navigation.class);
    }

    @Transactional(readOnly = true)
    public List<Navigation> findByLevel(int level) {
        return navigationDao.findByLevel(level);
    }

    @Transactional(readOnly = true)
    public List<Navigation> findByExample(Navigation nav) {
        return navigationDao.findByExample(nav);
    }

    @Transactional
    public void addNavigation(Navigation navigation) {
        curdService.create(navigation);
    }

    @Transactional
    public void deleteNavigation(Navigation navigation) {
        curdService.delete(navigation);
    }

    @Transactional
    public void updateNavigation(Navigation navigation) {
        curdService.update(navigation);
    }

    public Navigation findById(int id) {
        return curdService.retrieve(Navigation.class, id);
    }

    @Transactional
    public void deleteById(int id) {
        curdService.delete(Navigation.class, id);
    }

    @Transactional
    public boolean updateStatus(int id, String status) {
        if(id <= 0 || StringUtils.isEmpty(status)){
            return false;
        }
        Navigation navigation = this.findById(id);
        if(navigation == null){
            return false;
        }
        navigation.setStatus(status);
        Set<Navigation> childrens = navigation.getChildrens();
        for(Navigation nav : childrens){
            nav.setStatus(status);
        }
        this.updateNavigation(navigation);
        return true;
    }

    @Transactional
    public void updateSequence(int[] ids, int[] numbers) {
        if(ArrayUtils.isEmpty(ids) || ArrayUtils.isEmpty(numbers)){
            return;
        }
        List<Navigation> navigations = navigationDao.findByIds(ids);
        for(int i=0;i<ids.length;i++){
            for(Navigation navigation : navigations){
                if(navigation.getId() == ids[i]){
                    navigation.setSequence(numbers[i]);
                }
            }
        }
    }
}
