package com.xml.project.controller;

import java.io.IOException;
import java.util.ArrayList;

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

import com.xml.project.dto.ResponseDTO;
import com.xml.project.service.ResponseService;

@RestController()
@RequestMapping(value = "api/response")
@CrossOrigin()
public class ResponseController {

	@Autowired
	private ResponseService service;
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<ResponseDTO> saveResponse(@RequestBody ResponseDTO dto) throws Exception {
		System.out.println("controller saveresponse = ");
		service.save(dto);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping("/search/{broj}/{osobaIme}/{osobaPrezime}")
    public ResponseEntity<String> searchFromRDF(@PathVariable("broj") String broj,
    										@PathVariable("osobaIme") String osobaIme,
								    		@PathVariable("osobaPrezime") String osobaPrezime) throws IOException {
        broj = broj.replace("_", "/");
        System.out.println("seatchFromRDF = " + broj + " " + osobaIme + " " + osobaPrezime);
        ArrayList<String> result = service.searchByMetadata(broj, osobaIme, osobaPrezime);
        String output = "";
        for (String r : result) {
            output += "\n" + r;
        }
        System.out.println("OUTPUT: " + output);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
	
	@GetMapping(value = "/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getResponseHTML(@PathVariable("id") String id) {
		System.out.println("controller broj = " + id);
		String result = service.getHTML(id);
		System.out.println("constroller result = " + result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	/*@GetMapping(value = "/pdf/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> getPdf(@PathVariable("id") String id) throws Exception {
		Resource resource = service.getPdf(id);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}*/

}
