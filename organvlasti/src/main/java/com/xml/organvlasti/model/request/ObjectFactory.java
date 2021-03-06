//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.17 at 01:08:09 PM CET 
//


package com.xml.organvlasti.model.request;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.projekat.zahtev package. 
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

    private final static QName _Naslov_QNAME = new QName("http://www.projekat.org/zahtev", "naslov");
    private final static QName _DrugiNacin_QNAME = new QName("http://www.projekat.org/zahtev", "drugi_nacin");
    private final static QName _Mesto_QNAME = new QName("http://www.projekat.org/zahtev", "mesto");
    private final static QName _InstitucijaNaziv_QNAME = new QName("http://www.projekat.org/zahtev", "naziv");
    private final static QName _TekstZahtevaUvodZakon_QNAME = new QName("http://www.projekat.org/zahtev", "zakon");
    private final static QName _TekstZahtevaInformacije_QNAME = new QName("http://www.projekat.org/zahtev", "informacije");
    private final static QName _TekstZahtevaUvod_QNAME = new QName("http://www.projekat.org/zahtev", "uvod");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.projekat.zahtev
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Institucija }
     * 
     */
    public Institucija createInstitucija() {
        return new Institucija();
    }

    /**
     * Create an instance of {@link TekstZahteva }
     * 
     */
    public TekstZahteva createTekstZahteva() {
        return new TekstZahteva();
    }

    /**
     * Create an instance of {@link Lista }
     * 
     */
    public Lista createLista() {
        return new Lista();
    }

    /**
     * Create an instance of {@link Zahtev }
     * 
     */
    public Zahtev createZahtev() {
        return new Zahtev();
    }

    /**
     * Create an instance of {@link Osoba }
     * 
     */
    public Osoba createOsoba() {
        return new Osoba();
    }

    /**
     * Create an instance of {@link TekstZahteva.Uvod }
     * 
     */
    public TekstZahteva.Uvod createTekstZahtevaUvod() {
        return new TekstZahteva.Uvod();
    }

    /**
     * Create an instance of {@link Institucija.Naziv }
     * 
     */
    public Institucija.Naziv createInstitucijaNaziv() {
        return new Institucija.Naziv();
    }

    /**
     * Create an instance of {@link Institucija.Mesto }
     * 
     */
    public Institucija.Mesto createInstitucijaMesto() {
        return new Institucija.Mesto();
    }

    /**
     * Create an instance of {@link Lista.Stavka }
     * 
     */
    public Lista.Stavka createListaStavka() {
        return new Lista.Stavka();
    }

    /**
     * Create an instance of {@link TekstZahteva.Informacije }
     * 
     */
    public TekstZahteva.Informacije createTekstZahtevaInformacije() {
        return new TekstZahteva.Informacije();
    }

    /**
     * Create an instance of {@link Datum }
     * 
     */
    public Datum createDatum() {
        return new Datum();
    }

    /**
     * Create an instance of {@link org.projekat.zahtev.Stavka }
     * 
     */
    public com.xml.organvlasti.model.request.Stavka createStavka() {
        return new com.xml.organvlasti.model.request.Stavka();
    }

    /**
     * Create an instance of {@link Naslovi }
     * 
     */
    public Naslovi createNaslovi() {
        return new Naslovi();
    }

    /**
     * Create an instance of {@link Podnosilac }
     * 
     */
    public Podnosilac createPodnosilac() {
        return new Podnosilac();
    }

    /**
     * Create an instance of {@link Zahtev.ZahtevStatus }
     * 
     */
    public Zahtev.ZahtevStatus createZahtevZahtevStatus() {
        return new Zahtev.ZahtevStatus();
    }

    /**
     * Create an instance of {@link Zahtev.ZahtevBroj }
     * 
     */
    public Zahtev.ZahtevBroj createZahtevZahtevBroj() {
        return new Zahtev.ZahtevBroj();
    }

    /**
     * Create an instance of {@link Zahtev.MestoDatum }
     * 
     */
    public Zahtev.MestoDatum createZahtevMestoDatum() {
        return new Zahtev.MestoDatum();
    }

    /**
     * Create an instance of {@link Zahtev.Footer }
     * 
     */
    public Zahtev.Footer createZahtevFooter() {
        return new Zahtev.Footer();
    }

    /**
     * Create an instance of {@link Osoba.Ime }
     * 
     */
    public Osoba.Ime createOsobaIme() {
        return new Osoba.Ime();
    }

    /**
     * Create an instance of {@link Osoba.Prezime }
     * 
     */
    public Osoba.Prezime createOsobaPrezime() {
        return new Osoba.Prezime();
    }

    /**
     * Create an instance of {@link Osoba.Username }
     * 
     */
    public Osoba.Username createOsobaUsername() {
        return new Osoba.Username();
    }

    /**
     * Create an instance of {@link TekstZahteva.Uvod.Zakon }
     * 
     */
    public TekstZahteva.Uvod.Zakon createTekstZahtevaUvodZakon() {
        return new TekstZahteva.Uvod.Zakon();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Naslovi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/zahtev", name = "naslov")
    public JAXBElement<Naslovi> createNaslov(Naslovi value) {
        return new JAXBElement<Naslovi>(_Naslov_QNAME, Naslovi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/zahtev", name = "drugi_nacin")
    public JAXBElement<String> createDrugiNacin(String value) {
        return new JAXBElement<String>(_DrugiNacin_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/zahtev", name = "mesto")
    public JAXBElement<String> createMesto(String value) {
        return new JAXBElement<String>(_Mesto_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Institucija.Naziv }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/zahtev", name = "naziv", scope = Institucija.class)
    public JAXBElement<Institucija.Naziv> createInstitucijaNaziv(Institucija.Naziv value) {
        return new JAXBElement<Institucija.Naziv>(_InstitucijaNaziv_QNAME, Institucija.Naziv.class, Institucija.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Institucija.Mesto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/zahtev", name = "mesto", scope = Institucija.class)
    public JAXBElement<Institucija.Mesto> createInstitucijaMesto(Institucija.Mesto value) {
        return new JAXBElement<Institucija.Mesto>(_Mesto_QNAME, Institucija.Mesto.class, Institucija.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TekstZahteva.Uvod.Zakon }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/zahtev", name = "zakon", scope = TekstZahteva.Uvod.class)
    public JAXBElement<TekstZahteva.Uvod.Zakon> createTekstZahtevaUvodZakon(TekstZahteva.Uvod.Zakon value) {
        return new JAXBElement<TekstZahteva.Uvod.Zakon>(_TekstZahtevaUvodZakon_QNAME, TekstZahteva.Uvod.Zakon.class, TekstZahteva.Uvod.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TekstZahteva.Informacije }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/zahtev", name = "informacije", scope = TekstZahteva.class)
    public JAXBElement<TekstZahteva.Informacije> createTekstZahtevaInformacije(TekstZahteva.Informacije value) {
        return new JAXBElement<TekstZahteva.Informacije>(_TekstZahtevaInformacije_QNAME, TekstZahteva.Informacije.class, TekstZahteva.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TekstZahteva.Uvod }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/zahtev", name = "uvod", scope = TekstZahteva.class)
    public JAXBElement<TekstZahteva.Uvod> createTekstZahtevaUvod(TekstZahteva.Uvod value) {
        return new JAXBElement<TekstZahteva.Uvod>(_TekstZahtevaUvod_QNAME, TekstZahteva.Uvod.class, TekstZahteva.class, value);
    }

}
