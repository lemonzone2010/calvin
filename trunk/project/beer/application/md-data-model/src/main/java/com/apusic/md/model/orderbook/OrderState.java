package com.apusic.md.model.orderbook;

import java.util.Arrays;
import java.util.List;

public enum OrderState {

    SELLER_CONFIRM_ORDER("待卖家确认"), 
    AWAITING_PAYMENT("待付款"), 
    PAID("已付款"), 
    AWAITING_DELIVERY("待发货"), 
    AWAITING_TAKE_DELIVERY("待确认收货"),
    COMPLETED("已完成"),
    CANCELLED("已取消");

    private String state;
    
  //  private static List<SelectItem> selectItems = new ArrayList<SelectItem>();

    private OrderState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static OrderState toObject(String state) {
        if (OrderState.AWAITING_PAYMENT.toString().equals(state)) {
            return OrderState.AWAITING_PAYMENT;
        } else if (OrderState.PAID.toString().equals(state)) {
            return OrderState.PAID;
        } else if (OrderState.AWAITING_DELIVERY.toString().equals(state)) {
            return OrderState.AWAITING_DELIVERY;
        } else if (OrderState.SELLER_CONFIRM_ORDER.toString().equals(state)) {
            return OrderState.SELLER_CONFIRM_ORDER;
        } else if (OrderState.AWAITING_TAKE_DELIVERY.toString().equals(state)){
            return OrderState.AWAITING_TAKE_DELIVERY;
        }
        return OrderState.CANCELLED;
    }

    public static List<OrderState> getList() {
        return Arrays.asList(values());
    }

  /*  public static List<SelectItem> getSelectItem() {
        if(selectItems.size() == 0) {
            List<OrderState> states = getList();
            selectItems.add(new SelectItem("","订单状态"));
            for (OrderState state : states) {
                selectItems.add(new SelectItem(state.toString(), state.getState()));
            }
        }
        return selectItems;
    }*/
}
