package com.apusic.md.model.orderbook;

import java.util.Arrays;
import java.util.List;

public enum TimeSelectEnum {
    MONTH("近一个月订单"), MONTH_BEFORE("一个月前订单");

    private String label;
    
    //private static  List<SelectItem> selectItems = new ArrayList<SelectItem>();

    private TimeSelectEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static List<TimeSelectEnum> getList() {
        return Arrays.asList(values());
    }

   /* public static List<SelectItem> getSelectItem() {
        if(selectItems.size() == 0) {
            List<TimeSelectEnum> times = getList();
            selectItems.add(new SelectItem("","订单时间"));
            for (TimeSelectEnum time : times) {
                selectItems.add(new SelectItem(time.toString(), time.getLabel()));
            }
        }
        return selectItems;
    }*/
}
