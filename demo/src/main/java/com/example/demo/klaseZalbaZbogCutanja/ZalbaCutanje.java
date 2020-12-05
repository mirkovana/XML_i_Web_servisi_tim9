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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="naslov">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="podaci_o_povereniku" type="{http://www.ftn.uns.ac.rs/zalbaCutanja}TPravno_lice"/>
 *         &lt;element name="telo_zalbe" type="{http://www.ftn.uns.ac.rs/zalbaCutanja}Ttelo_zalbe"/>
 *         &lt;element name="podaci_o_podnosiocu_zalbe" type="{http://www.ftn.uns.ac.rs/zalbaCutanja}TFizicko_lice"/>
 *         &lt;element name="podaci_o_mestu_i_datumu_podnosenja_zalbe" type="{http://www.ftn.uns.ac.rs/zalbaCutanja}Tmesto_datum"/>
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
    "naslov",
    "podaciOPovereniku",
    "teloZalbe",
    "podaciOPodnosiocuZalbe",
    "podaciOMestuIDatumuPodnosenjaZalbe"
})
@XmlRootElement(name = "zalba_cutanje")
public class ZalbaCutanje {

    @XmlElement(required = true)
    protected String naslov;
    @XmlElement(name = "podaci_o_povereniku", required = true)
    protected TPravnoLice podaciOPovereniku;
    @XmlElement(name = "telo_zalbe", required = true)
    protected TteloZalbe teloZalbe;
    @XmlElement(name = "podaci_o_podnosiocu_zalbe", required = true)
    protected TFizickoLice podaciOPodnosiocuZalbe;
    @XmlElement(name = "podaci_o_mestu_i_datumu_podnosenja_zalbe", required = true)
    protected TmestoDatum podaciOMestuIDatumuPodnosenjaZalbe;

    /**
     * Gets the value of the naslov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaslov() {
        return naslov;
    }

    /**
     * Sets the value of the naslov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaslov(String value) {
        this.naslov = value;
    }

    /**
     * Gets the value of the podaciOPovereniku property.
     * 
     * @return
     *     possible object is
     *     {@link TPravnoLice }
     *     
     */
    public TPravnoLice getPodaciOPovereniku() {
        return podaciOPovereniku;
    }

    /**
     * Sets the value of the podaciOPovereniku property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPravnoLice }
     *     
     */
    public void setPodaciOPovereniku(TPravnoLice value) {
        this.podaciOPovereniku = value;
    }

    /**
     * Gets the value of the teloZalbe property.
     * 
     * @return
     *     possible object is
     *     {@link TteloZalbe }
     *     
     */
    public TteloZalbe getTeloZalbe() {
        return teloZalbe;
    }

    /**
     * Sets the value of the teloZalbe property.
     * 
     * @param value
     *     allowed object is
     *     {@link TteloZalbe }
     *     
     */
    public void setTeloZalbe(TteloZalbe value) {
        this.teloZalbe = value;
    }

    /**
     * Gets the value of the podaciOPodnosiocuZalbe property.
     * 
     * @return
     *     possible object is
     *     {@link TFizickoLice }
     *     
     */
    public TFizickoLice getPodaciOPodnosiocuZalbe() {
        return podaciOPodnosiocuZalbe;
    }

    /**
     * Sets the value of the podaciOPodnosiocuZalbe property.
     * 
     * @param value
     *     allowed object is
     *     {@link TFizickoLice }
     *     
     */
    public void setPodaciOPodnosiocuZalbe(TFizickoLice value) {
        this.podaciOPodnosiocuZalbe = value;
    }

    /**
     * Gets the value of the podaciOMestuIDatumuPodnosenjaZalbe property.
     * 
     * @return
     *     possible object is
     *     {@link TmestoDatum }
     *     
     */
    public TmestoDatum getPodaciOMestuIDatumuPodnosenjaZalbe() {
        return podaciOMestuIDatumuPodnosenjaZalbe;
    }

    /**
     * Sets the value of the podaciOMestuIDatumuPodnosenjaZalbe property.
     * 
     * @param value
     *     allowed object is
     *     {@link TmestoDatum }
     *     
     */
    public void setPodaciOMestuIDatumuPodnosenjaZalbe(TmestoDatum value) {
        this.podaciOMestuIDatumuPodnosenjaZalbe = value;
    }

}