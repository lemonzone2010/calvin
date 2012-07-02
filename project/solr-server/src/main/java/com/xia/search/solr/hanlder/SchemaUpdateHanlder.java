package com.xia.search.solr.hanlder;

import javax.xml.stream.XMLInputFactory;

import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.XMLErrorLogger;
import org.apache.solr.handler.ContentStreamHandlerBase;
import org.apache.solr.handler.ContentStreamLoader;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.update.processor.UpdateRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaUpdateHanlder extends SchemaStreamHandlerBase{
	 public static Logger log = LoggerFactory.getLogger(SchemaUpdateHanlder.class);
	  private static final XMLErrorLogger xmllog = new XMLErrorLogger(log);

	 XMLInputFactory inputFactory;


	  @Override
	  public void init(NamedList args) {
	    super.init(args);

	    inputFactory = XMLInputFactory.newInstance();
	    try {
	      // The java 1.6 bundled stax parser (sjsxp) does not currently have a thread-safe
	      // XMLInputFactory, as that implementation tries to cache and reuse the
	      // XMLStreamReader.  Setting the parser-specific "reuse-instance" property to false
	      // prevents this.
	      // All other known open-source stax parsers (and the bea ref impl)
	      // have thread-safe factories.
	      inputFactory.setProperty("reuse-instance", Boolean.FALSE);
	    }
	    catch (IllegalArgumentException ex) {
	      // Other implementations will likely throw this exception since "reuse-instance"
	      // isimplementation specific.
	      log.debug("Unable to set the 'reuse-instance' property for the input chain: " + inputFactory);
	    }
	    inputFactory.setXMLReporter(xmllog);
	  }

	  @Override
	  protected ContentStreamLoader newLoader(SolrQueryRequest req, UpdateRequestProcessor processor) {
	    return new SchemaXMLLoader(processor, inputFactory);
	  }

	  

		@Override
		  public String getVersion() {
		    return "$Revision: 1.0 $";
		  }

		  @Override
		  public String getDescription() {
		    return "The standard Solr request handler";
		  }

		  @Override
		  public String getSourceId() {
		    return "$Id: StandardRequestHandler.java 1.0 2010-12-26 20:21:48Z xiayong $";
		  }

		  @Override
		  public String getSource() {
		    return "$URL: com.xia.search.solr.hanlder.SchemaUpdateHanlder $";
		  }
}
