package com.xml.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	
	@GetMapping("/search")
	public ResponseEntity<String> searchFromRDF() throws IOException{
	//public ResponseEntity<String> searchFromRDF(@PathVariable("broj") String broj,
    //										@PathVariable("osobaIme") String osobaIme,
	//							    		@PathVariable("osobaPrezime") String osobaPrezime) throws IOException {
        String broj = "000-00-0000/0000-00";
        String status = "osnovana";
        String osobaIme = "A";
        String osobaPrezime = "A";
        String institucijaNaziv = "Učiteljskog fakulteta";
        String datum = "16.04.2020.";
        String trazena_informacija = "Ugovor o radu";
        String poverenikIme = "IME";
        String poverenikPrezime = "PREZIME";
        Map<String, String> params = new HashMap<>();
        params.put("broj", broj);
        params.put("status", status);
        params.put("osobaIme", osobaIme);
        params.put("osobaPrezime", osobaPrezime);
        params.put("institucijaNaziv", institucijaNaziv);
        params.put("datum", datum);
        params.put("trazena_informacija", trazena_informacija);
        params.put("poverenikIme", poverenikIme);
        params.put("poverenikPrezime", poverenikPrezime);

        System.out.println("seatchFromRDF = " + broj + " " + osobaIme + " " + osobaPrezime);
        ArrayList<String> result = service.searchByMetadata(params);
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