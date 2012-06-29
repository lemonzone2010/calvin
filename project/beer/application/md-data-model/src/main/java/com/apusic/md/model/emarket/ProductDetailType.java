package com.apusic.md.model.emarket;

public enum ProductDetailType {
	
	PRODUCT_DETAIL_TEMPLATE("商品详情模板"),PRODUCT_TEMPLATE("商品模板");
	
	private String type;
	
	private ProductDetailType(String type){
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}
}
