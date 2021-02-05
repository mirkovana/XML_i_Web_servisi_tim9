package com.xml.organvlasti.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.xml.organvlasti.model.email.EmailModel;
import com.xml.organvlasti.model.keywordSearch.KeywordSearch;
import com.xml.organvlasti.model.request.Zahtev;
import com.xml.organvlasti.model.zahtevResponse.RequestListResponse;
import com.xml.organvlasti.model.zahtevResponse.RequestListResponse.RequestItem;
import com.xml.organvlasti.parser.DOMParser;
import com.xml.organvlasti.parser.JAXParser;
import com.xml.organvlasti.parser.XSLTransformer;
import com.xml.organvlasti.rdf.FusekiReader;
import com.xml.organvlasti.rdf.FusekiWriter;
import com.xml.organvlasti.rdf.MetadataExtractor;
import com.xml.organvlasti.repository.RequestRepository;

@Service()
public class RequestService {

	private final String requestXSL = "src/main/resources/xsl/zahtev.xsl";
	private static String schemaPath = "src/main/resources/documents/zahtev.xsd";
	private static String contextPath = "com.xml.organvlasti.model.request";

	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private RequestRepository repository;
	@Autowired
	private UserService userService;
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	/*public Zahtev saveJax(Zahtev dto) throws JAXBException, SAXException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, IOException {
		String brojZalbe = UUID.randomUUID().toString().split("-")[4] + "-2021";
		dto.setBroj(brojZalbe);
		dto.setAbout("http://www.projekat.org/zahtev/" + brojZalbe);
		Document prev = null;
		try {
			System.out.println("findRequestById call");
			prev = repository.findRequestById(brojZalbe);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("request already exist");
			return null;
		}

		Marshaller marshaller = JAXParser.createMarshaller(contextPath, schemaPath);
		StringWriter sw = new StringWriter();
		marshaller.marshal(dto, sw);
		String xmlString = sw.toString();
		//xmlString = xmlString.replaceAll("ns1", "za");
		System.out.println("sw.tostring = " + xmlString);
		repository.save(xmlString, brojZalbe);
		metadataExtractor.extractMetadata(xmlString, MetadataExtractor.REQUEST_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.REQUEST_RDF_FILEPATH, FusekiWriter.REQUEST_METADATA_GRAPH_URI);
		return dto;
	}*/
	
	public RequestListResponse getAll() throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException {
		return repository.getAll();
	}
	
	public RequestListResponse getAllForUser(String username) throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException {
		return repository.getAllForUser(username);
	}
	
	public void save(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		domParser.setSchema(schemaPath);
		Document document = domParser.getDocument(dto);
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("za:zahtev");		
		Element zahtevElement = (Element) nodeList.item(0);
		
		//String id = sp.getAttribute("id");
		String id = UUID.randomUUID().toString().split("-")[4] + "-2021";
		zahtevElement.setAttribute("broj", id);
		zahtevElement.setAttribute("about", "http://www.projekat.org/zahtev/" + id);
		zahtevElement.setAttribute("time", Long.toString(System.currentTimeMillis()));
	
		Document prev = null;
		try {
			System.out.println("findRequestById call");
			prev = repository.findRequestById(id);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("request already exist");
			return;
		}
		System.out.println("found request = " + prev);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		String xmlString = sw.toString();
		//sets <broj> tag
		xmlString = xmlString.replace("insert_broj_here", id);

		repository.save(xmlString, id);
		
		metadataExtractor.extractMetadata(xmlString, MetadataExtractor.REQUEST_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.REQUEST_RDF_FILEPATH, FusekiWriter.REQUEST_METADATA_GRAPH_URI);
	}
	
