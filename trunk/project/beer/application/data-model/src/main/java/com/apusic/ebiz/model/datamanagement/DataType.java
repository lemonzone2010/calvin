package com.apusic.ebiz.model.datamanagement;

public enum DataType {
	PLAINTEXT("纯文本"),RICHTEXT("富文本");

	private String type;

	private DataType(String type){
		this.type = type;
	}
	public String getType(){
	    return type;
	}
}
