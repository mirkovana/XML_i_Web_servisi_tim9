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
			ZalbaCutanje zalbaCutanje = (ZalbaCutanje) unmarshaller
					.unmarshal(new File("./data/zalbazbogcutanjacir.xml"));

			// Prikazuje unmarshallovan objekat
			printZalbaCutanje(zalbaCutanje);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private void printZalbaCutanje(ZalbaCutanje zalbaCutanje) {

		// Prikaz naziva fakulteta (getter metoda)
		System.out.println("- Naslov zalbe: " + zalbaCutanje.getNaslov() + "\n");
		System.out.println("- Podaci o povereniku: " + "\n");
		System.out.println("    Naziv poverenika: " + zalbaCutanje.getPodaciOPovereniku().getNazivPoverenika() + "\n");
		System.out.println("    Adresa poverenika: " + zalbaCutanje.getPodaciOPovereniku().getAdresa().getUlica() +" "
		+zalbaCutanje.getPodaciOPovereniku().getAdresa().getBroj() + ","+ zalbaCutanje.getPodaciOPovereniku().getAdresa().getGrad()+ "\n");
		System.out.println("- Podaci o podnosiocu zalbe: " + "\n");
		System.out.println("    Ime podnosioca: "+zalbaCutanje.getPodaciOPodnosiocuZalbe().getIme()+"\n");
		System.out.println("    Prezime podnosioca: "+zalbaCutanje.getPodaciOPodnosiocuZalbe().getPrezime()+"\n");
		System.out.println("    Adresa podnosioca: "+zalbaCutanje.getPodaciOPodnosiocuZalbe().getAdresa().getUlica()+" "+zalbaCutanje.getPodaciOPodnosiocuZalbe().getAdresa().getBroj()+", "+zalbaCutanje.getPodaciOPodnosiocuZalbe().getAdresa().getGrad()+"\n");
		System.out.println("    Drugi podaci za kontakt podnosioca: "+zalbaCutanje.getPodaciOPodnosiocuZalbe().getDrugiPodaciZaKontakt()+"\n");
		System.out.println("- Podaci o mestu i datumu podnosenja zalbe: " + zalbaCutanje.getPodaciOMestuIDatumuPodnosenjaZalbe().getMesto()+", "+zalbaCutanje.getPodaciOMestuIDatumuPodnosenjaZalbe().getDatum()+ "\n");
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
			ZalbaNaOdluku zalbaNaOdluku = (ZalbaNaOdluku) unmarshaller
					.unmarshal(new File("./data/zalbanaodlukucir.xml"));

			// Prikazuje unmarshallovan objekat
			printZalbaOdluka(zalbaNaOdluku);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private void printZalbaOdluka(ZalbaNaOdluku zalbaNaOdluku) {

		// Prikaz naziva fakulteta (getter metoda)
		System.out.println("- Naslov zalbe: " + zalbaNaOdluku.getNaslov() + "\n");
		System.out.println("- Podaci o povereniku: " + "\n");
		System.out.println("    Naziv poverenika: " + zalbaNaOdluku.getPodaciOPovereniku().getNazivPoverenika() + "\n");
		System.out.println("    Adresa poverenika: " + zalbaNaOdluku.getPodaciOPovereniku().getAdresa().getUlica() +" "
		+zalbaNaOdluku.getPodaciOPovereniku().getAdresa().getBroj() + ","+ zalbaNaOdluku.getPodaciOPovereniku().getAdresa().getGrad()+ "\n");
		System.out.println("- Sadrzaj zalbe: " + "\n");
		System.out.println("    Datum podnosenja odbijenog zahteva: " + zalbaNaOdluku.getSadrzajZalbe().getDatum() +"\n");
		System.out.println("    Razlog pobijanja odluke: "+ zalbaNaOdluku.getSadrzajZalbe().getZbogCegaSePobijaOdluka() +"\n");
		System.out.println("- Podaci o podnosiocu zalbe: " + "\n");
		System.out.println("    Ime podnosioca: "+zalbaNaOdluku.getPodaciOPodnosiocuZalbe().getIme()+"\n");
		System.out.println("    Prezime podnosioca: "+zalbaNaOdluku.getPodaciOPodnosiocuZalbe().getPrezime()+"\n");
		System.out.println("    Adresa podnosioca: "+zalbaNaOdluku.getPodaciOPodnosiocuZalbe().getAdresa().getUlica()+" "+zalbaNaOdluku.getPodaciOPodnosiocuZalbe().getAdresa().getBroj()+", "+zalbaNaOdluku.getPodaciOPodnosiocuZalbe().getAdresa().getGrad()+"\n");
		System.out.println("    Drugi podaci za kontakt podnosioca: "+zalbaNaOdluku.getPodaciOPodnosiocuZalbe().getDrugiPodaciZaKontakt()+"\n");
		System.out.println("- Podaci o mestu i datumu podnosenja zalbe: " + zalbaNaOdluku.getPodaciOMestuIDatumuPodnosenjaZalbe().getMesto()+", "+zalbaNaOdluku.getPodaciOMestuIDatumuPodnosenjaZalbe().getDatum()+ "\n");
	}

}
