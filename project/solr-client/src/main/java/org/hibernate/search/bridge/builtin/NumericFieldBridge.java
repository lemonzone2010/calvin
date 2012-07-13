package org.hibernate.search.bridge.builtin;

import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;
import org.hibernate.search.engine.impl.LuceneOptionsImpl;

/**
 * Bridge to index numeric values using a Trie structure (multiple terms
 * representing different precisions)
 * 
 * @author Gustavo Fernandes
 */
public abstract class NumericFieldBridge implements TwoWayFieldBridge {

	public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
		if (LuceneOptionsImpl.canAddEmptyField && value == null) {
			value = new Integer(0);
		}
		if (value != null) {
			luceneOptions.addNumericFieldToDocument(name, value, document);
		}
	}

	public String objectToString(Object object) {
		return object.toString();
	}

}
