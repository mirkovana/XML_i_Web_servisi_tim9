//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.17 at 03:47:53 PM CET 
//


package com.xml.project.model.notice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.projekat.obavestenje package. 
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

    private final static QName _Datum_QNAME = new QName("http://www.projekat.org/obavestenje", "datum");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.projekat.obavestenje
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Tpodnosilac }
     * 
     */
    public Tpodnosilac createTpodnosilac() {
        return new Tpodnosilac();
    }

    /**
     * Create an instance of {@link Torgan }
     * 
     */
    public Torgan createTorgan() {
        return new Torgan();
    }

    /**
     * Create an instance of {@link TopsteInformacije }
     * 
     */
    public TopsteInformacije createTopsteInformacije() {
        return new TopsteInformacije();
    }

    /**
     * Create an instance of {@link Obavestenje }
     * 
     */
    public Obavestenje createObavestenje() {
        return new Obavestenje();
    }

    /**
     * Create an instance of {@link TteloObavestenja }
     * 
     */
    public TteloObavestenja createTteloObavestenja() {
        return new TteloObavestenja();
    }

    /**
     * Create an instance of {@link TadresaOrgana }
     * 
     */
    public TadresaOrgana createTadresaOrgana() {
        return new TadresaOrgana();
    }

    /**
     * Create an instance of {@link TadresaPodnosioca }
     * 
     */
    public TadresaPodnosioca createTadresaPodnosioca() {
        return new TadresaPodnosioca();
    }

    /**
     * Create an instance of {@link Tpodnosilac.Ime }
     * 
     */
    public Tpodnosilac.Ime createTpodnosilacIme() {
        return new Tpodnosilac.Ime();
    }

    /**
     * Create an instance of {@link Tpodnosilac.Prezime }
     * 
     */
    public Tpodnosilac.Prezime createTpodnosilacPrezime() {
        return new Tpodnosilac.Prezime();
    }

    /**
     * Create an instance of {@link Torgan.Naziv }
     * 
     */
    public Torgan.Naziv createTorganNaziv() {
        return new Torgan.Naziv();
    }

    /**
     * Create an instance of {@link Torgan.Sediste }
     * 
     */
    public Torgan.Sediste createTorganSediste() {
        return new Torgan.Sediste();
    }

    /**
     * Create an instance of {@link TopsteInformacije.BrojPredmeta }
     * 
     */
    public TopsteInformacije.BrojPredmeta createTopsteInformacijeBrojPredmeta() {
        return new TopsteInformacije.BrojPredmeta();
    }

    /**
     * Create an instance of {@link TopsteInformacije.Datum }
     * 
     */
    public TopsteInformacije.Datum createTopsteInformacijeDatum() {
        return new TopsteInformacije.Datum();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.projekat.org/obavestenje", name = "datum")
    public JAXBElement<String> createDatum(String value) {
        return new JAXBElement<String>(_Datum_QNAME, String.class, null, value);
    }

}
