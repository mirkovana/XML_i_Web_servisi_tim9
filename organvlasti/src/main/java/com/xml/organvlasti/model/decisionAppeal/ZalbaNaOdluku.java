//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.20 at 09:11:41 AM CET 
//


package com.xml.organvlasti.model.decisionAppeal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
 *         &lt;element name="naslov" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="podaci_o_povereniku" type="{http://www.projekat.org/zalbanaodluku}TPravno_lice" minOccurs="0"/>
 *         &lt;element name="zalba_status">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="property" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="podaci_o_zalbi" type="{http://www.projekat.org/zalbanaodluku}Tpodaci_o_zalbi"/>
 *         &lt;element name="sadrzaj_zalbe" type="{http://www.projekat.org/zalbanaodluku}Tsadrzaj_zalbe"/>
 *         &lt;element name="podaci_o_podnosiocu_zalbe" type="{http://www.projekat.org/zalbanaodluku}TFizicko_lice"/>
 *         &lt;element name="podaci_o_mestu_i_datumu_podnosenja_zalbe" type="{http://www.projekat.org/zalbanaodluku}Tmesto_datum"/>
 *         &lt;element name="napomena" type="{http://www.projekat.org/zalbanaodluku}Tnapomena" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="broj" use="required" type="{http://www.projekat.org/zalbanaodluku}TBroj" />
 *       &lt;attribute name="username" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="datum" use="required" type="{http://www.projekat.org/zalbanaodluku}TDatum" />
 *       &lt;attribute name="about" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="status" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="poverenikUsername" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "zalbaStatus",
    "podaciOZalbi",
    "sadrzajZalbe",
    "podaciOPodnosiocuZalbe",
    "podaciOMestuIDatumuPodnosenjaZalbe",
    "napomena"
})
@XmlRootElement(name = "zalba_na_odluku")
public class ZalbaNaOdluku {

    protected String naslov;
    @XmlElement(name = "podaci_o_povereniku")
    protected TPravnoLice podaciOPovereniku;
    @XmlElement(name = "zalba_status", required = true)
    protected ZalbaNaOdluku.ZalbaStatus zalbaStatus;
    @XmlElement(name = "podaci_o_zalbi", required = true)
    protected TpodaciOZalbi podaciOZalbi;
    @XmlElement(name = "sadrzaj_zalbe", required = true)
    protected TsadrzajZalbe sadrzajZalbe;
    @XmlElement(name = "podaci_o_podnosiocu_zalbe", required = true)
    protected TFizickoLice podaciOPodnosiocuZalbe;
    @XmlElement(name = "podaci_o_mestu_i_datumu_podnosenja_zalbe", required = true)
    protected TmestoDatum podaciOMestuIDatumuPodnosenjaZalbe;
    protected Tnapomena napomena;
    @XmlAttribute(name = "broj", required = true)
    protected String broj;
    @XmlAttribute(name = "username", required = true)
    protected String username;
    @XmlAttribute(name = "datum", required = true)
    protected String datum;
    @XmlAttribute(name = "about", required = true)
    protected String about;
    @XmlAttribute(name = "status", required = true)
    protected String status;
    @XmlAttribute(name = "poverenikUsername", required = true)
    protected String poverenikUsername;

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
     * Gets the value of the zalbaStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ZalbaNaOdluku.ZalbaStatus }
     *     
     */
    public ZalbaNaOdluku.ZalbaStatus getZalbaStatus() {
        return zalbaStatus;
    }

    /**
     * Sets the value of the zalbaStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZalbaNaOdluku.ZalbaStatus }
     *     
     */
    public void setZalbaStatus(ZalbaNaOdluku.ZalbaStatus value) {
        this.zalbaStatus = value;
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

    /**
     * Gets the value of the broj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBroj() {
        return broj;
    }

    /**
     * Sets the value of the broj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBroj(String value) {
        this.broj = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the datum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatum() {
        return datum;
    }

    /**
     * Sets the value of the datum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatum(String value) {
        this.datum = value;
    }

    /**
     * Gets the value of the about property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbout() {
        return about;
    }

    /**
     * Sets the value of the about property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbout(String value) {
        this.about = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the poverenikUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoverenikUsername() {
        return poverenikUsername;
    }

    /**
     * Sets the value of the poverenikUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoverenikUsername(String value) {
        this.poverenikUsername = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="property" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class ZalbaStatus {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "property")
        protected String property;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the property property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProperty() {
            return property;
        }

        /**
         * Sets the value of the property property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProperty(String value) {
            this.property = value;
        }

    }

}
