package com.apusic.md.model.emarket;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Similarity;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenizerDef;
import org.wltea.analyzer.lucene.IKSimilarity;
import org.wltea.analyzer.solr.IKTokenizerFactory;

import com.apusic.ebiz.framework.core.fulltextsearch.filter.DoubleRangeFilterFactory;
import com.apusic.ebiz.model.BaseModel;
import com.apusic.ebiz.model.user.User;

@Indexed
@FullTextFilterDef(name = "retailPriceFilter", impl = DoubleRangeFilterFactory.class)
@AnalyzerDef(
		name = "customanalyzer",
		tokenizer = @TokenizerDef(
				factory = IKTokenizerFactory.class, 
				params = { @Parameter(name = "isMaxWordLength", value = "false") }))
@Similarity(impl =IKSimilarity.class )
public class Product extends BaseModel {


	@Field(index = Index.YES, store = Store.NO,boost = @Boost(2.0f) )
	private String productName;

	@IndexedEmbedded(prefix="category.",depth=1)
	private ProductCategory category;//类别

	@IndexedEmbedded(prefix="brand.",depth=1)
	private Brand brand;//品牌

	@Field(index = Index.YES, store = Store.NO)
	@NumericField
	private double wholesalePrice;//批发价

	@Field(index = Index.YES, store = Store.NO)
	@NumericField
	private double retailPrice;//零售价

	private String code;//商品编码

	@Field(index = Index.YES, store = Store.NO)
	@DateBridge(resolution = Resolution.DAY)
	private Date publishedTime;//发布时间

	private Date createTime;//创建时间


	@Field(index = Index.YES, store = Store.NO)
	private String keywords;//关键字

	@Field(index = Index.YES, store = Store.NO)
	private String desc;

	@Field(index = Index.YES, store = Store.NO )
	private String introduction;//产品介绍

	private ProductStateType state;  //已发布/未发布/下架

	@Field(index = Index.YES, store = Store.NO)
	private ProductStockType stock;  //有货/缺货

	@Field(index=Index.NO,store=Store.NO)
	private ProductReceiptType receipt; //发票

    @Field(index = Index.YES, store = Store.NO,boost = @Boost(1.5f))
	private String customization;//自定义属性

	@Field(index = Index.YES, store = Store.NO,boost = @Boost(1.5f))
	private String attribute;//类别属性

	@Field(index = Index.YES, store = Store.NO)
	private int wholeSaleThrehold;//批发起始量

	@Field(index = Index.YES, store = Store.NO)
	private SaleType	saleType;//批发

	private Float creditRaito;//零售

	private Set<ProductImage> images;

	private User user;
	
	@Field(index = Index.YES, store = Store.NO)
	private String getFullText(){
		String parentCateName = category.getParent()!=null?category.getParent().getName():"";
		return productName+" " 
			+ brand.getName() +" "
			+ category.getName() +" "
			+ parentCateName +" "
			+ keywords +" "
			+ attribute +" "
			+ customization +" "
			+ desc ;
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

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}

	public void addImage(ProductImage p){
		if(this.images==null){
			this.images = new HashSet<ProductImage>();
		}
		this.images.add(p);
	}

	public void removeImage(ProductImage p){
		if(this.images==null){
			this.images = new HashSet<ProductImage>();
		}
		this.images.remove(p);
	}


	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
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

	public Date getPublishedTime() {
		return publishedTime;
	}

	public void setPublishedTime(Date publishedTime) {
		this.publishedTime = publishedTime;
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

	public ProductStateType getState() {
		return state;
	}

	public void setState(ProductStateType state) {
		this.state = state;
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

    public ProductReceiptType getReceipt() {
        return receipt;
    }

    public void setReceipt(ProductReceiptType receipt) {
        this.receipt = receipt;
    }
	public ProductTemplate createTemplate(User user) {
		ProductTemplate productTemplate = new ProductTemplate();
		productTemplate.setId(getId());
		productTemplate.setBrandId(getBrand().getId());
		productTemplate.setCategoryId(getCategory().getId());
		productTemplate.setCode(getCode());
		productTemplate.setCreateTime(new Date());
		productTemplate.setCreditRaito(getCreditRaito());
		productTemplate.setCustomization(getCustomization());
		productTemplate.setAttribute(getAttribute());
		productTemplate.setDesc(getDesc());
		productTemplate.setIntroduction(getIntroduction());
		productTemplate.setKeywords(getKeywords());
		productTemplate.setRetailPrice(getRetailPrice());
		productTemplate.setWholesalePrice(getWholesalePrice());
		productTemplate.setWholeSaleThrehold(getWholeSaleThrehold());
		productTemplate.setSaleType(getSaleType());
		productTemplate.setStock(getStock());
		productTemplate.setRetailPrice(getRetailPrice());
		productTemplate.setTemplateName(getProductName());
		productTemplate.setUser(user);
		return productTemplate;
	}
}
