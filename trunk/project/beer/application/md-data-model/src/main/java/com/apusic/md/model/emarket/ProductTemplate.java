package com.apusic.md.model.emarket;

import java.util.Date;

import com.apusic.ebiz.model.BaseModel;
import com.apusic.ebiz.model.user.User;

public class ProductTemplate extends BaseModel{


	private String templateName;

	private int categoryId;

	private int brandId;

	private double wholesalePrice;//批发价

	private double retailPrice;//零售价

	private String code;//商品编码

	private Date createTime;//创建时间

	private String keywords;//关键字

	private String desc;

	private String introduction;//产品介绍

	private ProductStockType stock;

	private String customization;//自定义属性

	private String attribute;//类别属性

	private int wholeSaleThrehold;//批发起始量

	private SaleType	saleType;//批发

	private Float creditRaito;//零售

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public double getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public ProductStockType getStock() {
		return stock;
	}

	public void setStock(ProductStockType stock) {
		this.stock = stock;
	}

	public String getCustomization() {
		return customization;
	}

	public void setCustomization(String customization) {
		this.customization = customization;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public int getWholeSaleThrehold() {
		return wholeSaleThrehold;
	}

	public void setWholeSaleThrehold(int wholeSaleThrehold) {
		this.wholeSaleThrehold = wholeSaleThrehold;
	}

	public SaleType getSaleType() {
		return saleType;
	}

	public void setSaleType(SaleType saleType) {
		this.saleType = saleType;
	}

	public Float getCreditRaito() {
		return creditRaito;
	}

	public void setCreditRaito(Float creditRaito) {
		this.creditRaito = creditRaito;
	}

	public Product createProductByTemplate(User user){
		Product product = new Product();
		product.setUser(user);
		Brand  brand=new Brand();
        brand.setId(getBrandId());
        product.setBrand(brand);
        product.setAttribute(getAttribute());
        ProductCategory category = new ProductCategory();
        category.setId(getCategoryId());
        product.setCategory(category);
        product.setProductName(getTemplateName());
        product.setCreditRaito(getCreditRaito());
        product.setCustomization(getCustomization());
        product.setDesc(getDesc());
        product.setIntroduction(getIntroduction());
        product.setKeywords(getKeywords());
        product.setRetailPrice(getRetailPrice());
        product.setStock(getStock());
        product.setSaleType(getSaleType());
        product.setCode(getCode());
        product.setCreateTime(new Date());
        return product;
	}
}
