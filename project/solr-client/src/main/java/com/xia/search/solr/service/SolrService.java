package com.xia.search.solr.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.xia.search.solr.query.Page;
import com.xia.search.solr.query.Query;
import com.xia.search.solr.query.Result;


//TODO 索引的操作：新增，更新，删除，查询4类
public interface SolrService {
	/**
	 * 根据ID去检索,存在时,返回结果,存在于solr 中的uuid的id
	 * 
	 * @param enitty
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public String getSolrId(Object enitty) throws SolrServerException, IOException;

	/**
	 * 新增或者更新索引
	 * @param entitys 不能是list,只能是数组,查询得有数据的实体
	 * @return
	 * @throws Exception
	 */
	public Result indexSaveOrUpdate(Object... entitys) throws Exception;
	/**
	 * 删除索引
	 * @param entitys 不能是list,只能是数组,查询得有数据的实体
	 * @return
	 * @throws Exception
	 */
	public Result indexDelete(Object... entitys) throws Exception;

	/**
	 * 更新schema.xml
	 * @param entitys
	 * @return
	 * @throws Exception
	 */
	public Result updateSchema(Object... entitys) throws Exception;

	/**
	 * 查询，返回的结果为实体
	 * @param q
	 * @return
	 * @throws Exception
	 */
	public <T> Page<T> query(Query q) throws Exception;
	public List<String> suggest(String q) throws Exception;
}
