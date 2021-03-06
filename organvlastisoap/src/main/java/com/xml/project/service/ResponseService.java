package com.xml.project.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xml.organvlasti.parser.DOMParser;
import com.xml.organvlasti.rdf.FusekiWriter;
import com.xml.organvlasti.rdf.MetadataExtractor;
import com.xml.organvlasti.repository.DecisionAppealRepository;
import com.xml.organvlasti.repository.RequestRepository;
import com.xml.organvlasti.repository.ResponseRepository;

public class ResponseService {

	private DOMParser domParser;
	private ResponseRepository repository;
	private DecisionAppealService dAppealService;
	private SilenceAppealService sAppealService;
	private RequestService requestService;
	private MetadataExtractor metadataExtractor;

	public ResponseService() {
		domParser = new DOMParser();
		repository = new ResponseRepository();
		dAppealService = new DecisionAppealService();
		sAppealService = new SilenceAppealService();
		requestService = new RequestService();
		try {
			metadataExtractor = new MetadataExtractor();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveDecisionResponse(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto);
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("res:zalba");
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		//sp.setAttribute("about", "http://www.projekat.org/resenje/" + broj);
		System.out.println("node broj = " + broj);
		//broj = broj.replace("/", "_");
		Document prev = null;
		try {
			System.out.println("findResponseByBroj call");
			prev = repository.findResponseByBroj(broj);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("response already exist");
			return;
		}
		System.out.println("found response = " + prev);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		System.out.println("broj after = " + broj);
		repository.save(sw.toString(), broj + ".xml");
		
		metadataExtractor.extractMetadata(sw.toString(), MetadataExtractor.RESPONSE_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.RESPONSE_RDF_FILEPATH, FusekiWriter.RESPONSE_METADATA_GRAPH_URI);
	
		dAppealService.updateStateResolved(broj);
		requestService.updateStateResolved(broj, sp.getAttribute("status"));
	}

	public void saveSilenceResponse(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto);
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("res:zalba");
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		//sp.setAttribute("about", "http://www.projekat.org/resenje/" + broj);
		System.out.println("node broj = " + broj);
		//broj = broj.replace("/", "_");
		Document prev = null;
		try {
			System.out.println("findResponseByBroj call");
			prev = repository.findResponseByBroj(broj);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("response already exist");
			return;
		}
		System.out.println("found response = " + prev);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		System.out.println("broj after = " + broj);
		repository.save(sw.toString(), broj + ".xml");
		
		metadataExtractor.extractMetadata(sw.toString(), MetadataExtractor.RESPONSE_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.RESPONSE_RDF_FILEPATH, FusekiWriter.RESPONSE_METADATA_GRAPH_URI);
	
		sAppealService.updateStateResolved(broj);
		requestService.updateStateResolved(broj, sp.getAttribute("status"));
	}
	
}
