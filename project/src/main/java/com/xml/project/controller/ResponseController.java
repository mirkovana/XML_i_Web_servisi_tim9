package com.xml.project.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
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
import com.xml.project.dto.ResponseDTO;
import com.xml.project.model.responseList.ResponseList;
import com.xml.project.service.ResponseService;
import com.xml.project.soap.Sluzbenik;

@RestController()
@RequestMapping(value = "api/response")
@CrossOrigin()
public class ResponseController {

	@Autowired
	private ResponseService service;
	
	/*@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<ResponseDTO> saveResponse(@RequestBody ResponseDTO dto) throws Exception {
		System.out.println("controller saveresponse = ");
		service.save(dto);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}*/
	
	@PostMapping(value = "/decision", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveResponseDecision(@RequestBody String dto) throws Exception {
		System.out.println("controller saveresponse for decision appeal = ");
		service.save(dto, "decision");
		saveDecisionResponseSoap(dto);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping(value = "/silence", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@CrossOrigin
	public ResponseEntity saveResponseSilence(@RequestBody String dto) throws Exception {
		System.out.println("controller saveresponse for silence appeals= ");
		service.save(dto, "silence");
		saveSilenceResponseSoap(dto);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping(value = "/{username}/all",  produces = MediaType.TEXT_XML_VALUE)
	@CrossOrigin
	public ResponseEntity<ResponseList> getAllForUsername(@PathVariable("username") String username){
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
	public ResponseEntity<ResponseList> getAll() throws MalformedURLException{
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

	@RequestMapping(path = "/pdf/{broj}")
    public ResponseEntity<?> getPDF(@PathVariable("broj") String broj, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String html = service.getHTML(broj);
        /* Setup Source and target I/O streams */
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8070");
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
	
	private void saveDecisionResponseSoap(String xml) {
		System.out.println("sendAppeal");
		try {
			URL wsdlLocation = new URL("http://localhost:8050/ws/sluzbenik?wsdl");
			QName serviceName = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikService");
			QName portName = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikPort");

			Service service2 = Service.create(wsdlLocation, serviceName);
				
			Sluzbenik sluzbenik = service2.getPort(portName, Sluzbenik.class); 
				
			String response = sluzbenik.saveDecisionResponse(xml);
			System.out.println("Response from WS: " + response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void saveSilenceResponseSoap(String xml) {
		System.out.println("sendAppeal");
		try {
			URL wsdlLocation = new URL("http://localhost:8050/ws/sluzbenik?wsdl");
			QName serviceName = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikService");
			QName portName = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikPort");

			Service service2 = Service.create(wsdlLocation, serviceName);
				
			Sluzbenik sluzbenik = service2.getPort(portName, Sluzbenik.class); 
				
			String response = sluzbenik.saveSilenceResponse(xml);
			System.out.println("Response from WS: " + response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/*@GetMapping(value = "/pdf/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> getPdf(@PathVariable("id") String id) throws Exception {
		Resource resource = service.getPdf(id);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}*/

}
