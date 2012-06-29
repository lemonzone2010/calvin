package com.apusic.md.usersphere.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.md.model.usersphere.AgreementType;
import com.apusic.md.model.usersphere.RegisterAgreement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-md-usersphere-service-test.xml" })
@Transactional
public class AgreementEnrollmentServicetest {

	@Autowired
	private AgreementEnrollmentService registerAgreementService;

	@Test
	public void addRegisterAgreement(){
		RegisterAgreement g = new RegisterAgreement();
		g.setAgreementContent("我是注册协议的内容");
		g.setType(AgreementType.SHOP);
		registerAgreementService.addRegisterAgreement(g);
		Assert.assertTrue(g.getId()>0);
	}

	@Test
	public void updateRegisterAgreement(){
		RegisterAgreement registerAgreement = registerAgreementService.getRegisterAgreement(1);
		registerAgreement.setAgreementContent("我是修改了注册协议的内容");
		registerAgreementService.updateRegisterAgreement(registerAgreement);
	}

	@Test
	public void deleteRegisterAgreement(){
		registerAgreementService.deleteRegisterAgreement(1);
	}
}
