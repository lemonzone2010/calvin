package com.apusic.md.model.orderbook;

public enum InvoiceContentType {

    DETAIL("明细"), OFFICE_SUPPLIES("办公用品"), COMPUTER_ACCESSORIES("电脑配件"), CONSUMABLES("耗材");

    private String type;

    private InvoiceContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static InvoiceContentType toObject(String type) {
        if (InvoiceContentType.DETAIL.toString().equals(type)) {
            return InvoiceContentType.DETAIL;
        } else if (InvoiceContentType.OFFICE_SUPPLIES.toString().equals(type)) {
            return InvoiceContentType.OFFICE_SUPPLIES;
        } else if (InvoiceContentType.COMPUTER_ACCESSORIES.toString().equals(type)) {
            return InvoiceContentType.COMPUTER_ACCESSORIES;
        }
        return InvoiceContentType.CONSUMABLES;
    }

}
