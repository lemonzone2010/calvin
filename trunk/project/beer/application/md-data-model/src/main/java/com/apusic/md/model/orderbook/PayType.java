package com.apusic.md.model.orderbook;

import org.apache.commons.lang.StringUtils;

public enum PayType {
    ALIPAY("支付宝"), BILLPAY("块钱"), CHINAPAY("银联");

    private String type;

    private PayType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static PayType toObject(String type) {
        if (StringUtils.equals(PayType.ALIPAY.toString(), type)) {
            return PayType.ALIPAY;
        } else if (StringUtils.equals(PayType.BILLPAY.toString(), type)) {
            return PayType.BILLPAY;
        }
        return PayType.CHINAPAY;

    }
}
