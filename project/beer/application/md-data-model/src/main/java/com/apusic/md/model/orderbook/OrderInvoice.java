package com.apusic.md.model.orderbook;

import com.apusic.ebiz.model.BaseModel;

public class OrderInvoice extends BaseModel implements Cloneable{

    private static final long serialVersionUID = -76130133307261018L;


    private Order order;

    private InvoiceType type;

    private String head;

    private InvoiceContentType content;


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

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

    public OrderInvoice copyOrderInvoice(){
    	OrderInvoice orderInvoice = null ;
		try {
			orderInvoice =(OrderInvoice)this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return orderInvoice;
    }
}
