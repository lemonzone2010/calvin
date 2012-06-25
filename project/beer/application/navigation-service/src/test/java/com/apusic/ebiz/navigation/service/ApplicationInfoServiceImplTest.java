package com.apusic.ebiz.navigation.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.model.navigation.ApplicationInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:apusic-ebiz-framework-core.xml",
                                   "classpath*:apusic-ebiz-navigation-service.xml",
                                   "classpath*:apusic-ebiz-navigation-service-user.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class ApplicationInfoServiceImplTest {

    @Autowired
    private ApplicationInfoService appService;
    
    @org.junit.Before
    @Rollback(false)
    public void TestAdd(){
        for(int i = 0; i < 6; i++){
            ApplicationInfo app = new ApplicationInfo();
            app.setApplicationName("会员中心");
            app.setApplicationRoot("member");
            app.setSequence(1);
            app.setStatus("Y");
            appService.add(app);
        }
    }
    @Test
    @Rollback(false)
    public void testFindAll(){
        List<ApplicationInfo> apps = appService.findAll();
        assertTrue(apps.size() > 0);
    }
    @Test
    public void testFindByExample(){
        ApplicationInfo app = new ApplicationInfo();
        app.setApplicationName("会员中心");
        app.setApplicationRoot("member");
        List<ApplicationInfo> apps = appService.findByExample(app);
        assertTrue(apps.size() > 0);
    }
    @Test
    public void testFindById(){
        List<ApplicationInfo> apps = appService.findAll();
        ApplicationInfo app = appService.findById(apps.get(0).getId());
        assertTrue(app != null);
    }
    @Test
    @Rollback(true)
    public void  testUpdate(){
        List<ApplicationInfo> apps = appService.findAll();
        ApplicationInfo app = apps.get(0);
        app.setApplicationName("测试");
        appService.update(app);
        ApplicationInfo example = new ApplicationInfo();
        example.setApplicationName("测试");
        List<ApplicationInfo> appsNew = appService.findByExample(example);
        assertTrue(appsNew.size() > 0);
    }
    @Test
    @Rollback(true)
    public void testDelete(){
        List<ApplicationInfo> apps = appService.findAll();
        appService.delete(apps.get(0));
        List<ApplicationInfo> appsNew = appService.findAll();
        assertTrue(apps.size() > appsNew.size());
    }
    @Test
    @Rollback(true)
    public void testDeleteById(){
        List<ApplicationInfo> apps = appService.findAll();
        appService.deleteById(apps.get(0).getId());
        List<ApplicationInfo> appsNew = appService.findAll();
        assertTrue(apps.size() > appsNew.size());
    }
    @Test
    public void testFindData(){
        ApplicationInfo app = new ApplicationInfo();
        app.setApplicationRoot("member");
        List<ApplicationInfo> apps = appService.findData(app);
        assertTrue(apps.size() > 0);
    }
    @Test
    @Rollback(true)
    public void testDisable(){
        List<ApplicationInfo> apps = appService.findAll();
        ApplicationInfo app = apps.get(0);
        appService.disable(app.getId());
        ApplicationInfo appNew = appService.findById(app.getId());
        assertTrue("N".equals(appNew.getStatus()));
    }
    @Test
    @Rollback(true)
    public void testEnable(){
        List<ApplicationInfo> apps = appService.findAll();
        ApplicationInfo app = apps.get(0);
        appService.enable(app.getId());
        ApplicationInfo appNew = appService.findById(app.getId());
        assertTrue("Y".equals(appNew.getStatus()));
    }
    @Test
    public void testFindNameByRoot(){
        String name = appService.findNameByRoot("member");
        assertTrue(name.length() > 0);
    }
    @Test
    public void updateSequence(){
        List<ApplicationInfo> apps = appService.findAll();
        int[] ids = new int[apps.size()];
        int[] numbers = new int[apps.size()];
        for(int i = 0;i<apps.size();i++){
            ApplicationInfo info = apps.get(i);
            ids[i] = info.getId();
            numbers[i] = i;
        }
        appService.updateSequence(ids,numbers);
        List<ApplicationInfo> appsNew = appService.findAll();
        assertTrue(appsNew.get(0).getSequence()==0&& appsNew.get(1).getSequence() == 1);
    }
}
