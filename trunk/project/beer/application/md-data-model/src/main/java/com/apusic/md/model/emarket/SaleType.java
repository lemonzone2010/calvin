package com.apusic.md.model.emarket;

public enum SaleType {
    WHOLESALE("批发"),RETAIL("零售");
    
    private String type;
    
    public String getType() {
        return type;
    }

    private SaleType(String type){
        this.type = type;
    }
}
