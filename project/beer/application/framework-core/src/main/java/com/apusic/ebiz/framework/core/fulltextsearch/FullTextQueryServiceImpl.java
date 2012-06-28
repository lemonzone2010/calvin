package com.apusic.ebiz.framework.core.fulltextsearch;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.HibernateTemplate;
import com.apusic.ebiz.framework.core.dao.Page;

@Repository("ebiz_FullTextQueryService")
public class FullTextQueryServiceImpl implements FullTextQueryService {

	private HibernateTemplate hibernateTemplate;

	@Autowired
	public FullTextQueryServiceImpl(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public <T> List<T> find(final FullTextQueryParameter<T> param,
			final int start, final int size) {
		SearchCallback<T> callback = new SearchCallback<T>(param);
		callback.setFirstResult(start);
		callback.setMaxResults(size);
		List<T> result = hibernateTemplate.executeFind(callback);
		return result;
	}

	public <T> Page<T> find(final FullTextQueryParameter<T> param,
			final int size) {
		SearchCallback<T> callback = new SearchCallback<T>(param);
		List objects = hibernateTemplate.executeFind(callback);

		long rowCnt = getRowCount(param);

		Page<T> page = new Page<T>();
		page.setRows(objects);
		page.setPageSize(size);
		page.setTotal(rowCnt);
		return page;
	}
	
	public <T> GroupByResult<T> findAndGroupBy(  
			FullTextQueryParameter<T> param,String field,final int start, final int size) {
		param.setFacetField(field);
		SearchGroupByCountCallback<T> callback = new SearchGroupByCountCallback<T>(param); 
		callback.setFirstResult(start);
		callback.setMaxResults(size);
		Page page = (Page) hibernateTemplate.execute(callback);
		return new GroupByResult<T>(page, callback.getFacets());
	}

	public <T> long getRowCount(FullTextQueryParameter<T> param) {
		return getRowCount(param,null);
	}

	public <T> long getRowCount(FullTextQueryParameter<T> param,
			List<String[]> special) {
		SearchResultSizeCallback<T> resultSizeCallback = new SearchResultSizeCallback<T>(
				param);
		if (special!=null&&CollectionUtils.isNotEmpty(special)) {
			param.setSpecial(special);
		}
		long rowCnt = (Integer) hibernateTemplate.execute(resultSizeCallback);
		return rowCnt;
	}







	





}
