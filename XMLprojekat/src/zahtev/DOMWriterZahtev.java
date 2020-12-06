package zahtev;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * 
 * Primer demonstrira metode API-ja za potrebe programskog kreiranja DOM stabla. 
 * Pored programskog kreiranja DOM stabla, primer demonstrira i serijalizaciju
 * DOM stabla na proizvoljan stream (npr. FileOutputStream, System.out, itd.).
 *
 */
public class DOMWriterZahtev {

	private static String TARGET_NAMESPACE = "http://www.projekat.org/zahtev";

	private static String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
	
	private static DocumentBuilderFactory factory;
	
	private static TransformerFactory transformerFactory;
	
	private Document document;
	
	/*
	 * Factory initialization static-block
	 */
	static {
		factory = DocumentBuilderFactory.newInstance();
		
		transformerFactory = TransformerFactory.newInstance();
	}
	
	/**
	 * Generates document object model for a given XML file.
	 */
	public void createDocument() {

		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			// Kreiranje novog dokumenta 
			document = builder.newDocument(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generates sample document object model 
	 * programmatically using DOM API methods. 
	 */
	public void generateDOM() {
		
		// Kreiranje i postavljanje korenskog elementa
		Element zahtev = document.createElementNS(TARGET_NAMESPACE, "zahtev");	
		zahtev.setAttributeNS(XSI_NAMESPACE, "xsi:schemaLocation", "http://www.projekat.org/zahtev zahtev.xsd");

		zahtev.appendChild(institucija("naziv0", "mesto0"));
		zahtev.appendChild(naslov("4", "bold", "Zahtev za pristupanje informacijama"));
		zahtev.appendChild(tekstZahteva());
		zahtev.appendChild(mestoDatum());
		zahtev.appendChild(podnosilac());
		zahtev.appendChild(footer());

		document.appendChild(zahtev);
	}
	
	public Element footer() {
		/*<reference>*U kućici označiti koja zakonska prava na pristup informacijama želite da ostvarite.</reference>
        <reference>**U kućici označiti način dostavljanja kopije dokumenata.</reference>
        <reference>***Kada zahtevate drugi način dostavljanja obavezno upisati koji način dostavljanja zahtevate.</reference>*/
		Element footer = document.createElementNS(TARGET_NAMESPACE, "footer");		
		footer.appendChild(element("reference", "*U kućici označiti koja zakonska prava na pristup informacijama želite da ostvarite"));
		footer.appendChild(element("reference", "**U kućici označiti način dostavljanja kopije dokumenata."));
		footer.appendChild(element("reference", "***Kada zahtevate drugi način dostavljanja obavezno upisati koji način dostavljanja"));
		return footer;
	}
	
	public Element podnosilac() {
		Element podnosilac = document.createElementNS(TARGET_NAMESPACE, "podnosilac");		
			Element osoba = document.createElementNS(TARGET_NAMESPACE, "osoba");		
			osoba.appendChild(element("ime", "ime0"));
			osoba.appendChild(element("prezime", "prezime0"));
		podnosilac.appendChild(osoba);
		podnosilac.appendChild(element("adresa", "adresa0"));
		podnosilac.appendChild(element("drugi_podaci", "drugi_podaci0"));
		podnosilac.appendChild(element("potpis", "potpis"));
		return podnosilac;
	}
	
	public Element mestoDatum() {
		Element mestoDatum = document.createElementNS(TARGET_NAMESPACE, "mesto_datum");		
		mestoDatum.appendChild(text("U "));
		mestoDatum.appendChild(element("mesto", "mesto1"));
		mestoDatum.appendChild(text("dana"));
		mestoDatum.appendChild(element("datum", "10.10.2010."));
		mestoDatum.appendChild(text("godine"));
		return mestoDatum;
	}
	
	public Element naslov(String nivo, String stil, String naslovS) {
		Element naslov = document.createElementNS(TARGET_NAMESPACE, "naslov");
		naslov.setAttribute("nivo", nivo);
		naslov.setAttribute("stil", stil);
		naslov.appendChild(text(naslovS));
		return naslov;
	}
	
	public Element tekstZahteva() {
		Element tekstZahteva = document.createElementNS(TARGET_NAMESPACE, "tekst_zahteva");
		tekstZahteva.appendChild(uvod());
		tekstZahteva.appendChild(lista());
		tekstZahteva.appendChild(text("Ovaj zahtev se odnosi na sledece informacije:"));
		tekstZahteva.appendChild(element("informacije", "ahda hahd ua dhajdh ah dlakhdah alsh dka "));
		return tekstZahteva;
	}
	
	public Element uvod() {
		Element uvod = document.createElementNS(TARGET_NAMESPACE, "uvod");
		uvod.appendChild(text("Na osnovu"));
		uvod.appendChild(zakon("clana 15.", "st. 1.", "",
				"Zakona o slobodnom pristupu informacijama od javnog značaja",
				"(„Službeni glasnik RS“, br. 120/04, 54/07, 104/09 i 36/10)"));
		uvod.appendChild(text("od gore navedenog organa zahtevam:*"));
		return uvod;
	}
	
	public Element lista() {
		Element lista = document.createElementNS(TARGET_NAMESPACE, "lista");
		
		lista.appendChild(stavka("false", "1", "obaveštenje da li poseduje traženu informaciju"));
		lista.appendChild(stavka("false", "2", "uvid u dokument koji sadrži traženu informaciju"));
		lista.appendChild(stavka("false", "3", "kopiju dokumenta koji sadrži traženu informaciju"));
		
		Element stavka4 = stavka("false", "4", "dostavljanje kopije dokumenta koji sadrži traženu informaciju:**");
		/*<lista>
                    <stavka za:checked="false" za:id="5">поштом</stavka>
                    <stavka za:checked="false" za:id="6">elektronskom postom</stavka>
                    <stavka za:checked="false" za:id="7">faksom</stavka>
                    <stavka  za:checked="false" za:id="8">na drugi nacin***: 
                        <drugi_nacin>dajshkdandaskla</drugi_nacin>
                    </stavka>
                </lista>*/
		lista.appendChild(stavka4);
		Element lista2 = document.createElementNS(TARGET_NAMESPACE, "lista");
		lista2.appendChild(stavka("false", "5", "postom"));
		lista2.appendChild(stavka("false", "6", "elektronskom postom"));
		lista2.appendChild(stavka("false", "7", "faksom"));
		Element stavka8 = document.createElementNS(TARGET_NAMESPACE, "stavka");
		stavka8.setAttribute("checked", "false");
		stavka8.setAttribute("id", "8");
		stavka8.appendChild(text("na drugi nacin:***"));
			Element dn = document.createElementNS(TARGET_NAMESPACE, "drugi_nacin");
			dn.appendChild(text("neki drugi nacin za dostavu"));
		stavka8.appendChild(dn);
		lista2.appendChild(stavka8);
		
		stavka4.appendChild(lista2);
		
		return lista;
	}
	
	public Element stavka(String checked, String id, String stavkaS) {
		Element stavka = document.createElementNS(TARGET_NAMESPACE, "stavka");
		stavka.setAttribute("checked", checked);
		stavka.setAttribute("id", id);
		stavka.appendChild(text(stavkaS));
		return stavka;
	}
	
	public Element zakon(String clan, String stav, String tacka, String naziv, String glasnik) {
		Element zakon = document.createElementNS(TARGET_NAMESPACE, "zakon");
		if(!clan.contentEquals("")) {
			zakon.appendChild(element("clan", clan));
		}
		if(!stav.contentEquals("")) {
			zakon.appendChild(element("stav", stav));
		}
		if(!tacka.contentEquals("")) {
			zakon.appendChild(element("tacka", tacka));
		}
		if(!naziv.contentEquals("")) {
			zakon.appendChild(element("naziv", naziv));
		}
		if(!glasnik.contentEquals("")) {
			zakon.appendChild(element("glasnik", glasnik));
		}
		return zakon;
	}
	
	public Element institucija(String nazivS, String mestoS) {
		Element institucija = document.createElementNS(TARGET_NAMESPACE, "institucija");
		institucija.appendChild(naziv(nazivS));
		institucija.appendChild(text(" u "));
		institucija.appendChild(mesto(mestoS));
		return institucija;
	}
	
	public Element mesto(String mestoS) {
		Element mesto = document.createElementNS(TARGET_NAMESPACE, "mesto");
		mesto.appendChild(text(mestoS));
		return mesto;
	}
	public Element naziv(String nazivS) {
		Element naziv = document.createElementNS(TARGET_NAMESPACE, "naziv");
		naziv.appendChild(text(nazivS));
		return naziv;
	}
	public Text text(String text) {
		return document.createTextNode(text);
	}
	
	public Element element(String name, String text) {
		Element element = document.createElementNS(TARGET_NAMESPACE, name);
		element.appendChild(text(text));
		return element;
	}
	
	/**
	 * Serializes DOM tree to an arbitrary OutputStream.
	 */
	public void transform(OutputStream out) {
		try {

			// Kreiranje instance objekta zaduzenog za serijalizaciju DOM modela
			Transformer transformer = transformerFactory.newTransformer();

			// Indentacija serijalizovanog izlaza
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			// Nad "source" objektom (DOM stablo) vrÅ¡i se transformacija
			DOMSource source = new DOMSource(document);

			// RezultujuÄ‡i stream (argument metode) 
			StreamResult result = new StreamResult(out);

			// Poziv metode koja vrÅ¡i opisanu transformaciju
			transformer.transform(source, result);

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {

		String filePath = null;

		System.out.println("[INFO] DOM Parser");

		if (args.length != 1) {

			filePath = "data/xml/zahtevW.xml";

			System.out.println("[INFO] No input file, using default \""	+ filePath + "\"");

		} else {
			filePath = args[0];
		}

		DOMWriterZahtev handler = new DOMWriterZahtev();

		// Kreiranje Document Ä?vora
		handler.createDocument();

		// Generisanje DOM stabla
		handler.generateDOM();
		
		// Prikaz sadrÅ¾aja (isprobati sa FileOutputStream-om)
		handler.transform(System.out);
		
		
		try {
			handler.transform(new FileOutputStream("data/out/zahtevW.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
