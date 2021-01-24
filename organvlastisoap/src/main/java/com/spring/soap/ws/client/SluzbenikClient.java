package com.spring.soap.ws.client;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.spring.soap.ws.hello.Sluzbenik;

public class SluzbenikClient {

    public void testIt() {
    	
		try {
			//kreiranje web servisa
			URL wsdlLocation = new URL("http://localhost:8050/ws/sluzbenik?wsdl");
			QName serviceName = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikService");
			QName portName = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikPort");

			Service service = Service.create(wsdlLocation, serviceName);
			
			Sluzbenik sluzbenik = service.getPort(portName, Sluzbenik.class); 
			
			//poziv web servisa
			String response = sluzbenik.saveDecisionAppeal("");
			System.out.println("Response from WS: " + response);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		
		SluzbenikClient client = new SluzbenikClient();
		client.testIt();
    }

}