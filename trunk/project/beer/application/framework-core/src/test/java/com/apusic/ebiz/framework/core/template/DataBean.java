package com.apusic.ebiz.framework.core.template;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service("test_DataBean")
public class DataBean {
	public String getValue(String key) {
		if (StringUtils.equals("mail", key)){
			return "<html><body><h3>Hi ${user.userName}, welcome to the Chipping Sodbury On-the-Hill message boards!</h3><div></div></body></html>";
		}
		return null;
	}
}
