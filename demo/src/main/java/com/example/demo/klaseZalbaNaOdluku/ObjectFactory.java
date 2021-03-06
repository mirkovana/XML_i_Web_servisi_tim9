//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.12.07 at 07:36:55 PM CET 
//


package com.example.demo.klaseZalbaNaOdluku;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.demo.klaseZalbaNaOdluku package. 
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

    private final static QName _TpodaciOZalbiOrganKojiJeDoneoOdluku_QNAME = new QName("http://www.ftn.uns.ac.rs/zalbanaodluku", "organ_koji_je_doneo_odluku");
    private final static QName _TpodaciOZalbiNaslov_QNAME = new QName("http://www.ftn.uns.ac.rs/zalbanaodluku", "naslov");
    private final static QName _TpodaciOZalbiDatum_QNAME = new QName("http://www.ftn.uns.ac.rs/zalbanaodluku", "datum");
    private final static QName _TpodaciOZalbiBrojZalbe_QNAME = new QName("http://www.ftn.uns.ac.rs/zalbanaodluku", "broj_zalbe");
    private final static QName _TpodaciOZalbiPodnosilacZalbe_QNAME = new QName("http://www.ftn.uns.ac.rs/zalbanaodluku", "podnosilac_zalbe");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.demo.klaseZalbaNaOdluku
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Adresa }
     * 
     */
    public Adresa createAdresa() {
        return new Adresa();
    }

    /**
     * Create an instance of {@link ZalbaNaOdluku }
     * 
     */
    public ZalbaNaOdluku createZalbaNaOdluku() {
        return new ZalbaNaOdluku();
    }

    /**
     * Create an instance of {@link TPravnoLice }
     * 
     */
    public TPravnoLice createTPravnoLice() {
        return new TPravnoLice();
    }

    /**
     * Create an instance of {@link TpodaciOZalbi }
     * 
     */
    public TpodaciOZalbi createTpodaciOZalbi() {
        return new TpodaciOZalbi();
    }

    /**
     * Create an instance of {@link TsadrzajZalbe }
     * 
     */
    public TsadrzajZalbe createTsadrzajZalbe() {
        return new TsadrzajZalbe();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link TPravnoLice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/zalbanaodluku", name = "organ_koji_je_doneo_odluku", scope = TpodaciOZalbi.class)
    public JAXBElement<TPravnoLice> createTpodaciOZalbiOrganKojiJeDoneoOdluku(TPravnoLice value) {
        return new JAXBElement<TPravnoLice>(_TpodaciOZalbiOrganKojiJeDoneoOdluku_QNAME, TPravnoLice.class, TpodaciOZalbi.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/zalbanaodluku", name = "naslov", scope = TpodaciOZalbi.class)
    public JAXBElement<String> createTpodaciOZalbiNaslov(String value) {
        return new JAXBElement<String>(_TpodaciOZalbiNaslov_QNAME, String.class, TpodaciOZalbi.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/zalbanaodluku", name = "datum", scope = TpodaciOZalbi.class)
    public JAXBElement<XMLGregorianCalendar> createTpodaciOZalbiDatum(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TpodaciOZalbiDatum_QNAME, XMLGregorianCalendar.class, TpodaciOZalbi.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/zalbanaodluku", name = "broj_zalbe", scope = TpodaciOZalbi.class)
    public JAXBElement<BigInteger> createTpodaciOZalbiBrojZalbe(BigInteger value) {
        return new JAXBElement<BigInteger>(_TpodaciOZalbiBrojZalbe_QNAME, BigInteger.class, TpodaciOZalbi.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TFizickoLice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/zalbanaodluku", name = "podnosilac_zalbe", scope = TpodaciOZalbi.class)
    public JAXBElement<TFizickoLice> createTpodaciOZalbiPodnosilacZalbe(TFizickoLice value) {
        return new JAXBElement<TFizickoLice>(_TpodaciOZalbiPodnosilacZalbe_QNAME, TFizickoLice.class, TpodaciOZalbi.class, value);
    }

}
