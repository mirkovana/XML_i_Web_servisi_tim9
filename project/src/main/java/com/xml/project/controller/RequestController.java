package com.xml.project.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.xml.project.model.request.Zahtev;
import com.xml.project.model.zahtevResponse.RequestListResponse;
import com.xml.project.service.RequestService;

@RestController()
@RequestMapping(value = "api/request")
@CrossOrigin()
public class RequestController {

	@Autowired
	private RequestService service;
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveRequestXML(@RequestBody String xmlString) throws Exception {
		System.out.println("controller saveRequest as xml= ");
		service.save(xmlString);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping(value = "/deniedRequest", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveDeniedRequestXML(@RequestBody String xmlString) throws Exception {
		System.out.println("controller saveDeniedRequest as xml= ");
		service.saveDeniedRequest(xmlString);
		return new ResponseEntity(HttpStatus.OK);
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

        String html = service.getHTML(broj);
        /* Setup Source and target I/O streams */
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8070");
        converterProperties.setCharset("UTF-8");
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
	
	/*@GetMapping(value = "/download/{id}", produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> getRequestFile(@PathVariable("id") String id) {
		System.out.println("controller download id = " + id);
		String result;
		try {
			result = service.getFileDownload(id);
			System.out.println("constroller download result = " + result);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (TransformerException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("BAD_REQUEST", HttpStatus.BAD_REQUEST);
		}
	}*/
	
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
