package com.xml.project.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.Service;

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
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.xml.project.model.dAppealSearch.DAppealSearch;
import com.xml.project.model.decisionAppealResponse.DAppealListResponse;
import com.xml.project.model.keywordSearch.KeywordSearch;
import com.xml.project.service.DecisionAppealService;
import com.xml.project.soap.Sluzbenik;

@RestController()
@RequestMapping(value = "api/decision-appeals")
@CrossOrigin()
public class DecisionAppealController {

	@Autowired
	private DecisionAppealService service;
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveDecisionAppeal(@RequestBody String xmlString) {
		System.out.println("controller saveDecisionAppeal = ");
		try {
			service.save(xmlString);
			return new ResponseEntity(HttpStatus.OK);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ParserConfigurationException
				| SAXException | IOException | TransformerException | XMLDBException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{username}/all",  produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<DAppealListResponse> getAllForUsername(@PathVariable("username") String username){
		System.out.println("controller getallforusername = " + username);
		try {
			return new ResponseEntity<>(service.getAllForUsername(username), HttpStatus.OK);
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
	
	@GetMapping(value = "/all", produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<DAppealListResponse> getAll() throws MalformedURLException{
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
	
	@GetMapping(value = "/requestExplanation/{broj}", produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<String> requestExplanation(@PathVariable("broj") String broj){
		System.out.println("controller requestExplanation = " + broj);
		try {
			service.updateState(broj);
			String appealXml = service.getAppealXml(broj);
			sendAppeal(appealXml);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		} catch (InstantiationException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		} catch (TransformerException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		} catch (XMLDBException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{broj}")
	public ResponseEntity<Void> deleteAppeal(@PathVariable("broj") String broj) {
		try {
			service.deleteAppeal(broj);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (InstantiationException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (XMLDBException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
	
	@PostMapping(value = "/search", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<DAppealListResponse> searchMetadata(@RequestBody DAppealSearch s) throws Exception {
		System.out.println("controller searchMetadata xml = " + s);
		String broj = isEmpty(s.getBroj());
        String datum = isEmpty(s.getDatum());
        String status = isEmpty(s.getStatus());
        String mesto = isEmpty(s.getMesto());
        String ime = isEmpty(s.getIme());
        String prezime = isEmpty(s.getPrezime());
        String organVlasti = isEmpty(s.getNazivOrgana());
        Map<String, String> params = new HashMap<>();
        params.put("broj", broj);
        params.put("datum", datum);
        params.put("status", status);
        params.put("mesto", mesto);
        params.put("ime", ime);
        params.put("prezime", prezime);
        params.put("organVlasti", organVlasti);
        
        DAppealListResponse result = service.searchByMetadata(params);
        
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
	
	private void sendAppeal(String xml) {
		System.out.println("sendAppeal");
		try {
			URL wsdlLocation = new URL("http://localhost:8050/ws/sluzbenik?wsdl");
			QName serviceName = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikService");
			QName portName = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikPort");

			Service service2 = Service.create(wsdlLocation, serviceName);
			
			Sluzbenik sluzbenik = service2.getPort(portName, Sluzbenik.class); 
			
			//poziv web servisa
			String response = sluzbenik.saveDecisionAppeal(xml);
			System.out.println("Response from WS: " + response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
