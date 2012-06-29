package com.apusic.md.model.orderbook;

import com.apusic.ebiz.model.BaseModel;

public class Invoice extends BaseModel{

    private static final long serialVersionUID = 394236737284525138L;


    private InvoiceType type;	//发票抬头类型

    private String head;		//公司名称

    private InvoiceContentType content;	//发票内容

    private String userName;


    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public InvoiceContentType getContent() {
        return content;
    }

    public void setContent(InvoiceContentType content) {
        this.content = content;
    }

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void updateInfo(Invoice invoice) {
		this.type = invoice.getType();
		this.head = invoice.getHead();
		this.content = invoice.getContent();
		this.userName = invoice.getUserName();
	}

	public void setInfoByOrderInvoice(OrderInvoice orderInvoice) {
		head = orderInvoice.getHead();
		content = orderInvoice.getContent();
		type = orderInvoice.getType();
	}

}
