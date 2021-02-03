package com.xml.organvlasti.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.xml.organvlasti.dto.DecisionAppealDTO;
import com.xml.organvlasti.model.decisionAppealResponse.DAppealListResponse;
import com.xml.organvlasti.model.keywordSearch.KeywordSearch;
import com.xml.organvlasti.service.DecisionAppealService;

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
	
	@GetMapping(value = "/all", produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<DAppealListResponse> getAll() {
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
	
	@PostMapping(value = "/keywords", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<DAppealListResponse> searchKeywords(@RequestBody KeywordSearch s){
		System.out.println("controller searchKeywords xml = " + s);
		DAppealListResponse result;
		try {
			result = service.searchByKeywords(s);
	        System.out.println("OUTPUT: " + result);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (XMLDBException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (JAXBException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (SAXException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getDecisionAppealHTML(@PathVariable("id") String id) {
		System.out.println("controller html id = " + id);
		String result = service.getHTML(id);
		System.out.println("constroller result = " + result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/pdf/{broj}")
    public ResponseEntity<?> getPDF(@PathVariable("broj") String broj, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String html = service.getHTML(broj);
        /* Setup Source and target I/O streams */
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");
        /* Call convert method */
        HtmlConverter.convertToPdf(html, target, converterProperties);  
        /* extract output as bytes */
        byte[] bytes = target.toByteArray();
        /* Send the response as downloadable PDF */
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + broj + ".pdf") 
                .contentType(MediaType.APPLICATION_PDF) 
                .body(bytes);       
    }
}
