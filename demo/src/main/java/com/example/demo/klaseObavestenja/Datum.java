//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.30 at 04:19:07 PM CET 
//


package com.example.demo.klaseObavestenja;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dan" type="{http://www.w3.org/2001/XMLSchema}gDay"/>
 *         &lt;element name="mesec" type="{http://www.w3.org/2001/XMLSchema}gMonth"/>
 *         &lt;element name="godina" type="{http://www.w3.org/2001/XMLSchema}gYear"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dan",
    "mesec",
    "godina"
})
@XmlRootElement(name = "datum")
public class Datum {

    @XmlElement(required = true)
    @XmlSchemaType(name = "gDay")
    protected XMLGregorianCalendar dan;
    @XmlElement(required = true)
    @XmlSchemaType(name = "gMonth")
    protected XMLGregorianCalendar mesec;
    @XmlElement(required = true)
    @XmlSchemaType(name = "gYear")
    protected XMLGregorianCalendar godina;

    /**
     * Gets the value of the dan property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDan() {
        return dan;
    }

    /**
     * Sets the value of the dan property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDan(XMLGregorianCalendar value) {
        this.dan = value;
    }

    /**
     * Gets the value of the mesec property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMesec() {
        return mesec;
    }

    /**
     * Sets the value of the mesec property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMesec(XMLGregorianCalendar value) {
        this.mesec = value;
    }

    /**
     * Gets the value of the godina property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGodina() {
        return godina;
    }

    /**
     * Sets the value of the godina property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGodina(XMLGregorianCalendar value) {
        this.godina = value;
    }

}
