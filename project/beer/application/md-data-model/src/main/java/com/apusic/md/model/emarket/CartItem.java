package com.apusic.md.model.emarket;

public class CartItem {

	private Product product;    // 商品

	private int quantity;       // 数量

	private String itemInfo;	//保存产品详细信息，例如：名称+颜色+尺码等等

	private String imagePath;   //图片地址

	private SaleType saleType;	//类型

	public CartItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}


	public CartItem(Product product, String itemInfo,int quantity) {
		this.product = product;
		this.quantity = quantity;
		this.itemInfo = itemInfo;
	}

	public CartItem(Product product, int quantity,String imagePath) {
		this.product = product;
		this.quantity = quantity;
		this.imagePath = imagePath;
	}

	public CartItem(Product product, int quantity,String imagePath,String itemInfo,SaleType saleType) {
		this.product = product;
		this.quantity = quantity;
		this.imagePath = imagePath;
		this.itemInfo = itemInfo;
		this.saleType = saleType;
	}

	public Product getProduct() {
		return product;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemInfo == null) ? 0 : itemInfo.hashCode());
		result = prime * result + ((product == null) ? 0 :  this.product.getId());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		if (itemInfo == null) {
			if (other.itemInfo != null)
				return false;
		} else if (!itemInfo.equals(other.itemInfo))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (product.getId()!=other.product.getId())
			return false;
		return true;
	}

	public String getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(String itemInfo) {
		this.itemInfo = itemInfo;
	}

	public SaleType getSaleType() {
		return saleType;
	}

	public void setSaleType(SaleType saleType) {
		this.saleType = saleType;
	}

	/**
	 * 总价格
	 * @return
	 */
	public double getTotalPrice() {
		if (this.saleType.equals(SaleType.RETAIL)) {
			return product.getRetailPrice() * quantity;
		}
		return product.getWholesalePrice() * quantity;
	}

	/**
	 * 商品价格
	 * @return
	 */
	public double getProductPrice() {
		if (this.saleType.equals(SaleType.RETAIL)) {
			return product.getRetailPrice();
		}
		return product.getWholesalePrice();
	}
}
