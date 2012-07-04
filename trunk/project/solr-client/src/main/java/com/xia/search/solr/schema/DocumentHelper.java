package com.xia.search.solr.schema;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;
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
public class DocumentHelper {
	private Session session;
	private boolean canAddEmptyField=true;

	public DocumentHelper(Session session) {
		this.session = session;
	}

	public static void updateSolrSchema(Configuration configuration, Session session) {
		Iterator<PersistentClass> classMappings = configuration.getClassMappings();
		while (classMappings.hasNext()) {
			PersistentClass persistentClass = classMappings.next();
			try {
				Class<?> clazz = Class.forName(persistentClass.getClassName());
				DocumentHelper documentHelper = new DocumentHelper(session);
				SolrUpdateSchemaHelper.updateSchema(documentHelper.getDocument(clazz.newInstance()));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
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
				e.printStackTrace();
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
	private static <T> AbstractDocumentBuilder<T> getEntityBuilder(SearchFactoryImplementor searchFactoryImplementor, Class<?> entityClass) {
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
