package com.apusic.md.model.orderbook;

import org.apache.commons.lang.StringUtils;

public enum InvoiceType {
    PERSONAL("个人"),COMPANY("公司");
    
    private String type;
    
    private InvoiceType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    public static InvoiceType toInvoiceType(String invoiceType){
        if(StringUtils.equals(InvoiceType.PERSONAL.toString(),invoiceType)){
            return InvoiceType.PERSONAL;
        }
        return InvoiceType.COMPANY;
    }
}
