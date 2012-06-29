package com.apusic.md.model.orderbook;

import org.apache.commons.lang.StringUtils;

public enum PayMode {
	OFFLINE("线下支付","即时到帐，支持绝大数银行借记卡及部分银行信用卡","查看银行及限额"),
	ONLINE("在线支付","送货上门后再收款，支持现金、POS机刷卡、支票支付","查看运费及配送范围");

	private String mode;	//支付方式

	private String remarks;	//备注

	private String operator;	//链接操作

	private PayMode(String mode,String remarks,String operator){
		this.mode = mode;
		this.remarks = remarks;
		this.operator = operator;
	}

	public String getMode(){
		return this.mode;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getOperator() {
		return operator;
	}

	public static PayMode toPayMode(String payMode){
		if (StringUtils.equals("ONLINE", payMode)){
			return PayMode.ONLINE;
		}
		return PayMode.OFFLINE;
	}

}
