package com.apusic.md.model.orderbook;

import com.apusic.ebiz.model.BaseModel;

public class PayAndDelivery extends BaseModel{

    private static final long serialVersionUID = 7514839672885400327L;


    private PayMode payMode;

    private DeliveryMode deliveryMode;

    private DeliveryTimeSlot deliveryTime;

    private String userName;


    public PayMode getPayMode() {
        return payMode;
    }

    public void setPayMode(PayMode payMode) {
        this.payMode = payMode;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public DeliveryTimeSlot getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(DeliveryTimeSlot deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void updateInfo(PayAndDelivery newPayAndDelivery) {
		this.deliveryMode = newPayAndDelivery.getDeliveryMode();
		this.deliveryTime = newPayAndDelivery.getDeliveryTime();
		this.payMode = newPayAndDelivery.getPayMode();
		this.userName = newPayAndDelivery.getUserName();
	}
}
