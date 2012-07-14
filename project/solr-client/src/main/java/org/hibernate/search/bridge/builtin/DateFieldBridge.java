package org.hibernate.search.bridge.builtin;

import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;

public class DateFieldBridge implements TwoWayFieldBridge {
	public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
		if (value != null) {
			luceneOptions.addNumericFieldToDocument(name, value, document);
		}
	}

	public String objectToString(Object object) {
		return object.toString();
	}

	public Object get(String name, Document document) {
		return Integer.valueOf(document.getFieldable(name).stringValue());
	}
}
