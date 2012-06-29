package com.apusic.md.model.usersphere;

public enum AgreementType {
	PERSONAL("个人协议"),SHOP("商铺协议");

	private String type;

	private AgreementType(String type){
		this.type = type;
	}
}
