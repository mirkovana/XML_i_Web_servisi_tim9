//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.20 at 01:14:13 PM CET 
//


package com.xml.project.model.silenceAppeal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TZakon complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TZakon">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clan" type="{http://www.projekat.org/zalbazbogcutanja}TClan" minOccurs="0"/>
 *         &lt;element name="stav" type="{http://www.projekat.org/zalbazbogcutanja}TStav" minOccurs="0"/>
 *         &lt;element name="tacka" type="{http://www.projekat.org/zalbazbogcutanja}TNaziv" minOccurs="0"/>
 *         &lt;element name="naziv" type="{http://www.projekat.org/zalbazbogcutanja}TNaziv"/>
 *         &lt;element name="glasnik" type="{http://www.projekat.org/zalbazbogcutanja}TGlasnik" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TZakon", propOrder = {
    "clan",
    "stav",
    "tacka",
    "naziv",
    "glasnik"
})
public class TZakon {

    protected String clan;
    protected String stav;
    protected String tacka;
    @XmlElement(required = true)
    protected String naziv;
    protected String glasnik;

    /**
     * Gets the value of the clan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClan() {
        return clan;
    }

    /**
     * Sets the value of the clan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClan(String value) {
        this.clan = value;
    }

    /**
     * Gets the value of the stav property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStav() {
        return stav;
    }

    /**
     * Sets the value of the stav property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStav(String value) {
        this.stav = value;
    }

    /**
     * Gets the value of the tacka property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTacka() {
        return tacka;
    }

    /**
     * Sets the value of the tacka property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTacka(String value) {
        this.tacka = value;
    }

    /**
     * Gets the value of the naziv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Sets the value of the naziv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaziv(String value) {
        this.naziv = value;
    }

    /**
     * Gets the value of the glasnik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlasnik() {
        return glasnik;
    }

    /**
     * Sets the value of the glasnik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlasnik(String value) {
        this.glasnik = value;
    }

}
