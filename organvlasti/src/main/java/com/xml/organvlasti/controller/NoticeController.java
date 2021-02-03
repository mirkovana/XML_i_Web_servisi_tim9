package com.xml.organvlasti.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.xml.organvlasti.model.noticeResponse.NoticeListResponse;
import com.xml.organvlasti.service.NoticeService;

@RestController()
@RequestMapping(value = "api/notice")
@CrossOrigin()
public class NoticeController {

	@Autowired
	private NoticeService service;

	@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveNotice(@RequestBody String xml) throws Exception {
		System.out.println("controller saveNotice = ");
		service.save(xml);
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
	
	@RequestMapping(path = "/pdf/{broj}")
    public ResponseEntity<?> getPDF(@PathVariable("broj") String broj, HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] bytes = service.getPdfBytes(broj);	        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + broj + ".pdf") 
                .contentType(MediaType.APPLICATION_PDF) 
                .body(bytes);       
    }
	
	@CrossOrigin
	@PostMapping(value = "/napredna-pretraga")
	public ResponseEntity<?> naprednaPretraga(@RequestParam("nazivOrgana") String nazivOrgana,
			@RequestParam("sedisteOrgana") String sedisteOrgana, @RequestParam("ime") String ime,
			@RequestParam("prezime") String prezime, @RequestParam("datum") String datum,
			@RequestParam("brojPredmeta") String brojPredmeta) {
		System.out.println(prezime);
		Map<String, String> params = new HashMap<String, String>();
		params.put("nazivOrgana", nazivOrgana);
		params.put("sedisteOrgana", sedisteOrgana);
	params.put("ime", ime);
	params.put("prezime", prezime);
	params.put("datum", datum);
	params.put("broj_predmeta", brojPredmeta);
	ArrayList<Map<String, String>> ret = null;
	try {
		ret = service.search(params);
	} catch (IOException e) {
		e.printStackTrace();
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	for (Map<String, String> map : ret) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		System.out.println("\n");
	}
	
	
	System.out.println(ret.size() + " OVO JE SIZE");
	return new ResponseEntity(ret, HttpStatus.OK);
}
	
}
