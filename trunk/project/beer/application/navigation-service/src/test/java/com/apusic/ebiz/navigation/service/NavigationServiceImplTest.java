package com.apusic.ebiz.navigation.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.model.navigation.Navigation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:apusic-ebiz-framework-core.xml",
                                   "classpath*:apusic-ebiz-navigation-service.xml",
                                   "classpath*:apusic-ebiz-navigation-service-user.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class NavigationServiceImplTest {

    @Autowired
    private NavigationService ns;
    
    @Before
    @Rollback(false)
    public void testAddNavigation() {
        for (int i = 0; i < 6; i++) {
            Navigation ng = new Navigation();
            ng.setStatus("Y");
            ng.setApplicationId(1);
            ng.setLevel(1);
            ng.setName("会员管理");
            ng.setRoles("admin,manage");
            ng.setSequence(1);
            ng.setUrl("http://www.baidu.com");
            ns.addNavigation(ng);
        }
        List<Navigation> ngs = ns.findAll();
        assertTrue(ngs.size() > 0);
    }

    @Test
    public void testFindAll() {
        List<Navigation> ngs = ns.findAll();
        assertTrue(ngs.size() > 0);
    }

    @Test
    public void testFindByLevel() {
        List<Navigation> ngs = ns.findByLevel(1);
        assertTrue(ngs.size() > 0);
    }

    @Test
    @Rollback(true)
    public void testDeleteNavigation() {
        List<Navigation> ngs = ns.findByLevel(1);
        ns.deleteNavigation(ngs.get(0));
        List<Navigation> ngsNew = ns.findByLevel(1);
        assertTrue(ngsNew.size() < ngs.size());
    }

    @Test
    public void testUpdateNavigation() {
        List<Navigation> ngs = ns.findByLevel(1);
        Navigation ng = ngs.get(0);
        ng.setUrl("http://www.sohu.com");
        ns.updateNavigation(ng);
        Navigation ngNew = ns.findById(ng.getId());
        assertTrue("http://www.sohu.com".equals(ngNew.getUrl()));
    }
    
    @Test
    @Rollback(true)
    public void testDeleteById(){
        List<Navigation> ngs = ns.findByLevel(1);
        ns.deleteById(ngs.get(0).getId());
        List<Navigation> ngsNew = ns.findByLevel(1);
        assertTrue(ngsNew.size() < ngs.size());
    }
    
    @Test
    public void testFindById(){
        List<Navigation> ngs = ns.findByLevel(1);
        Navigation ng = ns.findById(ngs.get(0).getId());
        assertTrue(ng != null);
    }
    
    @Test
    public void testFindByExample(){
        Navigation ng = new Navigation();
        ng.setName("会员管理");
        List<Navigation> ngs = ns.findByExample(ng);
        assertTrue(ngs.size() > 0);
    }
    
    @Test
    public void testUpdateStatus(){
        List<Navigation> ngs = ns.findByLevel(1);
        Navigation ng = ngs.get(0);
        ns.updateStatus(ng.getId(), "N");
        Navigation ngNew = ns.findById(ng.getId());
        assertTrue("N".equals(ngNew.getStatus()));
    }
    
    @Test
    public void updateSequence(){
        List<Navigation> ngs = ns.findByLevel(1);
        int[] ids = new int[ngs.size()];
        int[] numbers = new int[ngs.size()];
        for(int i = 0;i<ngs.size();i++){
            Navigation navigation = ngs.get(i);
            ids[i] = navigation.getId();
            numbers[i] = i;
        }
        ns.updateSequence(ids,numbers);
        List<Navigation> ngsNew = ns.findByLevel(1);
        assertTrue(ngsNew.get(0).getSequence() == 0 && ngsNew.get(1).getSequence() == 1);
    }
}
