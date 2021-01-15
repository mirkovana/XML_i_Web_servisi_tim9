package com.xml.organvlasti.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.xml.organvlasti.dto.RequestDTO;
import com.xml.organvlasti.dto.RequestItem;
import com.xml.organvlasti.model.request.Zahtev;
import com.xml.organvlasti.service.RequestService;

@RestController()
@RequestMapping(value = "api/request")
@CrossOrigin()
public class RequestController {

	@Autowired
	private RequestService service;
	
	/*@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<RequestDTO> saveRequest(@RequestBody RequestDTO dto) throws Exception {
		System.out.println("controller saveRequest = ");
		service.save(dto);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}*/
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveRequestXML(@RequestBody String xmlString) throws Exception {
		System.out.println("controller saveRequest as xml= ");
		service.save(xmlString);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping(value = "/all")
	@CrossOrigin
	public ResponseEntity<ArrayList<RequestItem>> getAll() throws XMLDBException, ParserConfigurationException, SAXException, IOException {
		System.out.println("controller = ");
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{username}/all")
	@CrossOrigin
	public ResponseEntity<ArrayList<RequestItem>> getAllForUser(@PathVariable("username") String username) throws XMLDBException, ParserConfigurationException, SAXException, IOException {
		System.out.println("controller = ");
		return new ResponseEntity<>(service.getAllForUser(username), HttpStatus.OK);
	}
	
	@PutMapping(value = "deny/{broj}")
	@CrossOrigin
	public ResponseEntity<String> denyRequest(@PathVariable("broj") String broj){
		System.out.println("deny request = " + broj);
		service.denyRequest(broj);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	/*@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<Zahtev> saveRequestJax(@RequestBody Zahtev dto) throws Exception {
		System.out.println("controller saveRequest xml = ");
		System.out.println("Zahtev dto = " + dto);
		Zahtev zahtev = service.saveJax(dto);
		return new ResponseEntity<>(zahtev, HttpStatus.OK);
	}*/
	
	@DeleteMapping(value = "/{broj}")
	public ResponseEntity<Void> deletePaper(@PathVariable("broj") String broj) {
		service.deleteRequest(broj);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = "/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getRequestHTML(@PathVariable("id") String id) {
		System.out.println("controller html id = " + id);
		String result = service.getHTML(id);
		System.out.println("constroller result = " + result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
