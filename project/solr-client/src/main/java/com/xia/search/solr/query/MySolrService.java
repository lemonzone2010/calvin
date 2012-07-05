package com.xia.search.solr.query;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

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

	public Result update(Object... entitys) throws Exception;

	public Result updateSchema(Object... entitys) throws Exception;

	public List<?> query(Query query) throws Exception;
}
