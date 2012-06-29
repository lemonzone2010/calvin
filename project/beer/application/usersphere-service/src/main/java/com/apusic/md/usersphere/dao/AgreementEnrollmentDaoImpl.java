package com.apusic.md.usersphere.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.usersphere.AgreementType;
import com.apusic.md.model.usersphere.RegisterAgreement;

@Repository("usersphere_AgreementEnrollmentDao")
public class AgreementEnrollmentDaoImpl implements AgreementEnrollmentDao {

	@Autowired
	private QueryService queryService;

	public RegisterAgreement getRegisterAgreement(AgreementType type) {
		RegisterAgreement rg = new RegisterAgreement();
		rg.setType(type);
		List<RegisterAgreement> findByExample = this.queryService.findByExample(rg);
		if(findByExample!=null && findByExample.size() > 0){
			return findByExample.get(0);
		}
		return null;
	}
}
