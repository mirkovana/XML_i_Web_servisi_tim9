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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Tadresa_organa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Tadresa_organa">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.projekat.org/obavestenje}Tadresa">
 *       &lt;sequence>
 *         &lt;element name="broj_kancelarije">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="1"/>
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
@XmlType(name = "Tadresa_organa", propOrder = {
    "brojKancelarije"
})
public class TadresaOrgana
    extends Tadresa
{

    @XmlElement(name = "broj_kancelarije")
    protected int brojKancelarije;

    /**
     * Gets the value of the brojKancelarije property.
     * 
     */
    public int getBrojKancelarije() {
        return brojKancelarije;
    }

    /**
     * Sets the value of the brojKancelarije property.
     * 
     */
    public void setBrojKancelarije(int value) {
        this.brojKancelarije = value;
    }

}