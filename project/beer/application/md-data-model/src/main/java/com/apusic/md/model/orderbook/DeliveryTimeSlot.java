package com.apusic.md.model.orderbook;

import org.apache.commons.lang.StringUtils;

public enum DeliveryTimeSlot {
    WORKDAY("工作时间"), HOLIDAY("节假日"), WEEKEND("周末");

    private String timeSlot;

    private DeliveryTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public static DeliveryTimeSlot toDeliveryTimeSlot(String timeSlot) {
        if (StringUtils.equals(DeliveryTimeSlot.WORKDAY.toString(), timeSlot)) {
            return DeliveryTimeSlot.WORKDAY;
        } else if (StringUtils.equals(DeliveryTimeSlot.HOLIDAY.toString(), timeSlot)) {
            return DeliveryTimeSlot.HOLIDAY;
        }
        return DeliveryTimeSlot.WEEKEND;
    }
}
