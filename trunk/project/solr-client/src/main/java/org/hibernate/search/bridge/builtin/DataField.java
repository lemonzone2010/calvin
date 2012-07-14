package org.hibernate.search.bridge.builtin;

import java.io.Reader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.AbstractField;

public class DataField extends AbstractField {

	/**
	 * The value of the field as a String, or null. If null, the Reader value or
	 * binary value is used. Exactly one of stringValue(), readerValue(), and
	 * getBinaryValue() must be set.
	 */
	public String stringValue() {
		return fieldsData instanceof String ? (String) fieldsData : null;
	}

	/**
	 * The value of the field as a Reader, or null. If null, the String value or
	 * binary value is used. Exactly one of stringValue(), readerValue(), and
	 * getBinaryValue() must be set.
	 */
	public Reader readerValue() {
		return fieldsData instanceof Reader ? (Reader) fieldsData : null;
	}

	/**
	 * The TokesStream for this field to be used when indexing, or null. If
	 * null, the Reader value or String value is analyzed to produce the indexed
	 * tokens.
	 */
	public TokenStream tokenStreamValue() {
		return tokenStream;
	}

}
