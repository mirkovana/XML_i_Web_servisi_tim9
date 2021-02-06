//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.26 at 11:41:40 AM CET 
//


package org.projekat.resenje;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.projekat.resenje package. 
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

    private final static QName _Naslov_QNAME = new QName("http://www.projekat.org/resenje", "naslov");
    private final static QName _TInstitucijaMesto_QNAME = new QName("http://www.projekat.org/resenje", "mesto");
    private final static QName _TInstitucijaNaziv_QNAME = new QName("http://www.projekat.org/resenje", "naziv");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.projekat.resenje
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Zalba }
     * 
     */
    public Zalba createZalba() {
        return new Zalba();
    }

    /**
     * Create an instance of {@link TInstitucija }
     * 
     */
    public TInstitucija createTInstitucija() {
        return new TInstitucija();
    }

    /**
     * Create an instance of {@link TOsoba }
     * 
     */
    public TOsoba createTOsoba() {
        return new TOsoba();
    }

    /**
     * Create an instance of {@link Zalba.ZalbaBroj }
     * 
     */
    public Zalba.ZalbaBroj createZalbaZalbaBroj() {
        return new Zalba.ZalbaBroj();
    }

    /**
     * Create an instance of {@link Zalba.DatumSlanja }
     * 
     */
    public Zalba.DatumSlanja createZalbaDatumSlanja() {
        return new Zalba.DatumSlanja();
    }

    /**
     * Create an instance of {@link Zalba.ZalbaStatus }
     * 
     */
    public Zalba.ZalbaStatus createZalbaZalbaStatus() {
        return new Zalba.ZalbaStatus();
    }

    /**
     * Create an instance of {@link TUvod }
     * 
     */
    public TUvod createTUvod() {
        return new TUvod();
    }

    /**
     * Create an instance of {@link TSadrzaj }
     * 
     */
    public TSadrzaj createTSadrzaj() {
        return new TSadrzaj();
    }

    /**
     * Create an instance of {@link Datum }
     * 
     */
    public Datum createDatum() {
        return new Datum();
    }

    /**
     * Create an instance of {@link TNaslov }
     * 
     */
    public TNaslov createTNaslov() {
        return new TNaslov();
    }

    /**
     * Create an instance of {@link TTrazenaInformacija }
     * 
     */
    public TTrazenaInformacija createTTrazenaInformacija() {
        return new TTrazenaInformacija();
    }

    /**
     * Create an instance of {@link TIme }
     * 
     */
    public TIme createTIme() {
        return new TIme();
    }

    /**
     * Create an instance of {@link TResenje }
     * 
     */
    public TResenje createTResenje() {
        return new TResenje();
    }

    /**
     * Create an instance of {@link TPrezime }
     * 
     */
    public TPrezime createTPrezime() {
        return new TPrezime();
    }

    /**
     * Create an instance of {@link TZakon }
     * 
     */
    public TZakon createTZakon() {
        return new TZakon();
    }

    /**
     * Create an instance of {@link TObrazlozenje }
     * 
     */
    public TObrazlozenje createTObrazlozenje() {
        return new TObrazlozenje();
    }

    /**
     * Create an instance of {@link TParagraf }
     * 
     */
    public TParagraf createTParagraf() {
        return new TParagraf();
    }

    /**
     * Create an instance of {@link TInstitucija.Naziv }
     * 
     */
    public TInstitucija.Naziv createTInstitucijaNaziv() {
        return new TInstitucija.Naziv();
    }

    /**
     * Create an instance of {@link TInstitucija.Mesto }
     * 
     */
    public TInstitucija.Mesto createTInstitucijaMesto() {
        return new TInstitucija.Mesto();
    }

    /**
     * Create an instance of {@link TOsoba.Ime }
     * 
     */
    public TOsoba.Ime createTOsobaIme() {
        return new TOsoba.Ime();
    }

    /**
     * Create an instance of {@link TOsoba.Prezime }
     * 
     */
    public TOsoba.Prezime createTOsobaPrezime() {
        return new TOsoba.Prezime();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TNaslov }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/resenje", name = "naslov")
    public JAXBElement<TNaslov> createNaslov(TNaslov value) {
        return new JAXBElement<TNaslov>(_Naslov_QNAME, TNaslov.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TInstitucija.Mesto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/resenje", name = "mesto", scope = TInstitucija.class)
    public JAXBElement<TInstitucija.Mesto> createTInstitucijaMesto(TInstitucija.Mesto value) {
        return new JAXBElement<TInstitucija.Mesto>(_TInstitucijaMesto_QNAME, TInstitucija.Mesto.class, TInstitucija.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TInstitucija.Naziv }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/resenje", name = "naziv", scope = TInstitucija.class)
    public JAXBElement<TInstitucija.Naziv> createTInstitucijaNaziv(TInstitucija.Naziv value) {
        return new JAXBElement<TInstitucija.Naziv>(_TInstitucijaNaziv_QNAME, TInstitucija.Naziv.class, TInstitucija.class, value);
    }

}
