package com.apusic.md.model.usersphere;

import com.apusic.ebiz.model.BaseModel;

public class RegisterAgreement extends BaseModel{


	private AgreementType type;

	private String agreementContent;


	public AgreementType getType() {
		return type;
	}

	public void setType(AgreementType type) {
		this.type = type;
	}

	public String getAgreementContent() {
		return agreementContent;
	}

	public void setAgreementContent(String agreementContent) {
		this.agreementContent = agreementContent;
	}
}
