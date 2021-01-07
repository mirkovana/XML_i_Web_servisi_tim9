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

import com.xml.project.dto.RequestDTO;
import com.xml.project.service.RequestService;

@RestController()
@RequestMapping(value = "api/request")
@CrossOrigin()
public class RequestController {

	@Autowired
	private RequestService service;
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<RequestDTO> saveRequest(@RequestBody RequestDTO dto) throws Exception {
		System.out.println("controller saveRequest = ");
		service.save(dto);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getRequestHTML(@PathVariable("id") String id) {
		System.out.println("controller html id = " + id);
		String result = service.getHTML(id);
		System.out.println("constroller result = " + result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
