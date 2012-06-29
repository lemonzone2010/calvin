package com.apusic.md.model.orderbook;

import java.util.Date;

import com.apusic.ebiz.model.BaseModel;

public class OrderPriceChange extends BaseModel{

    private static final long serialVersionUID = 7529366631131981486L;

    
    private Integer orderItemId;
    
    private double originalPrice;
    
    private double currentPrice;
    
    private String action;
    
    private String operator;
    
    private Date createTime;


    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
