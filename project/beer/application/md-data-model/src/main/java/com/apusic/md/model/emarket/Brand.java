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
 * 品牌
 * @author xuzhengping
 *
 */

@Indexed
@AnalyzerDef(name = "customanalyzerBrand",
		tokenizer = @TokenizerDef(factory = IKTokenizerFactory.class,
									params = { @Parameter(name = "isMaxWordLength", value = "true") }))
@Similarity(impl =IKSimilarity.class )
public class Brand extends BaseModel {


	@Field(index = Index.YES, store = Store.NO, boost = @Boost(1.8f))
	private String name;

	private String logoPath;

	@Field(index = Index.YES, store = Store.NO)
	private StateType state;//启动，禁用

	private String url;//网店，或者搜索条件

	@IndexedEmbedded(depth=1)
	private Set<ProductCategory> categorys;

	public Set<ProductCategory> getCategorys() {
		return categorys;
	}

	public void setCategorys(Set<ProductCategory> categorys) {
		this.categorys = categorys;
	}

	public void addCategory(ProductCategory c){
		if(this.categorys==null){
			this.categorys = new HashSet<ProductCategory>();
		}
		this.categorys.add(c);
	}

	public void removeCategory(ProductCategory c){
		if(this.categorys==null){
			this.categorys = new HashSet<ProductCategory>();
		}
		this.categorys.remove(c);
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public StateType getState() {
		return state;
	}

	public void setState(StateType state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
