package com.apusic.md.model.orderbook;

import com.apusic.ebiz.model.BaseModel;
import com.apusic.md.model.emarket.CartItem;
import com.apusic.md.model.emarket.Product;

public class OrderItem extends BaseModel {

    private static final long serialVersionUID = -2779628405300989644L;


	private Product product;

	private Order order;

	private double price;		//单价

	private int quantity;

	private String itemInfo;    //保存产品详细信息，例如：名称+颜色+尺码等等

	private String remark;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

    public void setOrderInfoByCartItem(CartItem cartItem){
    	this.product = cartItem.getProduct();
    	this.itemInfo = cartItem.getItemInfo();
    	this.quantity = cartItem.getQuantity();
    	this.price = cartItem.getProductPrice();	//获得产品单价
    }
}