	public void deleteRequest(String broj) {
		try {
			repository.deleteRequest(broj);
			FusekiWriter.saveRDF(FusekiWriter.REQUEST_RDF_FILEPATH, FusekiWriter.REQUEST_METADATA_GRAPH_URI);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (XMLDBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getHTML(String broj) {
		Document xml = repository.findRequestById(broj);
		return xslTransformer.getHTMLfromXML(requestXSL, xml);
	}

	public String getFileDownload(String broj) throws TransformerException {
		Document xml = repository.findRequestById(broj);
		return getStringFromDocument(xml);
	}
	
	public void acceptRequest(String broj) {
		Document document = repository.findRequestById(broj);
		System.out.println("getTextcontnet" + document.toString());
		NodeList nodeList = document.getElementsByTagName("za:zahtev");
		//sp.getAttribute("status")
		Element sp = (Element) nodeList.item(0);
		sp.setAttribute("status", "accepted");
		try {
			String xmlString = getStringFromDocument(document);
			xmlString = xmlString.replace("sent", "accepted");
			xmlString = xmlString.replace("denied", "accepted");

			repository.deleteRequest(broj);
			repository.save(xmlString, broj);
			
			metadataExtractor.extractMetadata(xmlString, MetadataExtractor.REQUEST_RDF_FILE);
			FusekiWriter.saveRDF(FusekiWriter.REQUEST_RDF_FILEPATH, FusekiWriter.REQUEST_METADATA_GRAPH_URI);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (XMLDBException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void denyRequest(String broj) {
		//repository.denyRequest(broj);
		Document document = repository.findRequestById(broj);
		System.out.println("getTextcontnet" + document.toString());
		NodeList nodeList = document.getElementsByTagName("za:zahtev");
		//sp.getAttribute("status")
		Element sp = (Element) nodeList.item(0);
		sp.setAttribute("status", "denied");
		
		try {
			String xmlString = getStringFromDocument(document);
			xmlString = xmlString.replace("sent", "denied");
			
		    //repository.denyRequest(xmlFragment, broj);
		    
			repository.deleteRequest(broj);
			repository.save(xmlString, broj);
			
			metadataExtractor.extractMetadata(xmlString, MetadataExtractor.REQUEST_RDF_FILE);
			FusekiWriter.saveRDF(FusekiWriter.REQUEST_RDF_FILEPATH, FusekiWriter.REQUEST_METADATA_GRAPH_URI);
			
			sendEmail(broj, sp.getAttribute("username"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (XMLDBException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getStringFromDocument(Document document) throws TransformerException {
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));	
		return sw.toString();
	}
	
	public byte[] getPdfBytes(String broj) throws IOException {
		String html = getHTML(broj);
        /* Setup Source and target I/O streams */
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");
        converterProperties.setCharset("UTF-8");
        /* Call convert method */
        HtmlConverter.convertToPdf(html, target, converterProperties);  
        /* extract output as bytes */
        byte[] bytes = target.toByteArray();

        return bytes;
	}
	
	public void sendEmail(String broj, String to) {
		try {
			String toEmail = userService.getUserEmailByUsername(to);
			String fromEmail = "sluzbenik@gmail.com";
			byte[] pdfBytes = getPdfBytes(broj);
			
			System.out.println("sendemailrequestservice");
			String fooResourceUrl = "http://localhost:5000/email";
			RestTemplate restTemplate = new RestTemplate();
			EmailModel email = new EmailModel();
			email.setFrom(fromEmail);
			email.setPdf(Base64.getEncoder().encodeToString(pdfBytes));
			email.setSubject("Obavestenje o odbijanju zahteva za pristup informacijama");
			email.setText("Vas zahtev br." + broj + " je odbijen. Mozete uloziti zalbu povereniku.");
			email.setTo(toEmail);
			System.out.println("emailmodel = " + email);
			HttpEntity<EmailModel> request = new HttpEntity<>(email);
			restTemplate.postForObject(fooResourceUrl, request, EmailModel.class);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/*private void printNode(Node node) {
		if (node == null)
			return;
		if (node instanceof Element) {
			
			Element element = (Element) node;
			
			System.out.print("START_ELEMENT: " + element.getTagName());
			System.out.println();
			// Prikaz svakog od child nodova, rekurzivnim pozivom
			NodeList children = element.getChildNodes();
			
			if (children != null) {
				for (int i = 0; i < children.getLength(); i++) {
					Node aChild = children.item(i);
					printNode(aChild);
				}
			}
		} 			
	}*/

	public RequestListResponse searchByMetadata(Map<String, String> params) throws IOException {
        System.out.println("service executeQuerry!");
        ArrayList<Map<String, String>> result = FusekiReader.executeQuery(params, FusekiReader.REQUEST_QUERY_FILEPATH);
        System.out.println("return result querry!");
        ArrayList<String> brojList = new ArrayList<>();
        for(Map<String, String> map : result) {
        	String zahtev = map.get("zahtev");
        	String[] split = zahtev.split("\\/");
        	brojList.add(split[split.length-1]);
        	/*System.out.println("map = ");
        	for(String key : map.keySet()) {
            	System.out.println("ket = " + key + " value = " + map.get(key));        		
        	}
        	System.out.println();*/
        }
        RequestListResponse response = new RequestListResponse();
        List<RequestItem> itemsList = new ArrayList<>();
        
        for(String broj : brojList) {
        	Zahtev zahtev = repository.findRequestByIdMarshall(broj);
        	if(zahtev == null) {
        		continue;
        	}
        	RequestItem item = new RequestItem();
    		item.setBroj(zahtev.getBroj());
    		item.setDatum(zahtev.getDatum());
    		item.setInstitucija(zahtev.getInstitucijaNaziv());
    		item.setUsername(zahtev.getUsername());
    		item.setTime(zahtev.getTime());
    		item.setStatus(zahtev.getStatus());
    		if(item.getStatus().contentEquals("sent") && System.currentTimeMillis() - Long.parseLong(item.getTime()) > 120000) { 
    			item.setStatus("expired");
    		}
    		itemsList.add(item); 
        }
        response.setRequestItem(itemsList);
        System.out.println("search response = " + response);
        return response;
    }

	public RequestListResponse searchByKeywords(KeywordSearch s) throws NumberFormatException, XMLDBException, JAXBException, SAXException {
		return repository.searchByKeywords(s);
	}

}
