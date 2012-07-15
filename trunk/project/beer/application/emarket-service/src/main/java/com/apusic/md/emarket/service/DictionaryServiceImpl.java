package com.apusic.md.emarket.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wltea.analyzer.dic.Dictionary;

import com.apusic.ebiz.datamanagement.service.ConfigurationDataService;
import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.model.datamanagement.ConfigurationData;
import com.apusic.md.model.emarket.Brand;
import com.apusic.md.model.emarket.CategoryAttr;
import com.apusic.md.model.emarket.ProductCategory;

public class DictionaryServiceImpl implements DictionaryService {
	public static final Log logger = LogFactory.getLog(DictionaryServiceImpl.class);
	public static String LINE="\n";
	

	private ConfigurationDataService configurationDataService;
	private ProductCategoryService productCategoryService;
	private CategoryAttributeService categoryAttributeService;
	private BrandService brandService;
	
	private ConfigService configService;

	private String extWordsName;
	private String stopWordsName;
	
	public void reload() {
		loadDicts();
	}

	public void afterPropertiesSet() throws Exception {
		extWordsName = configService.getValueByKey("words.ext");
		stopWordsName = configService.getValueByKey("words.stop");
		loadDicts();
	}
	
	private void loadDicts(){
		if (logger.isDebugEnabled()) {
			logger.debug("####加载扩展分词词典");
		}
		//扩展词典
		List<ConfigurationData> datas = configurationDataService.getConfigurationDatasByCategoryName(extWordsName);
		for (ConfigurationData configurationData : datas) {
			List<String> wordList = splitWords(configurationData.getValue());
			Dictionary.loadExtendWords(wordList);
			if (logger.isDebugEnabled()) {
				logger.debug("####扩展词汇\n"+configurationData.getValue());
			}
		}
		//停用词词典
		datas = configurationDataService.getConfigurationDatasByCategoryName(stopWordsName);
		for (ConfigurationData configurationData : datas) {
			List<String> wordList = splitWords(configurationData.getValue());
			Dictionary.loadExtendStopWords(wordList);
			if (logger.isDebugEnabled()) {
				logger.debug("####停用词汇\n"+configurationData.getValue());
			}
		}
		
		//加载类型名称词典
		loadCategoryWords();
		//加载属性值词典
		loadCategoryAttributeWords() ;
		//加载品牌名称词典
		loadBrandWords();
	}

	private void loadCategoryWords() {
		if (logger.isDebugEnabled()) {
			logger.debug("####加载类型名称词典");
		}
		Dictionary.loadExtendWords(getCategoryNameList());
	}
	private void loadCategoryAttributeWords() {
		if (logger.isDebugEnabled()) {
			logger.debug("####加载属性值词典");
		}
		Dictionary.loadExtendWords(getCategoryAttrValueList());
	}
	private void loadBrandWords() {
		if (logger.isDebugEnabled()) {
			logger.debug("####加载品牌名称词典");
		}
		Dictionary.loadExtendWords(getBrandNameList());
		
	}
	private Collection<String> getCategoryNameList() {
		List<ProductCategory> categories = productCategoryService.findAll();
		List<String> categoryNames = new ArrayList<String>(categories.size());
		for (ProductCategory c : categories) {
			String name = c.getName().trim();
			
			categoryNames.add(c.getName());
		}
		return categoryNames;
	}

	private void splitAddWord(List<String> categoryNames, String name,String f) {
		if (name.indexOf(f)!=-1 ) {
			String[] names = name.split(f);
			for (String string : names) {
				categoryNames.add(string);
			}
		}
	}

	private Collection<String> getBrandNameList() {
		List<Brand> brands = brandService.findAll();
		List<String> brandNames = new ArrayList<String>(brands.size());
		for (Brand b : brands) {
			String name = b.getName().trim();
			//分割
//			splitAddWord(brandNames, name,"/");
//			splitAddWord(brandNames, name," ");
//			splitAddWord(brandNames, name,"\\");
//			splitAddWord(brandNames, name,",");
			brandNames.add(name);
		}
		return brandNames;
	}
	
	private Collection<String> getCategoryAttrValueList() {
		List<CategoryAttr> attrs = categoryAttributeService.findAll();
		List<String> attrValues = new ArrayList<String>(attrs.size());
		for (CategoryAttr c : attrs) {
			String[] values = StringUtils.split(c.getValue(),"|");
			for (String v : values) {
				attrValues.add(v);
			}
		}
		return attrValues;
	}

	
	private List<String> splitWords(String wordStr) {
		String[] words = wordStr.split(LINE);
		List<String> wordList = new ArrayList(words.length);
		CollectionUtils.addAll(wordList	, words);
		return wordList;
	}
	
	

	public String getExtWordsName() {
		return extWordsName;
	}

	public void setExtWordsName(String extWordsName) {
		this.extWordsName = extWordsName;
	}

	public String getStopWordsName() {
		return stopWordsName;
	}

	public void setStopWordsName(String stopWordsName) {
		this.stopWordsName = stopWordsName;
	}

	public void setConfigurationDataService(
			ConfigurationDataService configurationDataService) {
		this.configurationDataService = configurationDataService;
	}

	public void setProductCategoryService(
			ProductCategoryService productCategoryService) {
		this.productCategoryService = productCategoryService;
	}

	public void setCategoryAttributeService(
			CategoryAttributeService categoryAttributeService) {
		this.categoryAttributeService = categoryAttributeService;
	}

	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	
}
