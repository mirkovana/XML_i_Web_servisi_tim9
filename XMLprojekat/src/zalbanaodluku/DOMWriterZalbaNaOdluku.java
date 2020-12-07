package zalbanaodluku;

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
public class DOMWriterZalbaNaOdluku {

	private static String TARGET_NAMESPACE = "http://www.projekat.org/zalbanaodluku";

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
		Element zalba = document.createElementNS(TARGET_NAMESPACE, "zalba_na_odluku");
		document.appendChild(zalba);
		
		zalba.setAttributeNS(XSI_NAMESPACE, "xsi:schemaLocation", "http://www.projekat.org/zalbanaodluku zalbanaodluku.xsd");
		zalba.setAttribute("xmlns:zalba", "http://www.projekat.org/zalbanaodluku");
		zalba.appendChild(naslov("2", "bold", "ŽALBA PROTIV ODLUKE ORGANA VLASTI KOJOM JE ODBIJEN ILI ODBAČEN ZAHTEV ZA PRISTUP INFORMACIJI"));
		zalba.appendChild(podaciOPovereniku());
		zalba.appendChild(podaciOZalbi());
		zalba.appendChild(sadrzajZalbe());
		zalba.appendChild(podaciOPodnosiocu());
		zalba.appendChild(podaciOMestuDatumu());
		zalba.appendChild(napomena());
	}
	
	public Element napomena() {
		Element napomena = document.createElementNS(TARGET_NAMESPACE, "napomena");
		napomena.appendChild(element("tacka", " U žalbi se mora navesti odluka koja se pobija (rešenje, zaključak, obaveštenje), naziv organa koji je odluku doneo, kao i broj i datum odluke. Dovoljno je da žalilac navede u žalbi u kom pogledu je nezadovoljan odlukom, s tim da žalbu ne mora posebno obrazložiti. Ako žalbu izjavljuje na ovom obrascu, dodatno obrazloženje može posebno priložiti."));
		napomena.appendChild(element("tacka", "Uz žalbu obavezno priložiti kopiju podnetog zahteva i dokaz o njegovoj predaji-upućivanju organu kao i kopiju odluke organa koja se osporava žalbom."));
		return napomena;
	}
	
	public Element podaciOMestuDatumu() {
		Element podaci = document.createElementNS(TARGET_NAMESPACE, "podaci_o_mestu_i_datumu_podnosenja_zalbe");
		podaci.appendChild(element("mesto", "Novi Sad"));
		podaci.appendChild(element("datum", "10.10.2010."));
		return podaci;
	}
	
	public Element podaciOPodnosiocu() {
		Element podaci = document.createElementNS(TARGET_NAMESPACE, "podaci_o_podnosiocu_zalbe");
		podaci.appendChild(adresa("grad2", "ulica2", "10"));
		podaci.appendChild(element("ime", "Ime1"));
		podaci.appendChild(element("prezime", "Prezime1"));
		podaci.appendChild(element("potpis", "Potpis1"));
		podaci.appendChild(element("drugi_podaci_za_kontakt", "drugi podaci"));
		return podaci;
	}
	
	public Element sadrzajZalbe() {
		Element sadrzaj = document.createElementNS(TARGET_NAMESPACE, "sadrzaj_zalbe");
		sadrzaj.appendChild(text(" Navedenom odlukom organa vlasti (rešenjem, zaključkom, obaveštenjem u pisanoj formi sa elementima odluke) , suprotno zakonu, odbijen-odbačen je moj zahtev koji sam podneo/la-uputio/la dana "));
		sadrzaj.appendChild(element("datum", "15.10.2010."));
		sadrzaj.appendChild(text("godine i tako mi uskraćeno-onemogućeno ostvarivanje ustavnog i zakonskog prava na slobodan pristup informacijama od javnog značaja. Odluku pobijam u celosti, odnosno u delu kojim"));
		sadrzaj.appendChild(element("zbog_cega_se_pobija_odluka", "jer nije zasnovana na Zakonu o slobodnom pristupu informacijama od javnog značaja."));
		sadrzaj.appendChild(text("Na osnovu iznetih razloga, predlažem da "));
		sadrzaj.appendChild(element("predlog_povereniku", "Poverenik uvaži moju žalbu, poništi odluka prvostepenog organa i omogući mi pristup traženoj/im informaciji/ma."));
		sadrzaj.appendChild(text("Žalbu podnosim blagovremeno, u zakonskom roku utvrđenom u članu 22. st. 1. Zakona o slobodnom pristupu informacijama od javnog značaja."));
		return sadrzaj;
	}
	
	public Element podaciOZalbi() {
		Element podaci = document.createElementNS(TARGET_NAMESPACE, "podaci_o_zalbi");
		podaci.appendChild(naslov("2", "", "ZALBA"));
		
		Element podnosilac = document.createElementNS(TARGET_NAMESPACE, "podnosilac_zalbe");
			podnosilac.appendChild(adresa("Novi sad", "ulicaneka", "15"));
			podnosilac.appendChild(element("ime", "Ime1"));
			podnosilac.appendChild(element("prezime", "Prezime1"));
			podnosilac.appendChild(element("potpis", "Potpis1"));
		podaci.appendChild(podnosilac);
		podaci.appendChild(text("protiv resenja-zakljucka"));
		podaci.appendChild(element("organ_koji_je_doneo_odluku", "organ koji je doneo odluku"));
		podaci.appendChild(element("broj_zalbe", "000-00-0000/0000-00"));
		podaci.appendChild(element("datum", "10.10.2010."));
		return podaci;
	}
	
	public Element podaciOPovereniku() {
		Element podaci = document.createElementNS(TARGET_NAMESPACE, "podaci_o_povereniku");
		podaci.appendChild(adresa("Beograd", "Bulevar kralja", "15"));
		podaci.appendChild(element("naziv_poverenika", "Povereniku za informacije od javnog značaja i zaštitu podataka o ličnosti"));
		return podaci;
	}
	
	public Element adresa(String gradS, String ulicaS, String brojS) {
		Element adresa = document.createElementNS(TARGET_NAMESPACE, "adresa");
		adresa.appendChild(element("grad", gradS));
		adresa.appendChild(element("ulica", ulicaS));
		adresa.appendChild(element("broj", brojS));
		return adresa;
	}
	
	public Element naslov(String nivo, String stil, String naslovS) {
		Element naslov = document.createElementNS(TARGET_NAMESPACE, "naslov");
		naslov.setAttribute("zalba:nivo", nivo);
		if(!stil.contentEquals("")) {
			naslov.setAttribute("zalba:stil", "bold");			
		}
		naslov.appendChild(text(naslovS));
		return naslov;
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

		/*if (args.length != 1) {

			filePath = "data/xml/resenje.xml";

			System.out.println("[INFO] No input file, using default \""	+ filePath + "\"");

		} else {
			filePath = args[0];
		}*/

		DOMWriterZalbaNaOdluku handler = new DOMWriterZalbaNaOdluku();

		// Kreiranje Document Ä?vora
		handler.createDocument();

		// Generisanje DOM stabla
		handler.generateDOM();
		
		// Prikaz sadrÅ¾aja (isprobati sa FileOutputStream-om)
		handler.transform(System.out);
		
		
		try {
			handler.transform(new FileOutputStream("data/out/zalbanaodlukuW.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
