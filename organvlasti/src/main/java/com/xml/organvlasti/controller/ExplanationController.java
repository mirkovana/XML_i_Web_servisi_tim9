package com.xml.organvlasti.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.xml.organvlasti.service.DecisionAppealService;
import com.xml.organvlasti.service.ExplanationService;
import com.xml.organvlasti.soap.Poverenik;

@RestController()
@RequestMapping(value = "api/explanation")
@CrossOrigin()
public class ExplanationController {

	@Autowired
	private ExplanationService service;
		
	@PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<HttpStatus> saveExplanationXML(@RequestBody String xmlString) {
		System.out.println("controller saveExplanation as xml = ");
		try {
			service.save(xmlString);
			sendExplanation(xmlString);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ParserConfigurationException | SAXException | IOException | TransformerException | XMLDBException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getExplanationHTML(@PathVariable("id") String id) {
		System.out.println("controller html id = " + id);
		String result = service.getHTML(id);
		System.out.println("constroller result = " + result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	private void sendExplanation(String xml) {
		try {
			URL wsdlLocation = new URL("http://localhost:8071/ws/poverenik?wsdl");
			QName serviceName = new QName("http://soap.spring.com/ws/poverenik", "PoverenikService");
			QName portName = new QName("http://soap.spring.com/ws/poverenik", "PoverenikPort");

			Service service2 = Service.create(wsdlLocation, serviceName);
			
			Poverenik poverenik = service2.getPort(portName, Poverenik.class); 
			
			//poziv web servisa
			String response = poverenik.saveExplanation(xml);
			System.out.println("Response from WS: " + response);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
