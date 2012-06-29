package com.apusic.md.model.emarket;

public enum ProductStateType {
	WAIT_PUBLISH("未发布"),PUBLISH("已发布"),CANCEL_PUBLISH("下架");

	private String type;

	private ProductStateType(String  type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
