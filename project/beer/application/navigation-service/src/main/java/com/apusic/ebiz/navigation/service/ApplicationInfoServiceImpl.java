package com.apusic.ebiz.navigation.service;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.model.navigation.ApplicationInfo;
import com.apusic.ebiz.navigation.dao.ApplicationInfoDao;

@Service("navigation_ApplicationInfoService")
public class ApplicationInfoServiceImpl implements ApplicationInfoService {

    @Autowired
    private CrudService curdService;
    
    @Autowired
    private ApplicationInfoDao appDao;

    @Transactional
    public void add(ApplicationInfo app) {
        app.setStatus("Y");
        curdService.create(app);
    }

    @Transactional
    public void delete(ApplicationInfo app) {
        curdService.delete(app);
    }

    @Transactional(readOnly=true)
    public List<ApplicationInfo> findAll() {
        ApplicationInfo example = new ApplicationInfo();
        return appDao.findData(example);
    }

    @Transactional(readOnly=true)
    public List<ApplicationInfo> findByExample(ApplicationInfo app) {
        return appDao.findData(app);
    }

    @Transactional(readOnly=true)
    public ApplicationInfo findById(int id) {
        return curdService.retrieve(ApplicationInfo.class, id);
    }

    @Transactional
    public void update(ApplicationInfo app) {
        curdService.update(app);
    }

    @Transactional
    public void deleteById(int id){
        curdService.delete(ApplicationInfo.class, id);
    }

    @Transactional(readOnly=true)
    public List<ApplicationInfo> findData(ApplicationInfo app){
        return appDao.findData(app);
    }
    
    @Transactional
    public void disable(int id){
        updateStatus(id,"N");
    }

    @Transactional
    public void enable(int id){
        updateStatus(id,"Y");
    }
    
    @Transactional
    private void updateStatus(int id,String status){
        if(id <= 0){
            return;
        }
        ApplicationInfo app = this.findById(id);
        if (app != null) {
            app.setStatus(status);
            this.update(app);
        }
    }
    
    @Transactional(readOnly=true)
    public String findNameByRoot(String root){
        ApplicationInfo app = new ApplicationInfo();
        app.setApplicationRoot(root);
        List<ApplicationInfo> apps = this.findByExample(app);
        if(apps != null && apps.size() > 0) {
            return apps.get(0).getApplicationName();
        }
        return null;
    }

    @Transactional
    public void updateSequence(int[] ids, int[] numbers) {
        if(ArrayUtils.isEmpty(ids) || ArrayUtils.isEmpty(numbers)){
            return;
        }
        List<ApplicationInfo> apps = appDao.findByIds(ids);
        for(int i = 0;i<ids.length;i++){
            for(ApplicationInfo app : apps){
                if(app.getId() == ids[i]){
                    app.setSequence(numbers[i]);
                }
            }
        }
    }
}
