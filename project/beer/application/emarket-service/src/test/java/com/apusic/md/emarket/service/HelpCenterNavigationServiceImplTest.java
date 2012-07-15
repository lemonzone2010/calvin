package com.apusic.md.emarket.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.md.model.emarket.HelpCenterNavigation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-datamanagement-service.xml",
		"classpath*:apusic-md-emarket-service.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-md-emarket-service-test.xml" })
public class HelpCenterNavigationServiceImplTest {

	@Autowired
	private HelpCenterNavigationService hcns;
	
	@Before
	public void setUp() throws Exception {
		HelpCenterNavigation hcn = new HelpCenterNavigation();
		hcn.setLevel(1);
		hcn.setName("测试1");
		hcn.setSerialNumber(1);
		hcns.create(hcn);
	}

	@Test
	public void create() {
		HelpCenterNavigation hcn = new HelpCenterNavigation();
		hcn.setLevel(1);
		hcn.setName("测试");
		hcn.setSerialNumber(1);
		hcns.create(hcn);
		HelpCenterNavigation example = new HelpCenterNavigation();
		example.setName("测试");
		List<HelpCenterNavigation> hcnlistNew = hcns.findByExample(hcn);
		Assert.assertEquals(hcnlistNew.size(), 1);
	}

	@Test
	public void deleteById() {
		HelpCenterNavigation hcn = new HelpCenterNavigation();
		hcn.setId(1);
		List<HelpCenterNavigation> hcnlist = hcns.findByExample(hcn);
		Assert.assertEquals(hcnlist.size(), 1);
		hcns.deleteById(1);
		List<HelpCenterNavigation> hcnlistNew = hcns.findByExample(hcn);
		Assert.assertEquals(hcnlistNew.size(), 0);
	}

	@Test
	public void findByExample() {
		HelpCenterNavigation hcn = new HelpCenterNavigation();
		hcn.setName("测试1");
		List<HelpCenterNavigation> hcnlist = hcns.findByExample(hcn);
		Assert.assertEquals(hcnlist.size(), 1);
	}

	@Test
	public void updateEntity() {
		HelpCenterNavigation hcn = new HelpCenterNavigation();
		hcn.setName("测试1");
		List<HelpCenterNavigation> hcnlist = hcns.findByExample(hcn);
		HelpCenterNavigation hcnNew = hcnlist.get(0);
		hcnNew.setLevel(10);
		hcns.updateEntity(hcnNew);
		HelpCenterNavigation hcn2 = hcns.findById(hcnNew.getId());
		Assert.assertEquals(hcn2.getLevel().intValue(), 10);
	}

	@Test
	public void updateSerialNumber() {
		HelpCenterNavigation hcn1 = new HelpCenterNavigation();
		hcn1.setLevel(1);
		hcn1.setName("测试2");
		hcn1.setSerialNumber(1);
		hcns.create(hcn1);
		HelpCenterNavigation hcn2 = new HelpCenterNavigation();
		hcn2.setLevel(1);
		hcn2.setName("测试3");
		hcn2.setSerialNumber(2);
		hcns.create(hcn2);
		List<HelpCenterNavigation> hcnlist = hcns.findByLevel(1);
		int[] ids = new int[hcnlist.size()];
		int[] numbers = new int[hcnlist.size()];
		for(int i = 0;i < hcnlist.size();i++){
			ids[i] = hcnlist.get(i).getId();
			numbers[i] = i;
		}
		hcns.updateSerialNumber(ids, numbers);
		List<HelpCenterNavigation> hcnlistNew = hcns.findByLevel(1);
		Assert.assertEquals(hcnlistNew.get(0).getSerialNumber().intValue(),0);
		Assert.assertEquals(hcnlistNew.get(1).getSerialNumber().intValue(),1);
		
	}

}
