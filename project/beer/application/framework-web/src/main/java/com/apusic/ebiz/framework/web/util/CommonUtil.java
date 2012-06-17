package com.apusic.ebiz.framework.web.util;

import java.io.StringWriter;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;


public class CommonUtil {
    
    private final static int NUMBER_LEN = 2; 

    /**
     * 将String数组转换成int数组
     * @param values
     * @return
     * @author guguoqing
     */
    public static int[] strings2Ints(String[] values){
        int[] intValues = new int[values.length];
        if(values.length == 0){
            return intValues;
        }
        for(int i=0;i<values.length;i++){
            intValues[i] = Integer.valueOf(values[i]);
        }
        return intValues;
    }

    /**
     * 格式化金额，四舍五入保留小数点后两位
     * @param d1
     * @return
     */
    public static String formatMoney(double num) {
        return formatMoney(num, NUMBER_LEN);
    }
    /**
     * 格式化金额，根据传入格式进行四舍五入格式化,
     * @param d1
     * @param formater
     * @return
     */
    public static String formatMoney(double num, int len) {
        DecimalFormat format = null;
        if(len == 0){
            format = new DecimalFormat("##0");
        }else{
            StringWriter sw = new StringWriter();
            sw.append("##0.");
            for(int i = 0;i<len ;i++){
                sw.append("0");
            }
            format = new DecimalFormat(sw.toString());
        }
        return format.format(num);
    }
    
    public static String formatEmptyString(String value){
        if(StringUtils.isEmpty(value)){
            return "";
        }
        return value;
    }
}

