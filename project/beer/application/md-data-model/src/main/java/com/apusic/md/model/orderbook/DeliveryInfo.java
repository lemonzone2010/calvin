package com.apusic.md.model.orderbook;

import com.apusic.ebiz.model.BaseModel;
import com.apusic.md.model.usersphere.DeliveryAddr;

public class DeliveryInfo extends BaseModel implements Cloneable{

    private static final long serialVersionUID = -7085196589685010812L;


    private Order order;

    private DeliveryMode mode;		//配送模式

    private DeliveryTimeSlot timeSlot;	//配送时间

    private Double cost;				//运费

    private String receiver;			//收货人

    private String address;				//地址

    private String postcode;			//邮编

    private String mobile;				//电话

    private String phone;				//手机


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public DeliveryMode getMode() {
        return mode;
    }

    public void setMode(DeliveryMode mode) {
        this.mode = mode;
    }

    public DeliveryTimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(DeliveryTimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setInfoByDeliveryAddr(DeliveryAddr deliveryAddr){
    	this.address = deliveryAddr.getAddress();
    	this.receiver = deliveryAddr.getConsignee();
    	this.mobile = deliveryAddr.getMobile();
    	this.phone = deliveryAddr.getPhone();
    	this.postcode = deliveryAddr.getPostcode();
    }

    public DeliveryInfo copyDeliPaymentInfo(){
    	DeliveryInfo deliveryInfo = null ;
    	try {
    	    deliveryInfo =(DeliveryInfo)this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return deliveryInfo;
    }
}
