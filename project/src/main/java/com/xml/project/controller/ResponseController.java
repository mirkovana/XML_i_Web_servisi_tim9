package com.xml.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.xml.project.dto.ResponseDTO;
import com.xml.project.parser.DOMParser;
import com.xml.project.service.ResponseService;

@RestController()
@RequestMapping(value = "api/response")
@CrossOrigin()
public class ResponseController {

	@Autowired
	private ResponseService service;

	@Autowired
	private DOMParser domParser;

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<ResponseDTO> saveResponse(@RequestBody ResponseDTO dto) throws Exception {
		System.out.println("controller saveresponse = ");
		Document document = domParser.getDocument(dto.getText());
		NodeList nodeList = document.getElementsByTagName("res:zalba");

		if (nodeList.getLength() != 0) {
			service.save(dto);
		}

		nodeList = document.getElementsByTagName("ob:obavestenje");

		if (nodeList.getLength() != 0) {
			service.saveObavestenje(dto);
		}

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getResponseHTML(@PathVariable("id") String id) {
		System.out.println("controller broj = " + id);
		String result = service.getHTML(id);
		System.out.println("constroller result = " + result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	/*
	 * @GetMapping(value = "/pdf/{id}", produces =
	 * MediaType.APPLICATION_OCTET_STREAM_VALUE) public ResponseEntity<Object>
	 * getPdf(@PathVariable("id") String id) throws Exception { Resource resource =
	 * service.getPdf(id); return
	 * ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
	 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
	 * resource.getFilename() + "\"") .body(resource); }
	 */

}
