package com.xml.project.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.xml.project.model.decisionAppealResponse.DAppealListResponse;
import com.xml.project.service.DecisionAppealService;
import com.xml.project.soap.Sluzbenik;

@RestController()
@RequestMapping(value = "api/decision-appeals")
@CrossOrigin()
public class DecisionAppealController {

	@Autowired
	private DecisionAppealService service;
	
	/*@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<DecisionAppealDTO> saveDecisionAppeal(@RequestBody DecisionAppealDTO dto) throws Exception {
		System.out.println("controller saveDecisionAppeal = ");
		service.save(dto);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}*/
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveDecisionAppeal(@RequestBody String xmlString) throws Exception {
		System.out.println("controller saveDecisionAppeal = ");
		service.save(xmlString);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	/*@GetMapping(value = "/all", produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<RequestListResponse> getAll() throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException {
		System.out.println("controller = ");
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}*/
	
	@GetMapping(value = "/{username}/all",  produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<DAppealListResponse> getAllForUsername(@PathVariable("username") String username){
		System.out.println("controller getallforusername = " + username);
		try {
			return new ResponseEntity<>(service.getAllForUsername(username), HttpStatus.OK);
		} catch (XMLDBException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);	
		} catch (JAXBException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);	
		} catch (SAXException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);	
		}
	}
	
	@GetMapping(value = "/all", produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<DAppealListResponse> getAll() throws MalformedURLException{
		System.out.println("controller get all = ");
		try {
			return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
		} catch (XMLDBException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);	
		} catch (JAXBException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (SAXException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/requestExplanation/{broj}", produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<String> requestExplanation(@PathVariable("broj") String broj){
		System.out.println("controller requestExplanation = " + broj);
		try {
			service.updateState(broj);
			String appealXml = service.getAppealXml(broj);
			sendAppeal(appealXml);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		} catch (InstantiationException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		} catch (TransformerException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		} catch (XMLDBException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{broj}")
	public ResponseEntity<Void> deleteAppeal(@PathVariable("broj") String broj) {
		try {
			service.deleteAppeal(broj);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (InstantiationException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (XMLDBException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getDecisionAppealHTML(@PathVariable("id") String id) {
		System.out.println("controller html id = " + id);
		String result = service.getHTML(id);
		System.out.println("constroller result = " + result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	private void sendAppeal(String xml) {
		System.out.println("sendAppeal");
		try {
			URL wsdlLocation = new URL("http://localhost:8050/ws/sluzbenik?wsdl");
			QName serviceName = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikService");
			QName portName = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikPort");

			Service service2 = Service.create(wsdlLocation, serviceName);
			
			Sluzbenik sluzbenik = service2.getPort(portName, Sluzbenik.class); 
			
			//poziv web servisa
			String response = sluzbenik.saveDecisionAppeal(xml);
			System.out.println("Response from WS: " + response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
