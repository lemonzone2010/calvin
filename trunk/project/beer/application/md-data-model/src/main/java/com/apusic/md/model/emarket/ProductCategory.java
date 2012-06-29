package com.apusic.md.model.emarket;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Similarity;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenizerDef;
import org.wltea.analyzer.lucene.IKSimilarity;
import org.wltea.analyzer.solr.IKTokenizerFactory;

import com.apusic.ebiz.model.BaseModel;

/**
 * 商品类别
 * @author xuzhengping
 *
 */

@Indexed
@AnalyzerDef(name = "customanalyzerCategory",
		tokenizer = @TokenizerDef(factory = IKTokenizerFactory.class,
				params = { @Parameter(name = "isMaxWordLength", value = "false") }))
@Similarity(impl =IKSimilarity.class )
public class ProductCategory extends BaseModel {


	@IndexedEmbedded(depth=1)
	private ProductCategory parent;//父类别

	@Field(index = Index.YES, store = Store.NO, boost = @Boost(1.8f))
	private String name;//类别名称

	private Integer level;//级别

	@Field(index = Index.YES, store = Store.NO)
	private String key;//搜索使用的key

	private Integer serialNumber;//排序使用

	@Field(index = Index.YES,store=Store.NO)
	private StateType state;//是否启动

	@IndexedEmbedded(depth=1)
	private Set<Brand> brands;//该类别下的所有品牌

	@IndexedEmbedded
	private Set<CategoryAttr> categoryAttrs;//所有属性

	private Set<ProductCategory> childrens;//子节点

	public Set<CategoryAttr> getCategoryAttrs() {
		return categoryAttrs;
	}

	public void setCategoryAttrs(Set<CategoryAttr> categoryAttrs) {
		this.categoryAttrs = categoryAttrs;
	}

	public void addCategoryAttr(CategoryAttr attr){
		if(this.categoryAttrs==null){
			this.categoryAttrs = new HashSet<CategoryAttr>();
		}
		this.categoryAttrs.add(attr);
	}

	public void removeCategoryAttr(CategoryAttr attr){
		if(this.categoryAttrs==null){
			this.categoryAttrs = new HashSet<CategoryAttr>();
		}
		this.categoryAttrs.remove(attr);
	}

	public Set<Brand> getBrands() {
		return brands;
	}

	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}

	public void addBrand(Brand brand){
		if(this.brands==null){
			this.brands = new HashSet<Brand>();
		}
		this.brands.add(brand);
	}

	public void removeBrand(Brand brand){
		if(this.brands==null){
			this.brands = new HashSet<Brand>();
		}
		this.brands.remove(brand);
	}


	public ProductCategory getParent() {
		return parent;
	}

	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public StateType getState() {
		return state;
	}

	public void setState(StateType state) {
		this.state = state;
	}

    public Set<ProductCategory> getChildrens() {
        return childrens;
    }

    public void setChildrens(Set<ProductCategory> childrens) {
        this.childrens = childrens;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isLeafNode(){
        if(this.level == 3){
            return true;
        }
        return false;
    }
}
