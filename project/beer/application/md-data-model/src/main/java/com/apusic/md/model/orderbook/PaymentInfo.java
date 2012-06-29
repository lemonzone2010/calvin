package com.apusic.md.model.orderbook;

import com.apusic.ebiz.model.BaseModel;

public class PaymentInfo extends BaseModel implements Cloneable{

    private static final long serialVersionUID = -4466554093743239504L;


    private Order order;

    private PayMode payMode;	//支付方式

    private Double amount;		//支付金额

    private PayType payType;


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PayMode getPayMode() {
        return payMode;
    }

    public void setPayMode(PayMode payMode) {
        this.payMode = payMode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public PaymentInfo copyPaymentInfo(){
    	PaymentInfo paymentInfo = new PaymentInfo();
    	paymentInfo.setAmount(amount);
    	paymentInfo.setOrder(order);
    	paymentInfo.setPayMode(payMode);
    	paymentInfo.setPayType(payType);
    	return paymentInfo;
    }
}
