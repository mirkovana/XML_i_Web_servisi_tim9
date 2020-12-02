//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.12.02 at 05:08:40 PM CET 
//


package com.example.demo.klaseZalbaZbogCutanja;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.demo.klaseZalbaZbogCutanja package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TteloZalbeDatum_QNAME = new QName("http://www.ftn.uns.ac.rs/zalbaCutanja", "datum");
    private final static QName _TteloZalbePozivNaZakon_QNAME = new QName("http://www.ftn.uns.ac.rs/zalbaCutanja", "poziv_na_zakon");
    private final static QName _TteloZalbeNazivOrgana_QNAME = new QName("http://www.ftn.uns.ac.rs/zalbaCutanja", "naziv_organa");
    private final static QName _TteloZalbeNapomena_QNAME = new QName("http://www.ftn.uns.ac.rs/zalbaCutanja", "napomena");
    private final static QName _TteloZalbePodaciOZahtevuIInformaciji_QNAME = new QName("http://www.ftn.uns.ac.rs/zalbaCutanja", "podaci_o_zahtevu_i_informaciji");
    private final static QName _TteloZalbeRazlozi_QNAME = new QName("http://www.ftn.uns.ac.rs/zalbaCutanja", "razlozi");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.demo.klaseZalbaZbogCutanja
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ZalbaCutanje }
     * 
     */
    public ZalbaCutanje createZalbaCutanje() {
        return new ZalbaCutanje();
    }

    /**
     * Create an instance of {@link TPravnoLice }
     * 
     */
    public TPravnoLice createTPravnoLice() {
        return new TPravnoLice();
    }

    /**
     * Create an instance of {@link TteloZalbe }
     * 
     */
    public TteloZalbe createTteloZalbe() {
        return new TteloZalbe();
    }

    /**
     * Create an instance of {@link TFizickoLice }
     * 
     */
    public TFizickoLice createTFizickoLice() {
        return new TFizickoLice();
    }

    /**
     * Create an instance of {@link TmestoDatum }
     * 
     */
    public TmestoDatum createTmestoDatum() {
        return new TmestoDatum();
    }

    /**
     * Create an instance of {@link Adresa }
     * 
     */
    public Adresa createAdresa() {
        return new Adresa();
    }

    /**
     * Create an instance of {@link Trazlozi }
     * 
     */
    public Trazlozi createTrazlozi() {
        return new Trazlozi();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/zalbaCutanja", name = "datum", scope = TteloZalbe.class)
    public JAXBElement<XMLGregorianCalendar> createTteloZalbeDatum(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TteloZalbeDatum_QNAME, XMLGregorianCalendar.class, TteloZalbe.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/zalbaCutanja", name = "poziv_na_zakon", scope = TteloZalbe.class)
    public JAXBElement<String> createTteloZalbePozivNaZakon(String value) {
        return new JAXBElement<String>(_TteloZalbePozivNaZakon_QNAME, String.class, TteloZalbe.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/zalbaCutanja", name = "naziv_organa", scope = TteloZalbe.class)
    public JAXBElement<String> createTteloZalbeNazivOrgana(String value) {
        return new JAXBElement<String>(_TteloZalbeNazivOrgana_QNAME, String.class, TteloZalbe.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/zalbaCutanja", name = "napomena", scope = TteloZalbe.class)
    public JAXBElement<String> createTteloZalbeNapomena(String value) {
        return new JAXBElement<String>(_TteloZalbeNapomena_QNAME, String.class, TteloZalbe.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/zalbaCutanja", name = "podaci_o_zahtevu_i_informaciji", scope = TteloZalbe.class)
    public JAXBElement<String> createTteloZalbePodaciOZahtevuIInformaciji(String value) {
        return new JAXBElement<String>(_TteloZalbePodaciOZahtevuIInformaciji_QNAME, String.class, TteloZalbe.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Trazlozi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/zalbaCutanja", name = "razlozi", scope = TteloZalbe.class)
    public JAXBElement<Trazlozi> createTteloZalbeRazlozi(Trazlozi value) {
        return new JAXBElement<Trazlozi>(_TteloZalbeRazlozi_QNAME, Trazlozi.class, TteloZalbe.class, value);
    }

}
