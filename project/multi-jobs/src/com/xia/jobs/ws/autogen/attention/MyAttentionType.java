
package com.xia.jobs.ws.autogen.attention;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MyAttentionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MyAttentionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="smallViewUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="editViewUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bigViewUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dockIconUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MyAttentionType", propOrder = {
    "title",
    "smallViewUrl",
    "editViewUrl",
    "bigViewUrl",
    "dockIconUrl"
})
public class MyAttentionType {

    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    protected String smallViewUrl;
    @XmlElement(required = true)
    protected String editViewUrl;
    @XmlElement(required = true)
    protected String bigViewUrl;
    @XmlElement(required = true)
    protected String dockIconUrl;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the smallViewUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmallViewUrl() {
        return smallViewUrl;
    }

    /**
     * Sets the value of the smallViewUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmallViewUrl(String value) {
        this.smallViewUrl = value;
    }

    /**
     * Gets the value of the editViewUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditViewUrl() {
        return editViewUrl;
    }

    /**
     * Sets the value of the editViewUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditViewUrl(String value) {
        this.editViewUrl = value;
    }

    /**
     * Gets the value of the bigViewUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBigViewUrl() {
        return bigViewUrl;
    }

    /**
     * Sets the value of the bigViewUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBigViewUrl(String value) {
        this.bigViewUrl = value;
    }

    /**
     * Gets the value of the dockIconUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDockIconUrl() {
        return dockIconUrl;
    }

    /**
     * Sets the value of the dockIconUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDockIconUrl(String value) {
        this.dockIconUrl = value;
    }

}
