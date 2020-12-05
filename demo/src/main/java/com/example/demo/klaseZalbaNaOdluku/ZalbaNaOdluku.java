//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.12.01 at 09:16:29 PM CET 
//


package com.example.demo.klaseZalbaNaOdluku;

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
 *         &lt;element name="podaci_o_povereniku" type="{http://www.ftn.uns.ac.rs/zalbanaodluku}TPravno_lice"/>
 *         &lt;element name="podaci_o_zalbi" type="{http://www.ftn.uns.ac.rs/zalbanaodluku}Tpodaci_o_zalbi"/>
 *         &lt;element name="sadrzaj_zalbe" type="{http://www.ftn.uns.ac.rs/zalbanaodluku}Tsadrzaj_zalbe"/>
 *         &lt;element name="podaci_o_podnosiocu_zalbe" type="{http://www.ftn.uns.ac.rs/zalbanaodluku}TFizicko_lice"/>
 *         &lt;element name="podaci_o_mestu_i_datumu_podnosenja_zalbe" type="{http://www.ftn.uns.ac.rs/zalbanaodluku}Tmesto_datum"/>
 *         &lt;element name="napomena" type="{http://www.ftn.uns.ac.rs/zalbanaodluku}Tnapomena"/>
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
    "podaciOZalbi",
    "sadrzajZalbe",
    "podaciOPodnosiocuZalbe",
    "podaciOMestuIDatumuPodnosenjaZalbe",
    "napomena"
})
@XmlRootElement(name = "zalba_na_odluku")
public class ZalbaNaOdluku {

    @XmlElement(required = true)
    protected String naslov;
    @XmlElement(name = "podaci_o_povereniku", required = true)
    protected TPravnoLice podaciOPovereniku;
    @XmlElement(name = "podaci_o_zalbi", required = true)
    protected TpodaciOZalbi podaciOZalbi;
    @XmlElement(name = "sadrzaj_zalbe", required = true)
    protected TsadrzajZalbe sadrzajZalbe;
    @XmlElement(name = "podaci_o_podnosiocu_zalbe", required = true)
    protected TFizickoLice podaciOPodnosiocuZalbe;
    @XmlElement(name = "podaci_o_mestu_i_datumu_podnosenja_zalbe", required = true)
    protected TmestoDatum podaciOMestuIDatumuPodnosenjaZalbe;
    @XmlElement(required = true)
    protected Tnapomena napomena;

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
     * Gets the value of the podaciOZalbi property.
     * 
     * @return
     *     possible object is
     *     {@link TpodaciOZalbi }
     *     
     */
    public TpodaciOZalbi getPodaciOZalbi() {
        return podaciOZalbi;
    }

    /**
     * Sets the value of the podaciOZalbi property.
     * 
     * @param value
     *     allowed object is
     *     {@link TpodaciOZalbi }
     *     
     */
    public void setPodaciOZalbi(TpodaciOZalbi value) {
        this.podaciOZalbi = value;
    }

    /**
     * Gets the value of the sadrzajZalbe property.
     * 
     * @return
     *     possible object is
     *     {@link TsadrzajZalbe }
     *     
     */
    public TsadrzajZalbe getSadrzajZalbe() {
        return sadrzajZalbe;
    }

    /**
     * Sets the value of the sadrzajZalbe property.
     * 
     * @param value
     *     allowed object is
     *     {@link TsadrzajZalbe }
     *     
     */
    public void setSadrzajZalbe(TsadrzajZalbe value) {
        this.sadrzajZalbe = value;
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

    /**
     * Gets the value of the napomena property.
     * 
     * @return
     *     possible object is
     *     {@link Tnapomena }
     *     
     */
    public Tnapomena getNapomena() {
        return napomena;
    }

    /**
     * Sets the value of the napomena property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tnapomena }
     *     
     */
    public void setNapomena(Tnapomena value) {
        this.napomena = value;
    }

}