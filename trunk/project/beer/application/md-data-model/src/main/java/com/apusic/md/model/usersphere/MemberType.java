package com.apusic.md.model.usersphere;

public enum MemberType {

	PERSONAL("个人用户"),SHOP("商城商户"), UNATHORIZED_SHOP("待审批"), CACELLED_SHOP("作废");

	private String type;

	private MemberType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
