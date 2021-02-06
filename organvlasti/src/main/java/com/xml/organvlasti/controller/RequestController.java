package com.xml.organvlasti.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.springframework.core.io.Resource;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.ConverterProperties;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import com.xml.organvlasti.model.email.EmailModel;
import com.xml.organvlasti.model.keywordSearch.KeywordSearch;
import com.xml.organvlasti.model.request.Zahtev;
import com.xml.organvlasti.model.zahtevResponse.RequestListResponse;
import com.xml.organvlasti.model.zahtevSearch.ZahtevSearch;
import com.xml.organvlasti.service.RequestService;

@RestController()
@RequestMapping(value = "api/request")
@CrossOrigin()
public class RequestController {

	@Autowired
	private RequestService service;
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveRequestXML(@RequestBody String xmlString) {
		System.out.println("controller saveRequest as xml= ");
		try {
			service.save(xmlString);
			return new ResponseEntity(HttpStatus.OK);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| ParserConfigurationException | SAXException | IOException | TransformerException
				| XMLDBException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}		
	}
	
	@GetMapping(value = "/all", produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<RequestListResponse> getAll() throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException {
		System.out.println("controller = ");
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/{username}/all",  produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<RequestListResponse> getAllForUser(@PathVariable("username") String username) throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException {
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
	
	@DeleteMapping(value = "/{broj}")
	public ResponseEntity<Void> deleteRequest(@PathVariable("broj") String broj) {
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
	
	@RequestMapping(path = "/pdf/{broj}")
    public ResponseEntity<?> getPDF(@PathVariable("broj") String broj, HttpServletRequest request, HttpServletResponse response) throws IOException {
        /* extract output as bytes */
        byte[] bytes = service.getPdfBytes(broj);
        /* Send the response as downloadable PDF */
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + broj + ".pdf") 
                .contentType(MediaType.APPLICATION_PDF) 
                .body(bytes);       
    }
	
	@GetMapping(value = "/download/file/{id}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("id") String id) throws Exception {
		System.out.println("download/file = " + id);
		String result;
		try {
			result = service.getFileDownload(id);
			System.out.println("constroller download result = " + result);
			byte[] isr = result.getBytes();
			String fileName = id+".xml";
			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.setContentLength(isr.length);
			respHeaders.setContentType(new MediaType("text", "xml"));
			respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
			return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
		} catch (TransformerException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/keywords", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<RequestListResponse> searchKeywords(@RequestBody KeywordSearch s){
		System.out.println("controller searchKeywords xml = " + s);
		RequestListResponse result;
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
	@PostMapping(value = "/search", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<RequestListResponse> searchMetadata(@RequestBody ZahtevSearch s) throws Exception {
		System.out.println("controller searchMetadata xml = " + s);
		String broj = isEmpty(s.getBroj()); 
        String datum = isEmpty(s.getDatum());
        String ime = isEmpty(s.getIme());
        String prezime = isEmpty(s.getPrezime());
        String nazivInstitucije = isEmpty(s.getNazivOrgana());
        String sedisteInstitucije = isEmpty(s.getSediste());
        String status = isEmpty(s.getStatus());
        Map<String, String> params = new HashMap<>();
        params.put("broj", broj);
        params.put("datum", datum);
        params.put("ime", ime);
        params.put("prezime", prezime);
        params.put("nazivInstitucije", nazivInstitucije);
        params.put("sedisteInstitucije", sedisteInstitucije);
        params.put("status", status);
        
        RequestListResponse result = service.searchByMetadata(params);
        
        System.out.println("OUTPUT: " + result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	private String isEmpty(String s) {
		if(s.contentEquals("")) {
			return "_";
		}else {
			return s;
		}
	}
	
	@GetMapping("/generateJSON/{broj}")
	public ResponseEntity<byte[]> generateJSON(@PathVariable("broj") String broj) throws XMLDBException {

		try {
			String jsonPath = "src/main/resources/json/zahtev_" + broj + ".json";

			service.generateRequestJSON(broj);
			File file = new File(jsonPath);
			FileInputStream fileInputStream = new FileInputStream(file);
			
			return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + broj + ".json") 
	                .contentType(MediaType.APPLICATION_JSON) 
	                .body(IOUtils.toByteArray(fileInputStream));    

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/generateRDF/{broj}")
	public ResponseEntity<byte[]> generateRDF(@PathVariable("broj") String broj) throws XMLDBException {

		try {
			String rdfPath = "src/main/resources/rdf_gen/zahtev_" + broj + ".rdf";

			service.generateRequestRDF(broj, rdfPath);
			File file = new File(rdfPath);
			FileInputStream fileInputStream = new FileInputStream(file);
			
			return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + broj + ".rdf") 
	                .contentType(MediaType.APPLICATION_JSON) 
	                .body(IOUtils.toByteArray(fileInputStream));    

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
}
