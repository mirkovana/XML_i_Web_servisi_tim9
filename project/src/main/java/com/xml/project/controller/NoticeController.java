package com.xml.project.controller;

import java.io.IOException;

import javax.xml.bind.JAXBException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.xml.project.dto.NoticeDTO;
import com.xml.project.model.noticeResponse.NoticeListResponse;
import com.xml.project.model.zahtevResponse.RequestListResponse;
import com.xml.project.service.NoticeService;

@RestController()
@RequestMapping(value = "api/notice")
@CrossOrigin()
public class NoticeController {

	@Autowired
	private NoticeService service;

	@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveNotice(@RequestBody String dto) throws Exception {
		System.out.println("controller saveNotice = ");
		service.save(dto);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping(value = "/all", produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<NoticeListResponse> getAll() throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException {
		System.out.println("controller = ");
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{username}/organvlasti/all",  produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<NoticeListResponse> getAllForOrganVlastiUsername(@PathVariable("username") String username) throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException {
		System.out.println("controller = ");
		return new ResponseEntity<>(service.getAllForOrganVlastiUsername(username), HttpStatus.OK);
	}

	@GetMapping(value = "/{username}/user/all",  produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<NoticeListResponse> getAllForUserUsername(@PathVariable("username") String username) throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException {
		System.out.println("controller = ");
		return new ResponseEntity<>(service.getAllForUserUsername(username), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{broj}")
	public ResponseEntity<String> deleteNotice(@PathVariable("broj") String broj) {
		service.deleteNotice(broj);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	@GetMapping(value = "/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getNoticelHTML(@PathVariable("id") String id) {
		System.out.println("controller html id = " + id);
		String result = service.getHTML(id);
		System.out.println("constroller result = " + result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
