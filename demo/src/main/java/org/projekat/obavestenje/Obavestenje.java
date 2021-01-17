//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.17 at 03:47:53 PM CET 
//


package org.projekat.obavestenje;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="opste_informacije" type="{http://www.projekat.org/obavestenje}Topste_informacije"/>
 *         &lt;element name="naslov" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="podnaslov" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="telo" type="{http://www.projekat.org/obavestenje}Ttelo_obavestenja"/>
 *         &lt;element name="dostavljeno" maxOccurs="2" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Imenovanom"/>
 *               &lt;enumeration value="Arhivi"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="potpis" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="about" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="broj" type="{http://www.projekat.org/obavestenje}TBroj" />
 *       &lt;attribute name="username" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="datum" type="{http://www.projekat.org/obavestenje}Tdatum" />
 *       &lt;attribute name="organVlastiUsername" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "opsteInformacije",
    "naslov",
    "podnaslov",
    "telo",
    "dostavljeno",
    "potpis"
})
@XmlRootElement(name = "obavestenje")
public class Obavestenje {

    @XmlElement(name = "opste_informacije", required = true)
    protected TopsteInformacije opsteInformacije;
    protected String naslov;
    protected String podnaslov;
    @XmlElement(required = true)
    protected TteloObavestenja telo;
    protected List<String> dostavljeno;
    @XmlElement(required = true)
    protected String potpis;
    @XmlAttribute(name = "about")
    protected String about;
    @XmlAttribute(name = "broj")
    protected String broj;
    @XmlAttribute(name = "username")
    protected String username;
    @XmlAttribute(name = "datum")
    protected String datum;
    @XmlAttribute(name = "organVlastiUsername")
    protected String organVlastiUsername;

    /**
     * Gets the value of the opsteInformacije property.
     * 
     * @return
     *     possible object is
     *     {@link TopsteInformacije }
     *     
     */
    public TopsteInformacije getOpsteInformacije() {
        return opsteInformacije;
    }

    /**
     * Sets the value of the opsteInformacije property.
     * 
     * @param value
     *     allowed object is
     *     {@link TopsteInformacije }
     *     
     */
    public void setOpsteInformacije(TopsteInformacije value) {
        this.opsteInformacije = value;
    }

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
     * Gets the value of the podnaslov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPodnaslov() {
        return podnaslov;
    }

    /**
     * Sets the value of the podnaslov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPodnaslov(String value) {
        this.podnaslov = value;
    }

    /**
     * Gets the value of the telo property.
     * 
     * @return
     *     possible object is
     *     {@link TteloObavestenja }
     *     
     */
    public TteloObavestenja getTelo() {
        return telo;
    }

    /**
     * Sets the value of the telo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TteloObavestenja }
     *     
     */
    public void setTelo(TteloObavestenja value) {
        this.telo = value;
    }

    /**
     * Gets the value of the dostavljeno property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dostavljeno property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDostavljeno().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDostavljeno() {
        if (dostavljeno == null) {
            dostavljeno = new ArrayList<String>();
        }
        return this.dostavljeno;
    }

    /**
     * Gets the value of the potpis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPotpis() {
        return potpis;
    }

    /**
     * Sets the value of the potpis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPotpis(String value) {
        this.potpis = value;
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
     * Gets the value of the organVlastiUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganVlastiUsername() {
        return organVlastiUsername;
    }

    /**
     * Sets the value of the organVlastiUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganVlastiUsername(String value) {
        this.organVlastiUsername = value;
    }

}
