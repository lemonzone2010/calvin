package com.apusic.md.model.orderbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.apusic.ebiz.model.BaseModel;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductStateType;
import com.apusic.md.model.emarket.ProductStockType;
import com.apusic.md.model.emarket.SaleType;

public class Order extends BaseModel {

    private static final long serialVersionUID = -1456849377397169029L;


    private String number;

    private OrderState state;

    private Date createTime;

    private String creator;

    private String seller;

    private double totalPrice;

    private Set<OrderItem> orderItems;

    private SaleType saleType;

    private long workflowId;

    private boolean appraise = false;   //是否评价

    private DeliveryInfo deliveryInfo; // 收货人信息

    private PaymentInfo paymentInfo; // 支付信息

    private OrderInvoice orderInvoice; // 发票信息

    private String remark;

    private Date startTime; // 查询传值用，不持久到数据库

    private Date endTime; // 查询传值用，不持久到数据库

    private TimeSelectEnum timeSelectEnum; // 查询传值用，不持久到数据库

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPriceStrategy(TotalPriceStrategy strategy) {
        this.totalPrice = strategy.getTotalPrice(this);
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addItem(OrderItem item) {
        if (this.orderItems == null) {
            this.orderItems = new HashSet<OrderItem>();
        }
        this.orderItems.add(item);
    }

    public void addItem(Set<OrderItem> items) {
        if (this.orderItems == null) {
            this.orderItems = new HashSet<OrderItem>();
        }
        this.orderItems.addAll(items);
    }

    public SaleType getSaleType() {
        return saleType;
    }

    public void setSaleType(SaleType saleType) {
        this.saleType = saleType;
    }

    public long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(long workflowId) {
        this.workflowId = workflowId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public OrderInvoice getOrderInvoice() {
        return orderInvoice;
    }

    public void setOrderInvoice(OrderInvoice orderInvoice) {
        this.orderInvoice = orderInvoice;
    }

    public TimeSelectEnum getTimeSelectEnum() {
        return timeSelectEnum;
    }

    public void setTimeSelectEnum(TimeSelectEnum timeSelectEnum) {
        this.timeSelectEnum = timeSelectEnum;
    }

    public void setOrderNumber(OrderNumberStrategy strategy){
        this.number = strategy.setNumber(id);
    }

    public boolean isAppraise() {
        return appraise;
    }

    public void setAppraise(boolean appraise) {
        this.appraise = appraise;
    }

    public void copyProperties(Order orderTemplet) {

    	 this.creator = orderTemplet.getCreator();
         this.createTime = orderTemplet.getCreateTime();
         this.remark = orderTemplet.getRemark();

         PaymentInfo paymentInfo = orderTemplet.getPaymentInfo().copyPaymentInfo();
         this.paymentInfo = paymentInfo;
         paymentInfo.setOrder(this);

    	 DeliveryInfo deliveryInfo = orderTemplet.getDeliveryInfo().copyDeliPaymentInfo();
    	 this.deliveryInfo = deliveryInfo;
    	 deliveryInfo.setOrder(this);

    	 if(orderTemplet.getOrderInvoice() != null){
    		 OrderInvoice orderInvoice = orderTemplet.getOrderInvoice().copyOrderInvoice();
        	 this.orderInvoice = orderInvoice;
        	 orderInvoice.setOrder(this);
    	 }
    }

    public List<Product> getAllProducts(){
    	List<Product> products = new ArrayList<Product>();
    	if(orderItems == null){
    		return products;
    	}
    	for(OrderItem orderItem : orderItems){
    		products.add(orderItem.getProduct());
    	}
    	return products;
    }

    public List<Product> getOffSaleProducts(){
    	List<Product> products = this.getAllProducts();
    	List<Product> offSaleProducts = new ArrayList<Product>();
    	for (Product product : products){
    		if (product.getState() == ProductStateType.CANCEL_PUBLISH){
    			offSaleProducts.add(product);
    		}
    	}
    	return offSaleProducts;
    }

    public List<Product> getOutOfStockProducts(){
    	List<Product> products = this.getAllProducts();
    	List<Product> outOfStockProducts = new ArrayList<Product>();
    	for (Product product : products){
    		if (product.getStock() == ProductStockType.OUT_OF_STOCK){
    			outOfStockProducts.add(product);
    		}
    	}
    	return outOfStockProducts;
    }

}
