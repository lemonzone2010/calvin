package com.apusic.md.usersphere.dao;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.md.model.usersphere.AgreementType;
import com.apusic.md.model.usersphere.RegisterAgreement;
import com.apusic.md.usersphere.service.AgreementEnrollmentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-md-usersphere-service-test.xml" })
@Transactional
public class AgreementEnrollmentDaoTest {

	@Autowired
	private AgreementEnrollmentDao registerAgreementDao;

	@Autowired
	private AgreementEnrollmentService agreementService;

	@Test
	public void getRegisterAgreement() throws SQLException{
		RegisterAgreement r = new RegisterAgreement();
		r.setType(AgreementType.PERSONAL);
		r.setAgreementContent("我是个人协议内容");
		agreementService.addRegisterAgreement(r);
		RegisterAgreement registerAgreement = registerAgreementDao.getRegisterAgreement(AgreementType.PERSONAL);
		Assert.assertNotNull(registerAgreement);
	}
}
