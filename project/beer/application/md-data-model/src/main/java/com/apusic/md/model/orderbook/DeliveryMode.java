package com.apusic.md.model.orderbook;

public enum DeliveryMode {
    MD_EXPRESS("明大快递"),EMS("邮政快递");
    
    private String type;
    
    private DeliveryMode(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    public static DeliveryMode toObject(String type){
        if(DeliveryMode.MD_EXPRESS.toString().equals(type)){
            return DeliveryMode.MD_EXPRESS;
        }
        return DeliveryMode.EMS;
    }
}
