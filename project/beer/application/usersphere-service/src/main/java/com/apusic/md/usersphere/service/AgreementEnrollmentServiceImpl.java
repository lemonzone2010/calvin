package com.apusic.md.usersphere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.md.model.usersphere.AgreementType;
import com.apusic.md.model.usersphere.RegisterAgreement;
import com.apusic.md.usersphere.dao.AgreementEnrollmentDao;

@Service("usersphere_AgreementEnrollmentService")
public class AgreementEnrollmentServiceImpl implements AgreementEnrollmentService {

	@Autowired
	private CrudService crudService;

	@Autowired
	private AgreementEnrollmentDao registerAgreementDao;

	@Transactional
	public void addRegisterAgreement(RegisterAgreement registerAgreement) {
		this.crudService.create(registerAgreement);
	}

	@Transactional
	public void deleteRegisterAgreement(int id) {
		this.crudService.delete(RegisterAgreement.class, id);
	}

	public RegisterAgreement getRegisterAgreement(int id) {
		return this.crudService.retrieve(RegisterAgreement.class, id);
	}

	public RegisterAgreement getRegisterAgreement(AgreementType type) {
		return this.registerAgreementDao.getRegisterAgreement(type);
	}

	@Transactional
	public void updateRegisterAgreement(RegisterAgreement registerAgreement) {
		this.crudService.update(registerAgreement);
	}

}
