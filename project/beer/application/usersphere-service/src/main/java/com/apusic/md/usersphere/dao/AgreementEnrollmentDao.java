package com.apusic.md.usersphere.dao;

import com.apusic.md.model.usersphere.AgreementType;
import com.apusic.md.model.usersphere.RegisterAgreement;

public interface AgreementEnrollmentDao {
	RegisterAgreement getRegisterAgreement(AgreementType type);
}
