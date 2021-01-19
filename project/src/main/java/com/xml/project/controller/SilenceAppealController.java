package com.xml.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xml.project.service.SilenceAppealService;

@RestController()
@RequestMapping(value = "api/silence-appeals")
@CrossOrigin()
public class SilenceAppealController {

	@Autowired
	private SilenceAppealService service;
	
	/*@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<SilenceAppealDTO> saveSilenceAppeal(@RequestBody SilenceAppealDTO dto) throws Exception {
		System.out.println("controller saveSilenceAppeal= ");
		service.save(dto);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}*/
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveSilenceAppeal(@RequestBody String dto) throws Exception {
		System.out.println("controller saveSilenceAppeal= ");
		service.save(dto);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping(value = "/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getSilenceAppealHTML(@PathVariable("id") String id) {
		System.out.println("controller html id = " + id);
		String result = service.getHTML(id);
		System.out.println("constroller result = " + result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
