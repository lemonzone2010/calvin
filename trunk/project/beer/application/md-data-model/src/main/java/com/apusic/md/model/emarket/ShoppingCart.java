package com.apusic.md.model.emarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class ShoppingCart {

	private SaleType type;

	public ShoppingCart(SaleType type) {
		this.type = type;
	}

	private List<CartItem> items = new ArrayList<CartItem>();

	public void addToShoppingCart(Product product, int quantity, String imagePath, boolean isUpdate, String itemInfo) {
		if(items == null){
			items = new ArrayList<CartItem>();
		}
		for (CartItem item : items) {
			if (item.getProduct().getId() == product.getId()) {
				if(StringUtils.equals(item.getItemInfo(), itemInfo)){
					if (isUpdate) {
						item.setQuantity(quantity);
					} else {
						item.setQuantity(item.getQuantity() + quantity);
					}
					return;
				}
			}
		}
		items.add(new CartItem(product, quantity, imagePath, itemInfo, this.type));
	}

	public void decreaseToShoppingCart(Product product, int i,String itemInfo) {
		if(items == null){
			items = new ArrayList<CartItem>();
		}
		for (CartItem item : items) {
			if (item.getProduct().getId() == product.getId()) {
				if(StringUtils.equals(item.getItemInfo(), itemInfo)){
					item.setQuantity(item.getQuantity() - i);
					return;
				}
			}
		}
		items.add(new CartItem(product,itemInfo, i));
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void remove(String attribute,Product... products) {
		if(items == null){
			items = new ArrayList<CartItem>();
		}
		if (products == null || products.length == 0) {
			return;
		}
		for (Product product : products) {
			if (this.isProductInCart(product)) {
				for(int i=0;i<items.size();i++){
					if(StringUtils.equals(items.get(i).getItemInfo(), attribute)){
						items.remove(i);
					}
				}
			}
		}
	}

	private boolean isProductInCart(Product product) {
		for (CartItem item : items) {
			if (item.getProduct().getId() == product.getId()) {
				return true;
			}
		}
		return false;
	}

	public SaleType getType() {
		return type;
	}

	/**
	 * 总价
	 *
	 * @return
	 */
	public double getTotalPrice() {
		double totalPrice = 0;
		for (CartItem cartItem : items) {
			totalPrice += cartItem.getTotalPrice();
		}
		return totalPrice;
	}

	/**
	 * 按照商家划分订单项
	 *
	 * @return
	 */
	public Map<String, Set<CartItem>> getCartItemsMapBySeller() {
		Map<String, Set<CartItem>> itemsMap = new HashMap<String, Set<CartItem>>();
		for (CartItem cartItem : items) {
			String name = cartItem.getProduct().getUser().getName();
			if (itemsMap.containsKey(name)) {
				itemsMap.get(name).add(cartItem);
			} else {
				Set<CartItem> cartItems = new HashSet<CartItem>();
				cartItems.add(cartItem);
				itemsMap.put(name, cartItems);
			}
		}
		return itemsMap;
	}

	/**
	 * 看购物车中的商品是否支持发票
	 * @return
	 */
	public boolean getInvoiceInfo(){
		for(CartItem cartItem : items){
			ProductReceiptType productReceiptType = cartItem.getProduct().getReceipt();
			if(productReceiptType == ProductReceiptType.IN_RECEIPT){
				return true;		//提供发票
			}
		}
		return false;
	}
}
