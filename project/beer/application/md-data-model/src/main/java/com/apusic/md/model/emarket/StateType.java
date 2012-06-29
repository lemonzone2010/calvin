package com.apusic.md.model.emarket;

public enum StateType {
	ENABLED("启动"),DISABLED("禁用");

	private String type;

	public String getType() {
        return type;
    }

    private StateType(String type) {
		this.type = type;
	}
    
    public static StateType toStateType(String type){
        if (StateType.DISABLED.toString().equals(type)){
            return StateType.DISABLED;
        }
        return StateType.ENABLED;
    }
}
