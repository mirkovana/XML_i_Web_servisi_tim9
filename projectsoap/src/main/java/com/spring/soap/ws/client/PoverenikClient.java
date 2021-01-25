package com.spring.soap.ws.client;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.spring.soap.ws.hello.Poverenik;

public class PoverenikClient {

    public void testIt() {
    	
		try {
			//kreiranje web servisa
			URL wsdlLocation = new URL("http://localhost:8071/ws/poverenik?wsdl");
			QName serviceName = new QName("http://soap.spring.com/ws/poverenik", "PoverenikService");
			QName portName = new QName("http://soap.spring.com/ws/poverenik", "PoverenikPort");

			Service service = Service.create(wsdlLocation, serviceName);
			
			Poverenik poverenik = service.getPort(portName, Poverenik.class); 
			
			//poziv web servisa
			String response = poverenik.saveExplanation("");
			System.out.println("Response from WS: " + response);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		
		PoverenikClient client = new PoverenikClient();
		client.testIt();
    }

}