package com.example.demo.marshalling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.klaseObavestenja.Obavestenje;
import com.example.demo.klaseZalbaNaOdluku.ZalbaNaOdluku;
import com.example.demo.klaseZalbaZbogCutanja.ZalbaCutanje;
import com.example.demo.resenje.DOMParserResenje;
import com.example.demo.resenje.DOMWriterResenje;
import com.example.demo.zahtev.DOMParserZahtev;
import com.example.demo.zahtev.DOMWriterZahtev;


@RestController
public class Marshalling {
	
	@GetMapping(path = "/marshalling_obavestenje")
	public void marshallingObavestenje() {
		try {
			System.out.println("[INFO] Obavestenje: JAXB marshalling.\n");
			
			// DefiniÅ¡e se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("com.example.demo.klaseObavestenja");
			
			// Unmarshaller je objekat zaduÅ¾en za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();

			Obavestenje obavestenje = (Obavestenje) unmarshaller.unmarshal(new File("./data/Obavestenje.xml"));
			
			// Izmena nad objektnim modelom dodavanjem novog odseka
//			fakultet.getOdsek().add(createOdsek("23", "GRID"));
			
			// Marshaller je objekat zaduÅ¾en za konverziju iz objektnog u XML model
			Marshaller marshaller = context.createMarshaller();
			
			// PodeÅ¡avanje marshaller-a
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Umesto System.out-a, moÅ¾e se koristiti FileOutputStream
			marshaller.marshal(obavestenje, new FileOutputStream("./gen/obavestenje.xml"));
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(path = "/marshalling_zalbaCutanje")
	public void marshallingZalbaZbogCutanja() {
		try {
			System.out.println("[INFO] Example 2: JAXB unmarshalling/marshalling.\n");
			
			// DefiniÅ¡e se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("com.example.demo.klaseZalbaZbogCutanja");
			
			// Unmarshaller je objekat zaduÅ¾en za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();

			ZalbaCutanje zalbaCutanje = (ZalbaCutanje) unmarshaller.unmarshal(new File("./data/zalbazbogcutanjacir.xml"));
			
			// Izmena nad objektnim modelom dodavanjem novog odseka
//			fakultet.getOdsek().add(createOdsek("23", "GRID"));
			
			// Marshaller je objekat zaduÅ¾en za konverziju iz objektnog u XML model
			Marshaller marshaller = context.createMarshaller();
			
			// PodeÅ¡avanje marshaller-a
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Umesto System.out-a, moÅ¾e se koristiti FileOutputStream
			marshaller.marshal(zalbaCutanje, new FileOutputStream("./gen/zalbaZbogCutanja.xml"));
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(path = "/marshalling_zalbaOdluka")
	public void marshallingZalbaOdluka() {
		try {
			System.out.println("[INFO] Example 2: JAXB unmarshalling/marshalling.\n");
			
			// DefiniÅ¡e se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("com.example.demo.klaseZalbaNaOdluku");
			
			// Unmarshaller je objekat zaduÅ¾en za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();

			ZalbaNaOdluku obavestenje = (ZalbaNaOdluku) unmarshaller.unmarshal(new File("./data/zalbanaodlukucir.xml"));
			
			// Izmena nad objektnim modelom dodavanjem novog odseka
//			fakultet.getOdsek().add(createOdsek("23", "GRID"));
			
			// Marshaller je objekat zaduÅ¾en za konverziju iz objektnog u XML model
			Marshaller marshaller = context.createMarshaller();
			
			// PodeÅ¡avanje marshaller-a
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Umesto System.out-a, moÅ¾e se koristiti FileOutputStream
			marshaller.marshal(obavestenje, new FileOutputStream("./gen/zalbaNaOdluku.xml"));
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(path = "/marshalling_resenje")
	public void writeResenje() {
		DOMWriterResenje.test();
	}
	@GetMapping(path = "/marshalling_zahtev")
	public void writeZahtev() {
		DOMWriterZahtev.test();
	}
}
