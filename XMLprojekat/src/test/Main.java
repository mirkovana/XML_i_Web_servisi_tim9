package test;

import java.util.Scanner;

import obavestenje.DOMParserObavestenje;
import resenje.DOMParserResenje;
import zahtev.DOMParserZahtev;
import zalbanaodluku.DOMParserZalbaNaOdluku;
import zalbazbogcutanja.DOMParserZalbaZbogCutanja;

public class Main {

	public static void main(String args[]) {
	
		System.out.println("Prikaz sadrzaja DOM stabla parsiranog XML dokumenta.");
		
		Scanner scanner = new Scanner(System.in);
		String choice = "";
		
		while (!choice.equals("x")) {
			System.out.println("\n[INPUT] Unesite \n"
					+ "0 - obavestenje.xml, \n"
					+ "1 - resenje.xml, \n"
					+ "2 - zahtev.xml, \n"
					+ "3 - zalbanaodluku.xml, \n"
					+ "4 - zalbazbogucatnja.xml, \n"
					+ "x - kraj: \n");
	    	choice = scanner.next();
	    	
	    	if (choice.equals("0")) {
	    		DOMParserObavestenje.test(scanner);
	    	}else if (choice.equals("1")) {
	    		DOMParserResenje.test(scanner);
	    	}else if (choice.equals("2")) {
	    		DOMParserZahtev.test(scanner);
	    	}else if (choice.equals("3")) {
	    		DOMParserZalbaNaOdluku.test(scanner);
	    	}else if (choice.equals("4")) {
	    		DOMParserZalbaZbogCutanja.test(scanner);
	    	}else if (choice.equals("x")) {
	    		break;
	    	}else {
	    		System.out.println("Nepoznata komanda");
	    	}
		}
		scanner.close();
		System.out.println("[INFO] Kraj.");
	}
}
