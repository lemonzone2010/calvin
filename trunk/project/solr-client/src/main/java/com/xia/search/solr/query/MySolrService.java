package com.xia.search.solr.query;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.xia.search.solr.Page;
import com.xia.search.solr.Query;

public interface MySolrService {
	/**
	 * 根据ID去检索,存在时,返回结果,存在于solr 中的uuid的id
	 * 
	 * @param enitty
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public Object getIdFromSolr(Object enitty) throws SolrServerException, IOException;

	/**
	 * 新增索引
	 * @param entitys 不能是list,只能是数组
	 * @return
	 * @throws Exception
	 */
	public Result update(Object... entitys) throws Exception;

	/**
	 * 更新schema.xml
	 * @param entitys
	 * @return
	 * @throws Exception
	 */
	public Result updateSchema(Object... entitys) throws Exception;

	public <T> Page<T> query(Query q) throws Exception;
}
