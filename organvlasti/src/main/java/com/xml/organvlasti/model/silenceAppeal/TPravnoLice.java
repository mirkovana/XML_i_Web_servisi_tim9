//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.20 at 01:14:13 PM CET 
//


package com.xml.organvlasti.model.silenceAppeal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TPravno_lice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPravno_lice">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.projekat.org/zalbazbogcutanja}TLice">
 *       &lt;sequence>
 *         &lt;element name="naziv_poverenika" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="500"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPravno_lice", propOrder = {
    "nazivPoverenika"
})
public class TPravnoLice
    extends TLice
{

    @XmlElement(name = "naziv_poverenika")
    protected String nazivPoverenika;

    /**
     * Gets the value of the nazivPoverenika property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazivPoverenika() {
        return nazivPoverenika;
    }

    /**
     * Sets the value of the nazivPoverenika property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazivPoverenika(String value) {
        this.nazivPoverenika = value;
    }

}
