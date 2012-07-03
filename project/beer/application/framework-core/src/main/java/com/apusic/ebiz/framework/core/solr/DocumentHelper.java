package com.apusic.ebiz.framework.core.solr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.FieldInfo.IndexOptions;
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
public class DocumentHelper {
	private Session session;
	private boolean canAddEmptyField=true;

	public DocumentHelper(Session session) {
		this.session = session;
	}

	/**
	 * 根据hibernate search相关的api取得相应的实体的luence的field
	 * @param entity 定义了hsbernate search相关标签的实体
	 * @return
	 */
	public SolrDocument getDocument(Object entity) {
		ConversionContext conversionContext = new ContextualExceptionBridgeHelper();
		SearchFactoryImplementor searchFactoryImplementor = getSearchFactoryImplementor ();

		DocumentBuilderIndexedEntity<Object> entityBuilder = (DocumentBuilderIndexedEntity<Object>) getEntityBuilder(searchFactoryImplementor, entity.getClass());
		Map<String, String> fieldToAnalyzerMap = new HashMap<String, String>();
		;
		SolrDocument document=new SolrDocument();
		try {
			if(canAddEmptyField) {
				LuceneOptionsImpl.canAddEmptyField=true;
			}
			Document doc = entityBuilder.getDocument(entity, "", fieldToAnalyzerMap,
					searchFactoryImplementor.getInstanceInitializer(), conversionContext);
			for (Fieldable field : doc.getFields()) {
				document.add(new FieldAdaptor((Field)field));
			}
		} finally {
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
	public static class FieldAdaptor{
		Field field;

		public FieldAdaptor(Field field) {
			this.field = field;
		}

		public String getName() {
			return field.name();
		}

		public final boolean isStored() {
			return field.isStored();
		}

		public final boolean isIndexed() {
			return field.isIndexed();
		}

		public final boolean isTokenized() {
			return field.isTokenized();
		}

		public final boolean isTermVectorStored() {
			return field.isTermVectorStored();
		}

		public boolean isStoreOffsetWithTermVector() {
			return field.isStoreOffsetWithTermVector();
		}

		public IndexOptions getIndexOptions() {
			return field.getIndexOptions();
		}

		public boolean isLazy() {
			return field.isLazy();
		}


	}
	
	public final class SolrDocument {
	  List<FieldAdaptor> fields = new ArrayList<FieldAdaptor>();
	  public SolrDocument() {}

	  public final void add(FieldAdaptor field) {
	    fields.add(field);
	  }
	  
	 public FieldAdaptor getFieldable(String name) {
	   for (FieldAdaptor field : fields) {
	     if (field.getName().equals(name))
	       return field;
	   }
	   return null;
	 }


	  public final List<FieldAdaptor> getFields() {
	    return fields;
	  }

	  
	  @Override
	  public final String toString() {
	    StringBuilder buffer = new StringBuilder();
	    buffer.append("Document<");
	    for (int i = 0; i < fields.size(); i++) {
	    	FieldAdaptor field = fields.get(i);
	      buffer.append(field.toString());
	      if (i != fields.size()-1)
	        buffer.append(" ");
	    }
	    buffer.append(">");
	    return buffer.toString();
	  }
	}

}
