package com.xml.organvlasti.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.stereotype.Service;
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

import com.xml.organvlasti.dto.RequestDTO;
import com.xml.organvlasti.dto.RequestItem;
import com.xml.organvlasti.model.request.Zahtev;
import com.xml.organvlasti.parser.DOMParser;
import com.xml.organvlasti.parser.JAXParser;
import com.xml.organvlasti.parser.XSLTransformer;
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
	private MetadataExtractor metadataExtractor;
	
	/*public Zahtev saveJax(Zahtev dto) throws JAXBException, SAXException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, IOException {
		String brojZalbe = UUID.randomUUID().toString().split("-")[4] + "-2021";
		dto.setId(brojZalbe);
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
		System.out.println("sw.tostring = " + xmlString);
		repository.save(xmlString, brojZalbe);
		metadataExtractor.extractMetadata(xmlString, MetadataExtractor.REQUEST_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.REQUEST_RDF_FILEPATH, FusekiWriter.REQUEST_METADATA_GRAPH_URI);
		return dto;
	}*/
	
	public ArrayList<RequestItem> getAll() throws XMLDBException, ParserConfigurationException, SAXException, IOException {
		return repository.getAll(false, "");
	}
	
	public ArrayList<RequestItem> getAllForUser(String username) throws XMLDBException, ParserConfigurationException, SAXException, IOException {
		return repository.getAll(true, username);
	}
	
	public void save(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getHTML(String broj) {
		Document xml = repository.findRequestById(broj);
		return xslTransformer.getHTMLfromXML(requestXSL, xml);
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
}
