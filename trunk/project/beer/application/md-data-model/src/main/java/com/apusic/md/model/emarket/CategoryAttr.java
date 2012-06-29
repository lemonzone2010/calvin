package com.apusic.md.model.emarket;

import org.hibernate.search.annotations.AnalyzerDef;
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

@Indexed
@AnalyzerDef(name = "customanalyzerAttr",
		tokenizer = @TokenizerDef(factory = IKTokenizerFactory.class,
				params = { @Parameter(name = "isMaxWordLength", value = "false") }))
@Similarity(impl =IKSimilarity.class )
public class CategoryAttr extends BaseModel {


	@IndexedEmbedded(depth=1)
	private ProductCategory category;//类别

	@Field(index = Index.YES, store = Store.NO)
	private String name;

	@Field(index = Index.YES, store = Store.NO)
	private String key;

	@Field(index = Index.YES, store = Store.NO )
	private String value;//eg;网络：GSM|CDMA联通|3G电信|3G移动|3G双卡双模 。其中key是网络，后面的字符串就是value

	private int serialNumber;


	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
}
