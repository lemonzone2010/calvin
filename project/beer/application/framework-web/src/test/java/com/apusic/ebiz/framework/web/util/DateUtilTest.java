package com.apusic.ebiz.framework.web.util;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class DateUtilTest {

    @Test
    public void parseDateTime() throws Exception{
        String time = "2011-10-08 12:39:56";
        Date date = DateUtil.parseDateTime(time);
        Assert.assertNotNull(date);
    }
}
