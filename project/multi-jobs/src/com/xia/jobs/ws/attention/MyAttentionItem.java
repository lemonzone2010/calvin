package com.xia.jobs.ws.attention;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xia.jobs.Query;
import com.xia.jobs.WorkItem;
import com.xia.jobs.ws.autogen.attention.GetMyAttentionsRequest;
import com.xia.jobs.ws.autogen.attention.GetMyAttentionsResponse;
import com.xia.jobs.ws.autogen.attention.MyAttentionService;
import com.xia.jobs.ws.autogen.attention.MyAttentionServicePortType;
import com.xia.jobs.ws.autogen.attention.MyAttentionType;
import com.xia.jobs.ws.workitem.WsWorkItem;

public class MyAttentionItem implements WsWorkItem{
	private final static Logger logger = Logger.getLogger(MyAttentionItem.class);
	private String id;
	private MyAttentionItemType type;
	private String displayName;
	private String smallViewUrl;
	private String editViewUrl;
	private String bigViewUrl;
	private String dockIconUrl;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the type
	 */
	public MyAttentionItemType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(MyAttentionItemType type) {
		this.type = type;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the smallViewUrl
	 */
	public String getSmallViewUrl() {
		return smallViewUrl;
	}
	/**
	 * @param smallViewUrl the smallViewUrl to set
	 */
	public void setSmallViewUrl(String smallViewUrl) {
		this.smallViewUrl = smallViewUrl;
	}
	/**
	 * @return the editViewUrl
	 */
	public String getEditViewUrl() {
		return editViewUrl;
	}
	/**
	 * @param editViewUrl the editViewUrl to set
	 */
	public void setEditViewUrl(String editViewUrl) {
		this.editViewUrl = editViewUrl;
	}
	/**
	 * @return the bigViewUrl
	 */
	public String getBigViewUrl() {
		return bigViewUrl;
	}
	/**
	 * @param bigViewUrl the bigViewUrl to set
	 */
	public void setBigViewUrl(String bigViewUrl) {
		this.bigViewUrl = bigViewUrl;
	}
	/**
	 * @return the dockIconUrl
	 */
	public String getDockIconUrl() {
		return dockIconUrl;
	}
	/**
	 * @param dockIconUrl the dockIconUrl to set
	 */
	public void setDockIconUrl(String dockIconUrl) {
		this.dockIconUrl = dockIconUrl;
	}
	public WorkItem convert(Object responseOneData) {
		MyAttentionType response=(MyAttentionType) responseOneData;
		displayName=response.getTitle();
		dockIconUrl=response.getDockIconUrl();
		bigViewUrl=response.getBigViewUrl();
		return this;
	}
	public GetMyAttentionsRequest reverse2Params(Query query) {
		GetMyAttentionsRequest request = new GetMyAttentionsRequest();
		request.setIdcardNo(query.getIdCardNo());
		request.setUserId(query.getUserId());
		return request;
	}
	@Override
	public String toString() {
		return "MyAttentionItem [id=" + id + ", type=" + type + ", displayName=" + displayName + ", smallViewUrl="
				+ smallViewUrl + ", editViewUrl=" + editViewUrl + ", bigViewUrl=" + bigViewUrl + ", dockIconUrl="
				+ dockIconUrl + "]";
	}
	public List<WorkItem> request(URL wsdlURL, Object request) {
		GetMyAttentionsRequest wsRequest = (GetMyAttentionsRequest) request;

		List<WorkItem> items = new ArrayList<WorkItem>();
		try {
			MyAttentionServicePortType port = new MyAttentionService(wsdlURL).getMyAttentionServiceSOAP();
			GetMyAttentionsResponse wsResponse = port.getMyAttentions(wsRequest);
			int result = wsResponse.getResult();
			if (result == 0) {
				for (MyAttentionType myAttention : wsResponse.getMyAttention()) {
					items.add(new MyAttentionItem().convert(myAttention));
				}
			} else if (result == 1) {
				logger.error("Retrieve MyAttention Items from :" + wsdlURL + " fails, no such a user: " + "");
			} else if (result == 2) {
				logger.error("Retrieve MyAttention Items from :" + wsdlURL + " fails, user :" + ""
						+ " do not have permission.");
			} else if (result == 3) {
				logger.error("Retrieve MyAttention Items from :" + wsdlURL + " fails, system error.");
			}
		} catch (Exception e) {
			logger.error("Retrieve MyAttention Items from :" + wsdlURL + " fails : ", e);
		}
		return items;
	}
	
	
}
