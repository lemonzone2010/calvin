package com.apusic.ebiz.framework.core.fulltextsearch;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.BooleanClause.Occur;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.search.FullTextFilter;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.engine.spi.FacetManager;
import org.hibernate.search.query.facet.FacetSortOrder;
import org.hibernate.search.query.facet.FacetingRequest;
import org.wltea.analyzer.lucene.IKQueryParser;

import com.apusic.ebiz.framework.core.dao.HibernateCallback;
import com.apusic.ebiz.framework.core.fulltextsearch.filter.Filter;
import com.apusic.ebiz.framework.core.mail.MimeMailBuilderImpl;

public abstract class AbstractFullTextQueryCallback<T> implements
		HibernateCallback {
	private static final Log logger = LogFactory.getLog(MimeMailBuilderImpl.class);

	private FullTextQueryParameter<T> param;
 
	public AbstractFullTextQueryCallback(FullTextQueryParameter<T> param) {
		this.param = param;
	}

	public Object doInHibernate(Session session) throws HibernateException,
			SQLException {
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		Class<T> clazz = param.getClazz();
		String[] fields = param.getFields();
		String[] matching = param.getMatching();
		List<String[]> special = param.getSpecial();
		if (fields.length != matching.length){
			throw new IllegalArgumentException("Error the field length is not identical to matching length");
		}

		if (fields == null || fields.length == 0){
			throw new IllegalArgumentException("Full text query error, you must specify field to search on");
		}

		if (matching == null || matching.length ==0){
			throw new IllegalArgumentException("Full text query error, you must specify keywords to search");
		}

		List<org.apache.lucene.search.Query> queries = null;
		queries = this.buildLuceneQuery(fullTextSession, clazz, fields, matching,special);
		
		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(clazz).get();

		BooleanJunction<BooleanJunction> booleanJunction = qb.bool();
		if (queries.isEmpty()) {
			return doInHibernate(null,null);
		}
		for (org.apache.lucene.search.Query query : queries){
			booleanJunction.must(query);
		}

		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(booleanJunction.createQuery(), clazz);
		SortField[] sortFields = param.getSortFields();
		if (sortFields != null && sortFields.length > 0) {
			fullTextQuery.setSort(new Sort(sortFields));
		}

		Filter[] filters = param.getFilters();
		if (filters !=null && filters.length >0){
			for (Filter filter : filters){
				String filterName = filter.getName();
				FullTextFilter fullTextFilter = fullTextQuery.enableFullTextFilter(filterName);
				Set<String> keys = filter.getParameterKeys();
				if (keys!=null && keys.size() > 0){
					for (String key : keys){
						fullTextFilter.setParameter(key, filter.get(key));
					}
				}
			}
		}
		
		if (StringUtils.isNotBlank(param.getFacetField())) {
			FacetingRequest labelFacetingRequest = qb.facet()
		    .name( "labelFaceting" )  
		    .onField( param.getFacetField())  
		    .discrete()  
		    .orderedBy( FacetSortOrder.COUNT_DESC )  
		    .includeZeroCounts( false )  
		    .maxFacetCount( 100)  
		    .createFacetingRequest();
			FacetManager facetManager = fullTextQuery.getFacetManager();  
			facetManager.enableFaceting( labelFacetingRequest );  
			return doInHibernate(fullTextQuery,facetManager);
		}
		return doInHibernate(fullTextQuery,null);
	}
 
	private List<org.apache.lucene.search.Query> buildLuceneQuery(FullTextSession fullTextSession, Class<T> clazz, String[] fields, String[] matching,List<String[]> special){
		Analyzer analyzer = fullTextSession.getSearchFactory().getAnalyzer(clazz.getAnnotation(AnalyzerDef.class).name());
		List<org.apache.lucene.search.Query> queries = new ArrayList<org.apache.lucene.search.Query>();
			IKQueryParser parser = new IKQueryParser();
			org.apache.lucene.search.Query query = null;
			try {
				//and查询
				if (special!=null&&CollectionUtils.isNotEmpty(special)) {
					for (String[] q : special) {
						org.apache.lucene.search.Query specialQuery = IKQueryParser.parse(q[0], q[1].toUpperCase());
						queries.add(specialQuery);
					}
				}
				//和上面 做and查询， 内部为or
				if (matching.length <= 1) {
					
					String[] onFields = null;
					onFields = fields[0].split(",");
					//分词 然后遍历分词，每个字段与这个词之间or逻辑， 然后再and
					List<String> words = analyze(matching[0], analyzer);
					if (words.isEmpty()) {
						return queries;
					}
					StringBuffer searchStr =  new StringBuffer("");
					Occur[] occurs = new Occur[fields.length];
					for (int i = 0; i < occurs.length; i++) {
						occurs[i] = Occur.SHOULD;
					}
					int j=0;
					for (String word : words) {
						if (j!=0) {
							searchStr.append("&&");
						}
						j++;
						searchStr.append("(");
						for (int i = 0; i < onFields.length; i++) {
							if (i!=0) {
								searchStr.append("||");
							}
							searchStr.append(onFields[i]+":'"+word+"'");
						}
						searchStr.append(")");
					}
					query= IKQueryParser.parse(searchStr.toString());
					queries.add(query);
				}else{
						for (int j = 0; j < fields.length; j++) {
							query = IKQueryParser.parse(fields[j], matching[j]);
							queries.add(query);
						}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
		
		return queries;
	}
	
	private String escape(String matching){
		String matchingStr = matching.toLowerCase();
		String escaped = MultiFieldQueryParser.escape(matchingStr);
		return escaped;
	}
	

	//分词
	public List<String> analyze(String text, Analyzer analyzer) throws IOException  {
		text = escape(text);
		TokenStream ts = analyzer.tokenStream("", new StringReader(text));
		CharTermAttribute termAtt = (CharTermAttribute) ts.getAttribute(CharTermAttribute.class);
		List<String> words = new ArrayList<String>(termAtt.length()); 
		while (ts.incrementToken()) {
			String token = new String(termAtt.buffer(), 0, termAtt.length());
			words.add(token);
		}
		return words;
	}
	
	
	protected abstract Object doInHibernate(FullTextQuery query, FacetManager facetManager);
}
