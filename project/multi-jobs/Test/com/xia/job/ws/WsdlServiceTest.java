package com.xia.job.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xia.jobs.ws.attention.MyAttentionItem;
import com.xia.jobs.ws.attention.MyAttentionItemType;
import com.xia.jobs.ws.autogen.attention.GetMyAttentionsRequest;
import com.xia.jobs.ws.autogen.attention.GetMyAttentionsResponse;
import com.xia.jobs.ws.autogen.attention.MyAttentionService;
import com.xia.jobs.ws.autogen.attention.MyAttentionServicePortType;
import com.xia.jobs.ws.autogen.attention.MyAttentionType;
import com.xia.jobs.ws.autogen.attention.ObjectFactory;

public class WsdlServiceTest {
	private final Logger logger = Logger.getLogger(getClass());

	public static void main(String[] args) {
		List<MyAttentionItem> myAttentionItems = new WsdlServiceTest().getMyAttentionItems(
				"http://localhost:6888/eacDemo/myAttentionService?wsdl", null);
		System.out.println(myAttentionItems);
	}

	public List<MyAttentionItem> getMyAttentionItems(String wsdlURLStr, String urlPrefix) {
		ObjectFactory f = new ObjectFactory();
		GetMyAttentionsRequest wsRequest = f.createGetMyAttentionsRequest();
		wsRequest.setUserId("");
		// String idcardnumber = userInfo.getIdcardNo();
		// idcardnumber = EACStringUtils.rightPad(idcardnumber, 20, "0");
		wsRequest.setIdcardNo("");
		wsRequest.setUsername("");
		// String userEmail = userInfo.getEmailAddress();
		wsRequest.setEmailAddress("");
		List<MyAttentionItem> items = new ArrayList<MyAttentionItem>();
		URL wsdlURL = null;
		try {
			wsdlURL = new URL(wsdlURLStr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// if(!exists(wsdlURL)) return items;
		MyAttentionService ss = new MyAttentionService(wsdlURL);
		MyAttentionServicePortType port = ss.getMyAttentionServiceSOAP();
		// configureTimeout(port);
		try {
			GetMyAttentionsResponse wsResponse = port.getMyAttentions(wsRequest);
			int result = wsResponse.getResult();
			if (result == 0) {
				for (MyAttentionType myAttention : wsResponse.getMyAttention()) {
					MyAttentionItem item = new MyAttentionItem();
					item.setId("eac_workbench::WSMyAttentionPortlet");
					item.setDisplayName(myAttention.getTitle());
					item.setType(MyAttentionItemType.WS_PORTLET);
					item.setSmallViewUrl(urlPrefix + myAttention.getSmallViewUrl());
					item.setEditViewUrl(urlPrefix + myAttention.getEditViewUrl());
					item.setBigViewUrl(urlPrefix + myAttention.getBigViewUrl());
					item.setDockIconUrl(urlPrefix + myAttention.getDockIconUrl());
					items.add(item);
				}
			} else if (result == 1) {
				logger.info("Retrieve MyAttention Items from :" + urlPrefix + " fails, no such a user: " + "");
			} else if (result == 2) {
				logger.info("Retrieve MyAttention Items from :" + urlPrefix + " fails, user :" + ""
						+ " do not have permission.");
			} else if (result == 3) {
				logger.info("Retrieve MyAttention Items from :" + urlPrefix + " fails, system error.");
			}
		} catch (Exception e) {
			logger.info("Retrieve MyAttention Items from :" + urlPrefix + " fails : ", e);
			return items;
		}
		return items;
	}

}
