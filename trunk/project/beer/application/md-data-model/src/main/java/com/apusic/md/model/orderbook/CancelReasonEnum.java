package com.apusic.md.model.orderbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum CancelReasonEnum {
    STOCKOUT("缺货"),OTHER("其他原因");
    
    private String label;
    
    
    private CancelReasonEnum(String label){
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }

    public static CancelReasonEnum toObject(String label) {
        if(CancelReasonEnum.STOCKOUT.toString().equals(label)){
            return CancelReasonEnum.STOCKOUT;
        }
        return null;
    }
    
/*    public static List<SelectItem> getSelectItems() {
        if(selectItems.size() == 0) {
            List<CancelReasonEnum> reasons = getList();
            for (CancelReasonEnum reason : reasons) {
                selectItems.add(new SelectItem(reason.toString(), reason.getLabel()));
            }
        }
        return selectItems;
    }*/

    public static List<CancelReasonEnum> getList() {
        return Arrays.asList(values());
    }
}
