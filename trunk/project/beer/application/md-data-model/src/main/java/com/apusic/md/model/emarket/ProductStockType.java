package com.apusic.md.model.emarket;

public enum ProductStockType {
	IN_STOCK("有货"),OUT_OF_STOCK("缺货");

	private String type;

	private ProductStockType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}


}
