package com.apusic.ebiz.framework.web.util;

import junit.framework.Assert;

import org.junit.Test;

public class CommonUtilTest {

    @Test
    public void formatMoney(){
        Assert.assertEquals("26548.24", CommonUtil.formatMoney(26548.236));
        Assert.assertEquals("26548.2", CommonUtil.formatMoney(26548.2367,1));
        Assert.assertEquals("26548.237", CommonUtil.formatMoney(26548.2367,3));
        Assert.assertEquals("26548", CommonUtil.formatMoney(26548.2367,0));
        
    }
    
    @Test
    public void compareDouble(){
        double a = 6;
        double b = Double.valueOf("6.00");
        Assert.assertEquals(Double.compare(a, b),0);
    }
}
