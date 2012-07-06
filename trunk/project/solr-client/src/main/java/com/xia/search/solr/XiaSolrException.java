package com.xia.search.solr;

public class XiaSolrException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public XiaSolrException() {
		super();
	}

	public XiaSolrException(String message, Throwable cause) {
		super(message, cause);
	}

	public XiaSolrException(String message) {
		super(message);
	}

	public XiaSolrException(Throwable cause) {
		super(cause);
	}

}
