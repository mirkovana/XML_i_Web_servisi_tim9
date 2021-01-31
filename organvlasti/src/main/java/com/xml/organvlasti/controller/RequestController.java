package com.xml.organvlasti.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

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

import com.xml.organvlasti.model.email.EmailModel;
import com.xml.organvlasti.model.request.Zahtev;
import com.xml.organvlasti.model.zahtevResponse.RequestListResponse;
import com.xml.organvlasti.service.RequestService;

@RestController()
@RequestMapping(value = "api/request")
@CrossOrigin()
public class RequestController {

	@Autowired
	private RequestService service;
	
	/*@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<RequestDTO> saveRequest(@RequestBody RequestDTO dto) throws Exception {
		System.out.println("controller saveRequest = ");
		service.save(dto);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}*/
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveRequestXML(@RequestBody String xmlString) throws Exception {
		System.out.println("controller saveRequest as xml= ");
		service.save(xmlString);
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
	
	/*@GetMapping(value = "/pdf/{broj}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> getPdf(@PathVariable("broj") String broj) throws Exception {
		Resource resource = service.getPdf(broj);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}*/
	
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
	
	/*private void sendEmail(byte[] pdf) {
		System.out.println("sendemailcontroller");
		String fooResourceUrl = "http://localhost:5000/email";
		RestTemplate restTemplate = new RestTemplate();
		EmailModel email = new EmailModel();
		email.setFrom("alen@maildrop.cc");
		email.setPdf(Base64.getEncoder().encodeToString(pdf));
		email.setSubject("Subject");
		email.setText("text");
		email.setTo("alenmujo10@gmail.com");
		System.out.println("emailmodel = " + email);
		HttpEntity<EmailModel> request = new HttpEntity<>(email);
		restTemplate.postForObject(fooResourceUrl, request, EmailModel.class);
	}*/
	
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
	
	/*@GetMapping(value = "/download/file/{id}")
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
	}*/
	/*@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<String> saveRequestJax(@RequestBody Zahtev dto) throws Exception {
		System.out.println("controller saveRequest xml = ");
		System.out.println("Zahtev dto = " + dto);
		Zahtev zahtev = service.saveJax(dto);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}*/
}

/*@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
@CrossOrigin
public ResponseEntity<Zahtev> saveRequestJax(@RequestBody Zahtev dto) throws Exception {
	System.out.println("controller saveRequest xml = ");
	System.out.println("Zahtev dto = " + dto);
	Zahtev zahtev = service.saveJax(dto);
	return new ResponseEntity<>(zahtev, HttpStatus.OK);
}*/

/*@GetMapping(value = "/{username}/all")
@CrossOrigin
public ResponseEntity<ArrayList<RequestItem>> getAllForUser(@PathVariable("username") String username) throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException {
	System.out.println("controller = ");
	return new ResponseEntity<>(service.getAllForUser(username), HttpStatus.OK);
}*/