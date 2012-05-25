
package com.xia.jobs.ws.autogen.attention;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.eac.work.myattentionservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.eac.work.myattentionservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMyAttentionsRequest }
     * 
     */
    public GetMyAttentionsRequest createGetMyAttentionsRequest() {
        return new GetMyAttentionsRequest();
    }

    /**
     * Create an instance of {@link GetMyAttentionsResponse }
     * 
     */
    public GetMyAttentionsResponse createGetMyAttentionsResponse() {
        return new GetMyAttentionsResponse();
    }

    /**
     * Create an instance of {@link MyAttentionType }
     * 
     */
    public MyAttentionType createMyAttentionType() {
        return new MyAttentionType();
    }

}
