//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.17 at 03:47:53 PM CET 
//


package com.xml.project.model.notice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Tadresa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Tadresa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="naziv_ulice">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="broj_ulice" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tadresa", propOrder = {
    "nazivUlice",
    "brojUlice"
})
@XmlSeeAlso({
    TadresaOrgana.class,
    TadresaPodnosioca.class
})
public abstract class Tadresa {

    @XmlElement(name = "naziv_ulice", required = true)
    protected String nazivUlice;
    @XmlElement(name = "broj_ulice")
    protected String brojUlice;

    /**
     * Gets the value of the nazivUlice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazivUlice() {
        return nazivUlice;
    }

    /**
     * Sets the value of the nazivUlice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazivUlice(String value) {
        this.nazivUlice = value;
    }

    /**
     * Gets the value of the brojUlice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrojUlice() {
        return brojUlice;
    }

    /**
     * Sets the value of the brojUlice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrojUlice(String value) {
        this.brojUlice = value;
    }

}
