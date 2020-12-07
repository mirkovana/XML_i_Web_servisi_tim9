package zalbazbogcutanja;

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
public class DOMWriterZalbaZbogCutanja {

	private static String TARGET_NAMESPACE = "http://www.projekat.org/zalbazbogcutanja";

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
		Element zalba = document.createElementNS(TARGET_NAMESPACE, "zalba_cutanje");
		document.appendChild(zalba);
		
		zalba.setAttributeNS(XSI_NAMESPACE, "xsi:schemaLocation", "http://www.projekat.org/zalbazbogcutanja zalbazbogcutanja.xsd");
		zalba.setAttribute("xmlns:zc", "http://www.projekat.org/zalbazbogcutanja");

		zalba.appendChild(naslov("2", "bold", "ŽALBA KADA ORGAN VLASTI NIJE POSTUPIO/ nije postupio u celosti/ PO ZAHTEVU TRAŽIOCA U ZAKONSKOM ROKU (ĆUTANjE UPRAVE)"));
		zalba.appendChild(podaciOPovereniku());
		zalba.appendChild(teloZalbe());
		zalba.appendChild(podaciOPodnosiocu());
		zalba.appendChild(podaciOMestuDatumu());
	}
	
	public Element teloZalbe() {
		Element telo = document.createElementNS(TARGET_NAMESPACE, "telo_zalbe");
		telo.appendChild(text("U skladu sa "));
		telo.appendChild(zakon("članom 22.", "", "", "Zakona o slobodnom pristupu informacijama od javnog značaja", ""));
		telo.appendChild(text(" podnosim: ZALBU protiv"));
		telo.appendChild(element("naziv_organa", "naziv organa"));
		telo.appendChild(text("zbog toga sto organ vlasti nije:"));
		telo.appendChild(razlozi());
		telo.appendChild(text("po mom zahtevu za slobodan pristup informacijama od javnog značaja koji sam podneo tom organu dana"));
		telo.appendChild(element("datum", "10.10.2010."));
		telo.appendChild(text("godine, a kojim sam tražio/la da mi se u skladu sa "));
		telo.appendChild(zakon("", "", "", "Zakona o slobodnom pristupu informacijama od javnog značaja", ""));
		telo.appendChild(element("podaci_o_zahtevu_i_informaciji", "hasda hahda hsh adha adjhajda"));
		telo.appendChild(text("Na osnovu iznetog, predlažem da Poverenik uvaži moju žalbu i omogući mi pristup traženoj/im informaciji/ma. Kao dokaz , uz žalbu dostavljam kopiju zahteva sa dokazom o predaji organu vlasti"));
		telo.appendChild(element("napomena", "Kod žalbe zbog nepostupanju po zahtevu u celosti, treba priložiti i dobijeni odgovor organa vlasti."));
		return telo;
	}
	
	public Element razlozi() {
		Element razlozi = document.createElementNS(TARGET_NAMESPACE, "razlozi");
		razlozi.appendChild(razlog("nije postupio", "1"));
		razlozi.appendChild(razlog("nije postupio u celosti", "2"));
		razlozi.appendChild(razlog("nije postupio u zakonskom roku", "3"));
		return razlozi;
	}
	public Element razlog(String razlogS, String id) {
		Element razlog = document.createElementNS(TARGET_NAMESPACE, "razlog");
		razlog.setAttribute("podvuceno", "false");
		razlog.setAttribute("id", id);
		razlog.appendChild(text(razlogS));
		return razlog;
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
	
	public Element podaciOMestuDatumu() {
		Element podaci = document.createElementNS(TARGET_NAMESPACE, "podaci_o_mestu_i_datumu_podnosenja_zalbe");
		podaci.appendChild(element("mesto", "Mesto1"));
		podaci.appendChild(element("datum", "10.10.2010."));
		return podaci;
	}
	
	public Element podaciOPodnosiocu() {		
		Element podaci = document.createElementNS(TARGET_NAMESPACE, "podaci_o_podnosiocu_zalbe");
		podaci.appendChild(adresa("Grad2", "Ulica2", "10"));
		podaci.appendChild(element("ime", "Ime2"));
		podaci.appendChild(element("prezime", "Prezime2"));
		podaci.appendChild(element("drugi_podaci_za_kontakt", "kjahdajbdkja podaci"));
		podaci.appendChild(element("potpis", "potpis2"));
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
		podaci.appendChild(adresa("Grad1", "Ulica1", "15"));
		podaci.appendChild(element("naziv_poverenika", "Povereniky za informacije od javnog značaja i zaštitu podataka o ličnosti"));
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
		naslov.setAttribute("zc:nivo", nivo);
		if(!stil.contentEquals("")) {
			naslov.setAttribute("zc:stil", "bold");			
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

		DOMWriterZalbaZbogCutanja handler = new DOMWriterZalbaZbogCutanja();

		// Kreiranje Document Ä?vora
		handler.createDocument();

		// Generisanje DOM stabla
		handler.generateDOM();
		
		// Prikaz sadrÅ¾aja (isprobati sa FileOutputStream-om)
		handler.transform(System.out);
		
		
		try {
			handler.transform(new FileOutputStream("data/out/zalbazbogcutanjaW.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
