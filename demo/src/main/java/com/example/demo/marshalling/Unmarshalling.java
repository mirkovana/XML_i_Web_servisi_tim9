package com.example.demo.marshalling;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.klaseObavestenja.Obavestenje;
import com.example.demo.klaseZalbaNaOdluku.ZalbaNaOdluku;
import com.example.demo.klaseZalbaZbogCutanja.ZalbaCutanje;



@RestController
public class Unmarshalling {

	
	@GetMapping(path = "/unmarshalling_obavestenje")
	public void unmarshallingObavestenje() {
		System.out.println();
		try {
			
			System.out.println("[INFO] Example 1: JAXB unmarshalling.\n");
			
			// DefiniÅ¡e se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("com.example.demo.klaseObavestenja");
			
			// Unmarshaller je objekat zaduÅ¾en za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// Unmarshalling generiÅ¡e objektni model na osnovu XML fajla 
			Obavestenje obavestenje = (Obavestenje) unmarshaller.unmarshal(new File("./data/Obavestenje1.xml"));

			// Prikazuje unmarshallovan objekat
//			System.out.println(obavestenje.getNaslov());
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(path = "/unmarshalling_zalbaCutanje")
	public void unmarshallingZalbaCutanje() {
		System.out.println();
		try {
			
			System.out.println("[INFO] Example 1: JAXB unmarshalling.\n");
			
			// DefiniÅ¡e se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("com.example.demo.klaseZalbaZbogCutanja");
			
			// Unmarshaller je objekat zaduÅ¾en za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// Unmarshalling generiÅ¡e objektni model na osnovu XML fajla 
			ZalbaCutanje zalbaCutanje = (ZalbaCutanje) unmarshaller.unmarshal(new File("./data/zalbazbogcutanjacir.xml"));

			// Prikazuje unmarshallovan objekat
//			System.out.println(obavestenje.getNaslov());
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@GetMapping(path = "/unmarshalling_zalbaOdluka")
	public void unmarshallingzalbaOdluka() {
		System.out.println();
		try {
			
			System.out.println("[INFO] Example 1: JAXB unmarshalling.\n");
			
			// DefiniÅ¡e se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("com.example.demo.klaseZalbaNaOdluku");
			
			// Unmarshaller je objekat zaduÅ¾en za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// Unmarshalling generiÅ¡e objektni model na osnovu XML fajla 
			ZalbaNaOdluku zalbaNaOdluku = (ZalbaNaOdluku) unmarshaller.unmarshal(new File("./data/zalbanaodlukucir.xml"));

			// Prikazuje unmarshallovan objekat
//			System.out.println(obavestenje.getNaslov());
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
