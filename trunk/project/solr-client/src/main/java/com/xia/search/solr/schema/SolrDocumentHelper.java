package com.xia.search.solr.schema;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.hibernate.Session;
import org.hibernate.search.SearchException;
import org.hibernate.search.bridge.spi.ConversionContext;
import org.hibernate.search.bridge.util.impl.ContextualExceptionBridgeHelper;
import org.hibernate.search.engine.impl.LuceneOptionsImpl;
import org.hibernate.search.engine.spi.AbstractDocumentBuilder;
import org.hibernate.search.engine.spi.DocumentBuilderContainedEntity;
import org.hibernate.search.engine.spi.DocumentBuilderIndexedEntity;
import org.hibernate.search.engine.spi.EntityIndexBinder;
import org.hibernate.search.engine.spi.SearchFactoryImplementor;
import org.hibernate.search.util.impl.ContextHelper;

@SuppressWarnings("deprecation")
public class SolrDocumentHelper {
	private static final Log logger = LogFactory.getLog(SolrDocumentHelper.class);
	private static Map<Class<?>,SolrSchemaDocument> cache=new ConcurrentHashMap<Class<?>, SolrSchemaDocument>();
	private Session session;
	private boolean canAddEmptyField=true;

	public SolrDocumentHelper(Session session) {
		this.session = session;
	}
	
	public SolrSchemaDocument getSchemaDocument(Object entity) {
		if (cache.get(entity.getClass()) != null) {
			return cache.get(entity.getClass());
		}
		SolrSchemaDocument document = getDocument(entity);
		cache.put(entity.getClass(), document);
		return document;
	}
	/**
	 * 根据hibernate search相关的api取得相应的实体的luence的field
	 * @param entity 定义了hsbernate search相关标签的实体
	 * @return
	 */
	public SolrSchemaDocument getDocument(Object entity) {
		SolrSchemaDocument document=new SolrSchemaDocument();
		try {
			document.setEntityName(entity.getClass().getSimpleName());
			ConversionContext conversionContext = new ContextualExceptionBridgeHelper();
			SearchFactoryImplementor searchFactoryImplementor = getSearchFactoryImplementor ();

			DocumentBuilderIndexedEntity<Object> entityBuilder = (DocumentBuilderIndexedEntity<Object>) getEntityBuilder(searchFactoryImplementor, entity.getClass());
			Map<String, String> fieldToAnalyzerMap = new HashMap<String, String>();
			if(canAddEmptyField) {
				LuceneOptionsImpl.canAddEmptyField=true;
			}
			Serializable id=null;
			try {
				id=(Serializable) PropertyUtils.getProperty(entity, "id");
			} catch (Exception e) {
				logger.error(e);
			}
			if(id==null) {
				id="";
			}
			Document doc = entityBuilder.getDocument(entity, id, fieldToAnalyzerMap,
					searchFactoryImplementor.getInstanceInitializer(), conversionContext);
			for (Fieldable field : doc.getFields()) {
				FieldAdaptor fieldAdaptor = new FieldAdaptor((Field)field);
				fieldAdaptor.setEntityName(document.getEntityName());
				document.add(fieldAdaptor);
			}
		}catch (Exception e) {
			//没有定义@indexed的实体，会跑错
		}
		finally {
			LuceneOptionsImpl.canAddEmptyField=false;
		}
		return document;		
	}

	private SearchFactoryImplementor getSearchFactoryImplementor () {
		return ContextHelper. getSearchFactory( session) ;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> AbstractDocumentBuilder<T> getEntityBuilder(SearchFactoryImplementor searchFactoryImplementor, Class<?> entityClass) {
		EntityIndexBinder entityIndexBinding = searchFactoryImplementor.getIndexBindingForEntity( entityClass );
		if ( entityIndexBinding == null ) {
			DocumentBuilderContainedEntity entityBuilder = searchFactoryImplementor.getDocumentBuilderContainedEntity(
					entityClass
					);
			if ( entityBuilder == null ) {
				// should never happen but better be safe than sorry
				throw new SearchException(
						"Unable to perform work. Entity Class is not @Indexed nor hosts @ContainedIn: " + entityClass
						);
			}
			else {
				return entityBuilder;
			}
		}
		else {
			return (AbstractDocumentBuilder<T>) entityIndexBinding.getDocumentBuilder();
		}
	}

	public boolean isCanAddEmptyField() {
		return canAddEmptyField;
	}

	public void setCanAddEmptyField(boolean canAddEmptyField) {
		this.canAddEmptyField = canAddEmptyField;
	}

}
