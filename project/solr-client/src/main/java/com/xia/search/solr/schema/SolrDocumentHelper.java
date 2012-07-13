package com.xia.search.solr.schema;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.reflection.XMember;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.search.SearchException;
import org.hibernate.search.analyzer.Discriminator;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.spi.ConversionContext;
import org.hibernate.search.bridge.util.impl.ContextualExceptionBridgeHelper;
import org.hibernate.search.engine.impl.LuceneOptionsImpl;
import org.hibernate.search.engine.spi.AbstractDocumentBuilder;
import org.hibernate.search.engine.spi.AbstractDocumentBuilder.PropertiesMetadata;
import org.hibernate.search.engine.spi.DocumentBuilderContainedEntity;
import org.hibernate.search.engine.spi.DocumentBuilderIndexedEntity;
import org.hibernate.search.engine.spi.EntityIndexBinder;
import org.hibernate.search.engine.spi.SearchFactoryImplementor;
import org.hibernate.search.spi.InstanceInitializer;
import org.hibernate.search.util.impl.ContextHelper;
import org.hibernate.search.util.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class SolrDocumentHelper {
	private static final Log logger = LogFactory.getLog(SolrDocumentHelper.class);
	private static Map<Class<?>,SolrSchemaDocument> cache=new ConcurrentHashMap<Class<?>, SolrSchemaDocument>();
	private SessionFactory sessionFactory;
	private boolean canAddEmptyField=true;

	public SolrDocumentHelper(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SolrSchemaDocument getSchemaDocument(Object entity) {
		if (cache.get(entity.getClass()) != null) {
			return cache.get(entity.getClass());
		}
		SolrSchemaDocument document = getDocument(entity,true);
		cache.put(entity.getClass(), document);
		return document;
	}
	public SolrSchemaDocument getDocument(Object entity) {
		return getDocument(entity, false);
	}
	/**
	 * 根据hibernate search相关的api取得相应的实体的luence的field
	 * @param entity 定义了hsbernate search相关标签的实体
	 * @return
	 */
	public SolrSchemaDocument getDocument(Object entity,boolean canAddEmptyField) {
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
			if (id == null) {
				id = new Integer(0);
			}
			Document doc = entityBuilder.getDocument(entity, id, fieldToAnalyzerMap,
					searchFactoryImplementor.getInstanceInitializer(), conversionContext);
			if(canAddEmptyField&&entityBuilder.getMetadata().embeddedGetters.size()>0) {
				Set<String> processedFieldNames = new HashSet<String>();
				buildDocumentEmbbedFields(entity, doc, entityBuilder.getMetadata(), fieldToAnalyzerMap, processedFieldNames, conversionContext, searchFactoryImplementor.getInstanceInitializer());
			}
			for (Fieldable field : doc.getFields()) {
				FieldAdaptor fieldAdaptor = new FieldAdaptor(field);
				fieldAdaptor.setEntityName(document.getEntityName());
				document.add(fieldAdaptor);
			}
		}catch (Exception e) {
			logger.error(e);
			//没有定义@indexed的实体，会跑错
		}
		finally {
			LuceneOptionsImpl.canAddEmptyField=false;
		}
		return document;		
	}

	private SearchFactoryImplementor getSearchFactoryImplementor () {
		return ContextHelper. getSearchFactoryBySFI((SessionFactoryImplementor) sessionFactory) ;
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
	protected LuceneOptions getFieldLuceneOptions(PropertiesMetadata propertiesMetadata,int i, Object value) {
		LuceneOptions options;
		options = new LuceneOptionsImpl(
				propertiesMetadata.fieldStore.get( i ),
				propertiesMetadata.fieldIndex.get( i ),
				propertiesMetadata.fieldTermVectors.get( i ),
				propertiesMetadata.fieldBoosts.get( i ) * propertiesMetadata.dynamicFieldBoosts.get( i ).defineBoost( value ),
				propertiesMetadata.fieldNullTokens.get( i ),
				propertiesMetadata.precisionSteps.get( i )
		);
		return options;
	}
	protected LuceneOptions getClassLuceneOptions(PropertiesMetadata propertiesMetadata,int i) {
		return new LuceneOptionsImpl(
				propertiesMetadata.classStores.get( i ),
				propertiesMetadata.classIndexes.get( i ),
				propertiesMetadata.classTermVectors.get( i ),
				propertiesMetadata.classBoosts.get( i )
		);
	}
	/**
	 * 处理set子集的
	 * @param instance
	 * @param doc
	 * @param propertiesMetadata
	 * @param fieldToAnalyzerMap
	 * @param processedFieldNames
	 * @param conversionContext
	 * @param objectInitializer
	 */
	private void buildDocumentEmbbedFields(Object instance,
			Document doc,
			PropertiesMetadata propertiesMetadata,
			Map<String, String> fieldToAnalyzerMap,
			Set<String> processedFieldNames,
			ConversionContext conversionContext,
			InstanceInitializer objectInitializer) {

		// needed for field access: I cannot work in the proxied version
		Object unproxiedInstance = unproxy( instance, objectInitializer );

		// process the class bridges
		for ( int i = 0; i < propertiesMetadata.classBridges.size(); i++ ) {
			FieldBridge fb = propertiesMetadata.classBridges.get( i );
			final String fieldName = propertiesMetadata.classNames.get( i );
			final FieldBridge oneWayConversionContext = conversionContext.oneWayConversionContext( fb );
			conversionContext.pushProperty( fieldName );
			try {
				oneWayConversionContext.set(
						fieldName, unproxiedInstance,
						doc, getClassLuceneOptions( propertiesMetadata,i )
						);
			}
			finally {
				conversionContext.popProperty();
			}
		}

		// process the indexed fields
		XMember previousMember = null;
		Object currentFieldValue = null;
		for ( int i = 0; i < propertiesMetadata.fieldNames.size(); i++ ) {
			XMember member = propertiesMetadata.fieldGetters.get( i );
			if ( previousMember != member ) {
				currentFieldValue = ReflectionHelper.getMemberValue( unproxiedInstance, member );
				previousMember = member;
				if ( member.isCollection() ) {
					if ( currentFieldValue instanceof Collection ) {
						objectInitializer.initializeCollection( (Collection) currentFieldValue );
					}
					else if ( currentFieldValue instanceof Map ) {
						objectInitializer.initializeMap( (Map) currentFieldValue );
					}
				}
			}

			final FieldBridge fieldBridge = propertiesMetadata.fieldBridges.get( i );
			final String fieldName = propertiesMetadata.fieldNames.get( i );
			if(doc.getFieldable(fieldName)!=null) {
				continue;
			}
			final FieldBridge oneWayConversionContext = conversionContext.oneWayConversionContext( fieldBridge );
			conversionContext.pushProperty( propertiesMetadata.fieldGetterNames.get( i ) );
			try {
				oneWayConversionContext.set(
						fieldName, currentFieldValue, doc,
						getFieldLuceneOptions(propertiesMetadata, i, currentFieldValue )
						);
			}
			finally {
				conversionContext.popProperty();
			}
		}

		// allow analyzer override for the fields added by the class and field bridges
		allowAnalyzerDiscriminatorOverride(
				doc, propertiesMetadata, fieldToAnalyzerMap, processedFieldNames, unproxiedInstance
				);

		// recursively process embedded objects
		for ( int i = 0; i < propertiesMetadata.embeddedGetters.size(); i++ ) {
			XMember member = propertiesMetadata.embeddedGetters.get( i );
			conversionContext.pushProperty( propertiesMetadata.embeddedFieldNames.get( i ) );
			try {
				//Object value = ReflectionHelper.getMemberValue( unproxiedInstance, member );
				//TODO handle boost at embedded level: already stored in propertiesMedatada.boost

				//if ( value == null ) {
				//	processEmbeddedNullValue( doc, propertiesMetadata, conversionContext, i, member );
				//	continue;
				//} 
					PropertiesMetadata embeddedMetadata = propertiesMetadata.embeddedPropertiesMetadata.get( i );
					try {
						Object newValue = Class.forName(member.getElementClass().getName()).newInstance();
						buildDocumentEmbbedFields(
								newValue,
								doc,
								embeddedMetadata,
								fieldToAnalyzerMap,
								processedFieldNames,
								conversionContext,
								objectInitializer
								);
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			}
			finally {
				conversionContext.popProperty();
			}
		}
	}

	private Object unproxy(Object instance, InstanceInitializer objectInitializer) {
		if ( instance == null )
			return null;

		return objectInitializer.unproxy( instance );
	}
	/**
	 * Allows a analyzer discriminator to override the analyzer used for any field in the Lucene document.
	 *
	 * @param doc The Lucene <code>Document</code> which shall be indexed.
	 * @param propertiesMetadata The metadata for the entity we currently add to the document.
	 * @param fieldToAnalyzerMap This map contains the actual override data. It is a map between document fields names and
	 * analyzer definition names. This map will be added to the <code>Work</code> instance and processed at actual indexing time.
	 * @param processedFieldNames A list of field names we have already processed.
	 * @param unproxiedInstance The entity we currently "add" to the document.
	 */
	private void allowAnalyzerDiscriminatorOverride(Document doc, PropertiesMetadata propertiesMetadata, Map<String, String> fieldToAnalyzerMap, Set<String> processedFieldNames, Object unproxiedInstance) {
		Discriminator discriminator = propertiesMetadata.discriminator;
		if ( discriminator == null ) {
			return;
		}

		Object value = null;
		if ( propertiesMetadata.discriminatorGetter != null ) {
			value = ReflectionHelper.getMemberValue( unproxiedInstance, propertiesMetadata.discriminatorGetter );
		}

		// now we give the discriminator the opportunity to specify a analyzer per field level
		for ( Object o : doc.getFields() ) {
			Fieldable field = (Fieldable) o;
			if ( !processedFieldNames.contains( field.name() ) ) {
				String analyzerName = discriminator.getAnalyzerDefinitionName( value, unproxiedInstance, field.name() );
				if ( analyzerName != null ) {
					fieldToAnalyzerMap.put( field.name(), analyzerName );
				}
				processedFieldNames.add( field.name() );
			}
		}
	}
}
