package com.apusic.ebiz.framework.web.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class DateUtil {

    private static final String STANDARD_FORMAT = "yyyy-MM-dd";
    private static final String PRECISE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(java.util.Date date) {
    	if(date == null){
    		return null;
    	}
    	return DateFormatUtils.format(date, STANDARD_FORMAT);
    }

    public static String formatTime(java.util.Date date) {
    	if(date == null){
    		return null;
    	}
    	return DateFormatUtils.format(date, PRECISE_FORMAT);
    }
    public static String format(java.util.Date date , String pattern) {
    	if(date == null){
    		return null;
    	}
    	return DateFormatUtils.format(date, pattern);
    }


    public static Date parseDate(String s) throws ParseException {
        return parse(s);
    }
    public static Date parseDateTime(String s) throws ParseException {
        return parseTime(s);
    }
    public static Date parse(String s) throws ParseException {
        if (StringUtils.isNotBlank(s)) {
            return new SimpleDateFormat(STANDARD_FORMAT).parse(s);
        }
        return null;
    }
    public static Date parseTime(String s) throws ParseException {
        if (StringUtils.isNotBlank(s)) {
            return new SimpleDateFormat(PRECISE_FORMAT).parse(s);
        }
        return null;
    }
}
