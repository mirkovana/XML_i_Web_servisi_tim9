package resenje;

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
public class DOMWriterResenje {

	private static String TARGET_NAMESPACE = "http://www.projekat.org/resenje";

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
		Element zalba = document.createElementNS(TARGET_NAMESPACE, "zalba");
		document.appendChild(zalba);
		
		zalba.setAttributeNS(XSI_NAMESPACE, "xsi:schemaLocation", "http://www.projekat.org/resenje resenje.xsd");
		zalba.setAttribute("xmlns:resenje", "http://www.projekat.org/resenje");
		zalba.setAttribute("status", "odbacena");		
		zalba.appendChild(uvod());
		zalba.appendChild(sadrzaj());
	}
	
	public Element osoba(String imeS, String prezimeS) {
		Element osoba = document.createElementNS(TARGET_NAMESPACE, "osoba");
			Element ime = document.createElementNS(TARGET_NAMESPACE, "ime");
				ime.appendChild(document.createTextNode(imeS));
				osoba.appendChild(ime);
			Element prezime = document.createElementNS(TARGET_NAMESPACE, "prezime");
				prezime.appendChild(document.createTextNode(prezimeS));
				osoba.appendChild(prezime);
		return osoba;
	}
	
	public Text text(String text) {
		return document.createTextNode(text);
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
	
	public Element element(String name, String text) {
		Element element = document.createElementNS(TARGET_NAMESPACE, name);
		element.appendChild(text(text));
		return element;
	}
	
	public Element uvod() {
		Element uvod = document.createElementNS(TARGET_NAMESPACE, "uvod");

			Element paragraf = document.createElementNS(TARGET_NAMESPACE, "paragraf");
				paragraf.appendChild(text("Poverenik za informacije od javnog značaja i zaštitu podataka o ličnosti, u postupku po albi koju je izjavio"));
				paragraf.appendChild(osoba("OsobaIme1", "OsobaPrezime1"));
				paragraf.appendChild(text(" zbog nepostupanja "));
				paragraf.appendChild(institucija("InstitucijaNaziv", "InstitucijaMesto"));
				paragraf.appendChild(text("sa privremenim sedištem u "));
				paragraf.appendChild(mesto("mesto sedista"));
				paragraf.appendChild(text("po njegovom zahtevu od "));
				paragraf.appendChild(element("datum", "20.10.2010."));
				paragraf.appendChild(text("godine za pristup informacijama od javnog značaja, na osnovu "));
				paragraf.appendChild(zakon("člana 35.", "stav 1.", "tacka 5", 
							"Zakona o slobodnom pristupu informacijama od javnog značaja",
							"(„Sl. glasnik RS“, br. 120/04, 54/07, 104/09 i 36/10)"));
				paragraf.appendChild(text("a u vezi sa "));
				paragraf.appendChild(zakon("clanom 4.", "", "tacka 22.", 
									"Zakona o zaštiti podataka o ličnosti",
									"(„Sl. glasnik RS“, broj 87/18)"));
				paragraf.appendChild(text("kao i "));
				paragraf.appendChild(zakon("člana 23. i člana 24.", "stav 4.", "", 
						"Zakona o slobodnom pristupu informacijama od javnog značaja",""));
	
				paragraf.appendChild(text("i"));				
				paragraf.appendChild(zakon("clana 173.", "stav2.", "",
							"Zakona o opštem upravnom postupku", 
							"(„Sl. glasnik RS“, br. 18/2016 i 95/2018-autentično tumačenje)"));
				paragraf.appendChild(text(", donosi:"));
		
		uvod.appendChild(element("broj", "123-45-6789/1011-12"));
		uvod.appendChild(element("datum", "10.10.2010."));
		uvod.appendChild(paragraf);
		return uvod;
	}
	
	public Element sadrzaj() {
		Element sadrzaj = document.createElementNS(TARGET_NAMESPACE, "sadrzaj");
		sadrzaj.appendChild(resenje());
		sadrzaj.appendChild(obrazlozenje());
		sadrzaj.appendChild(poverenik());
		return sadrzaj;
	}
	
	public Element resenje() {
		Element resenje = document.createElementNS(TARGET_NAMESPACE, "resenje");
		
		Element naslov = document.createElementNS(TARGET_NAMESPACE, "naslov");
			naslov.setAttribute("resenje:nivo", "2");
			naslov.appendChild(text("RESENJE"));
		
		Element paragraf1 = document.createElementNS(TARGET_NAMESPACE, "paragraf");
			paragraf1.appendChild(text("Nalaže se "));
			paragraf1.appendChild(institucija("Učiteljskom fakultetu", "Prizrenu"));
			paragraf1.appendChild(text("sa privremenim sedištem u "));
			paragraf1.appendChild(mesto("Leposaviću"));
			paragraf1.appendChild(text(", da bez odlaganja, a najkasnije u roku od pet dana od dana prijema ovog rešenja, obavesti "));
			paragraf1.appendChild(osoba("A","A"));
			paragraf1.appendChild(text(", da li poseduje tražene informacije odnosno dokument u kome su iste sadržane, i to: Ugovor o radu koji je kao poslednji potpisan između  tog Fakulteta i "));
			paragraf1.appendChild(osoba("C","C"));
			paragraf1.appendChild(text(", te da mu, ukoliko takav dokument poseduje dostavi kopiju istog elektronskom poštom na adresu … ili poštom,"));
		
		Element paragraf2 = document.createElementNS(TARGET_NAMESPACE, "paragraf");
			paragraf2.appendChild(text("II O izvršenju rešenja "));
			paragraf2.appendChild(institucija("Učiteljski fakultet", "Prizrenu"));
			paragraf2.appendChild(text("sa privremenim sedištem u "));
			paragraf2.appendChild(mesto("Leposaviću"));
			paragraf2.appendChild(text(", će obavestiti Poverenika u roku od sedam dana od prijema ovog rešenja."));
			
		resenje.appendChild(naslov);
		resenje.appendChild(paragraf1);
		resenje.appendChild(paragraf2);
		
		return resenje;
	}
	
	public Element obrazlozenje() {
		Element obrazlozenje = document.createElementNS(TARGET_NAMESPACE, "obrazlozenje");

		Element naslov = document.createElementNS(TARGET_NAMESPACE, "naslov");
			naslov.setAttribute("resenje:nivo", "2");
			naslov.appendChild(text("OBRAZLOZENJE"));
		
		Element paragraf1 = document.createElementNS(TARGET_NAMESPACE, "paragraf");
			paragraf1.appendChild(osoba("A", "A"));
			paragraf1.appendChild(text(", kao tražilac informacija, izjavio je dana "));
			paragraf1.appendChild(element("datum", "07.05.2020."));
			paragraf1.appendChild(text("godine žalbu Povereniku, zbog nepostupanja "));
			paragraf1.appendChild(institucija("Učiteljskog fakulteta", "Prizrenu"));
			paragraf1.appendChild(text("sa privremenim sedištem u "));
			paragraf1.appendChild(mesto("Leposavicu"));
			paragraf1.appendChild(text("po njegovom zahtevu od "));
			paragraf1.appendChild(element("datum", "16.04.2020."));
			paragraf1.appendChild(text("godine za pristup informacijama od javnog značaja i u prilogu dostavio kopiju istog."));
		
		Element paragraf2 = document.createElementNS(TARGET_NAMESPACE, "paragraf");
			paragraf2.appendChild(text("Protiv ovog rešenja nije dopuštena žalba već se, u skladu sa "));
			paragraf2.appendChild(zakon("","","","Zakonom o upravnim sporovima", ""));
			paragraf2.appendChild(text(", može pokrenuti upravni spor tužbom "));
			paragraf2.appendChild(institucija("Upravnom sudu", "Beogradu"));
			paragraf2.appendChild(text("u roku od 30 dana od dana prijema rešenja. Taksa na tužbu iznosi "));
			paragraf2.appendChild(element("taksa", "390.00"));
			paragraf2.appendChild(text("dinara"));
		
		obrazlozenje.appendChild(naslov);
		obrazlozenje.appendChild(paragraf1);
		obrazlozenje.appendChild(paragraf2);
		
		return obrazlozenje;
	}
	
	public Element poverenik() {
		Element poverenik = document.createElementNS(TARGET_NAMESPACE, "poverenik");
		poverenik.appendChild(element("ime", "ImePoverenika"));
		poverenik.appendChild(element("prezime", "PrezimePoverenika"));
		return poverenik;
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

		DOMWriterResenje handler = new DOMWriterResenje();

		// Kreiranje Document Ä?vora
		handler.createDocument();

		// Generisanje DOM stabla
		handler.generateDOM();
		
		// Prikaz sadrÅ¾aja (isprobati sa FileOutputStream-om)
		handler.transform(System.out);
		
		
		try {
			handler.transform(new FileOutputStream("data/out/resenjeW.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
