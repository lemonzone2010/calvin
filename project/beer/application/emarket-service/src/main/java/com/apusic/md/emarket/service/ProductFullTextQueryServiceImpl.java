package com.apusic.md.emarket.service;

import java.util.List;

import org.apache.lucene.search.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.fulltextsearch.FullTextQueryParameter;
import com.apusic.ebiz.framework.core.fulltextsearch.FullTextQueryService;
import com.apusic.ebiz.framework.core.fulltextsearch.GroupByResult;
import com.apusic.ebiz.framework.core.fulltextsearch.filter.Filter;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductCategory;

@Service("emarket_ProductFullTextQueryService")
public class ProductFullTextQueryServiceImpl implements ProductFullTextQueryService{
 
	@Autowired
	private FullTextQueryService fullTextQueryService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Transactional
	public List<ProductCategory> fullTextQueryCategorys(String keyword) {
		ProductCategory example = new ProductCategory();
		example.setName(keyword);
		example.setLevel(3);
		return productCategoryService.findByExample(example );
		//FullTextQueryParameter<ProductCategory> query = new FullTextQueryParameter<ProductCategory>(ProductCategory.class,new String[]{keyword},new String[]{"name"});
		//return fullTextQueryService.find(query, 0,Integer.MAX_VALUE);
	}

	@Transactional
	public boolean keywordIsCategory(String keyword) {
		return this.fullTextQueryCategorys(keyword).size()>0?true:false;
	}

	@Transactional
	public long fullTextPrecisionQueryProductCount(String keyword,Filter filter,List<String[]> querys) {
		FullTextQueryParameter<Product> query = getQueryParameter(keyword,filter,querys);
		return this.fullTextQueryService.getRowCount(query);
	}

	@Transactional
	public List<Product> fullTextPrecisionQueryProduct(String keyword,SortField sortField,Filter filter,List<String[]> querys,int start, int size) {
		FullTextQueryParameter<Product> query = getQueryParameter(keyword,filter,querys);
		if(query!=null && sortField!=null){
			query.setSortFields(new SortField[]{sortField});
		}
		return fullTextQueryService.find(query, start, size);
	}

	@Transactional
	public List<Product> fullTextLikeQueryProduct(String keyword, SortField sortField, int start,
			int size) {
		FullTextQueryParameter<Product> query = new FullTextQueryParameter<Product>(Product.class,new String[]{keyword},new String[]{"fullText"});
		if(sortField!=null && sortField!=null){
			query.setSortFields(new SortField[]{sortField});
		}
		return fullTextQueryService.find(query, start, size);
	}
	
	@Transactional
	public GroupByResult<Product> fullTextLikeQueryProductGroupBy(String keyword, SortField sortField, int start,
			int size) {
		FullTextQueryParameter<Product> query = new FullTextQueryParameter<Product>(Product.class,new String[]{keyword},new String[]{"fullText"});
		if(sortField!=null && sortField!=null){
			query.setSortFields(new SortField[]{sortField});
		}
		return fullTextQueryService.findAndGroupBy(query,"category.id",start,size);
	}
	
	@Transactional
	public GroupByResult<Product>  fullTextLikeQueryWholeProductGroupBy(String keyword,
			SortField sortField, int start, int size,
			List<String[]> queryParams) {
		FullTextQueryParameter<Product> query = new FullTextQueryParameter<Product>(Product.class,new String[]{keyword},new String[]{"fullText"});
		query.setSpecial(queryParams);
		if(sortField!=null && sortField!=null){
			query.setSortFields(new SortField[]{sortField});
		}
		return fullTextQueryService.findAndGroupBy(query,"category.id",start,size);
	}
	
	@Transactional
	public List<Product> fullTextLikeQueryWholeProduct(String keyword,
			SortField sortField, int start, int size,
			List<String[]> queryParams) {
		FullTextQueryParameter<Product> query = new FullTextQueryParameter<Product>(Product.class,new String[]{keyword},new String[]{"fullText"});
		query.setSpecial(queryParams);
		if(sortField!=null && sortField!=null){
			query.setSortFields(new SortField[]{sortField});
		}
		return fullTextQueryService.find(query, start, size);
	}
	
	
	@Transactional
	public long fullTextLikeQueryProductCount(String keyword,
			List<String[]> queryParams) {
		FullTextQueryParameter<Product> query = new FullTextQueryParameter<Product>(Product.class,new String[]{keyword},new String[]{"fullText"});
		query.setSpecial(queryParams);	
		return  fullTextQueryService.getRowCount(query,queryParams);
	}
	@Transactional
	public long fullTextLikeQueryProductCount(String keyword) {
		return fullTextLikeQueryProductCount(keyword,null);
	}
	


	private FullTextQueryParameter<Product> getQueryParameter(String keyword,
			Filter filter, List<String[]> querys) {
		String [] params = null;
		String [] fields = null;

		if(querys!=null && querys.size() > 0){
			params = new String[querys.size() + 1];
			fields = new String[querys.size() + 1];
			for(int i = 0; i < querys.size(); i++){
				fields[i] = querys.get(i)[0];
				params[i] = querys.get(i)[1];
			}
			params[querys.size()] = keyword;
			fields[querys.size()] = "category.name";
		}else{
			params = new String[]{keyword};
			fields = new String []{"category.name"};
		}
		FullTextQueryParameter<Product> query = null;
		if(params!=null && params.length==1){
			query = new FullTextQueryParameter<Product>(Product.class,params,fields);
		}else if(params!=null && params.length > 1){
			query = new FullTextQueryParameter<Product>(Product.class,params,fields);
		}else{
			query = new FullTextQueryParameter<Product>(Product.class,params,fields);
		}
		if(query!=null && filter!=null){
			query.setFilters(new Filter[]{filter});
		}
		return query;
	}
	
}
