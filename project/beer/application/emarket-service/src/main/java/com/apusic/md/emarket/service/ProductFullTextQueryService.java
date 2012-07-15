package com.apusic.md.emarket.service;

import java.util.List;

import org.apache.lucene.search.SortField;

import com.apusic.ebiz.framework.core.fulltextsearch.GroupByResult;
import com.apusic.ebiz.framework.core.fulltextsearch.filter.Filter;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductCategory;

public interface ProductFullTextQueryService {

	List<ProductCategory> fullTextQueryCategorys(String keyword);

	List<Product> fullTextPrecisionQueryProduct(String keyword,SortField sortField,Filter filter,List<String[]> querys,int start, int size);

	List<Product> fullTextLikeQueryWholeProduct(String keyWord,SortField sortField, int firstIndex, int pageSize,List<String[]> queryParams);

	List<Product> fullTextLikeQueryProduct(String keyword,SortField sortField,int start, int size);

	long fullTextPrecisionQueryProductCount(String keyword,Filter filter,List<String[]> querys);

	long fullTextLikeQueryProductCount(String keyword);

	boolean keywordIsCategory(String keyword);

	long fullTextLikeQueryProductCount(String keyWord,List<String[]> queryParams);
	public GroupByResult<Product> fullTextLikeQueryProductGroupBy(String keyword, SortField sortField, int start,int size);
	public GroupByResult<Product>  fullTextLikeQueryWholeProductGroupBy(String keyword,SortField sortField, int start, int size,List<String[]> queryParams);
	

}
