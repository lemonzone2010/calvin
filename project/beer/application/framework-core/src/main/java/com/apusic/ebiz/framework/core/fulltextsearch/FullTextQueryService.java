package com.apusic.ebiz.framework.core.fulltextsearch;

import java.util.List;

import com.apusic.ebiz.framework.core.dao.Page;

public interface FullTextQueryService {
	<T> List<T> find(final FullTextQueryParameter<T> param, final int start, final int size);

	<T> Page<T> find(final FullTextQueryParameter<T> param, final int size);

	<T> long getRowCount(FullTextQueryParameter<T> param);

	<T> long getRowCount(FullTextQueryParameter<T> param,List<String[]> queryParams);
	
	<T> GroupByResult<T> findAndGroupBy(FullTextQueryParameter<T> param,String field,final int start, final int size);
}  
