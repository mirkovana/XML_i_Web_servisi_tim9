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


@RestController
public class Marshalling {
	@GetMapping(path = "/marshalling_obavestenje")
	public void marshallingObavestenje() {
		try {
			System.out.println("[INFO] Example 2: JAXB unmarshalling/marshalling.\n");
			
			// DefiniÅ¡e se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("com.example.demo.klaseObavestenja");
			
			// Unmarshaller je objekat zaduÅ¾en za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();

			Obavestenje obavestenje = (Obavestenje) unmarshaller.unmarshal(new File("./data/Obavestenje1.xml"));
			
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
}
