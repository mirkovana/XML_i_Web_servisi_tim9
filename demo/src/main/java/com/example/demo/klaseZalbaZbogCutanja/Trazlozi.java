//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.12.02 at 05:08:40 PM CET 
//


package com.example.demo.klaseZalbaZbogCutanja;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Trazlozi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Trazlozi">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="nije_postupio" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="nije_postupio_u_celosti" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="nije_postupio_u_zakonskom_roku" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Trazlozi", propOrder = {
    "nijePostupio",
    "nijePostupioUCelosti",
    "nijePostupioUZakonskomRoku"
})
public class Trazlozi {

    @XmlElement(name = "nije_postupio")
    protected Object nijePostupio;
    @XmlElement(name = "nije_postupio_u_celosti")
    protected Object nijePostupioUCelosti;
    @XmlElement(name = "nije_postupio_u_zakonskom_roku")
    protected Object nijePostupioUZakonskomRoku;

    /**
     * Gets the value of the nijePostupio property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNijePostupio() {
        return nijePostupio;
    }

    /**
     * Sets the value of the nijePostupio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNijePostupio(Object value) {
        this.nijePostupio = value;
    }

    /**
     * Gets the value of the nijePostupioUCelosti property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNijePostupioUCelosti() {
        return nijePostupioUCelosti;
    }

    /**
     * Sets the value of the nijePostupioUCelosti property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNijePostupioUCelosti(Object value) {
        this.nijePostupioUCelosti = value;
    }

    /**
     * Gets the value of the nijePostupioUZakonskomRoku property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNijePostupioUZakonskomRoku() {
        return nijePostupioUZakonskomRoku;
    }

    /**
     * Sets the value of the nijePostupioUZakonskomRoku property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNijePostupioUZakonskomRoku(Object value) {
        this.nijePostupioUZakonskomRoku = value;
    }

}
