package com.xml.project.service;

import java.io.IOException;
import java.io.StringWriter;

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
import org.xmldb.api.base.XMLDBException;

import com.xml.organvlasti.parser.DOMParser;
import com.xml.organvlasti.rdf.FusekiWriter;
import com.xml.organvlasti.rdf.MetadataExtractor;
import com.xml.organvlasti.repository.DecisionAppealRepository;
import com.xml.organvlasti.repository.SilenceAppealRepository;

public class SilenceAppealService {

	private DOMParser domParser;
	private SilenceAppealRepository repository;
	private MetadataExtractor metadataExtractor;

	public SilenceAppealService() {
		domParser = new DOMParser();
		repository = new SilenceAppealRepository();
		try {
			metadataExtractor = new MetadataExtractor();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto);
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("zc:zalba_cutanje");
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		//sp.setAttribute("about", "http://www.projekat.org/zalbazbogcutanja/" + broj);
		System.out.println("node broj = " + broj);
		//broj = broj.replace("/", "_");
		
		//String id = sp.getAttribute("id");
		System.out.println("node broj = " + broj);
		Document prev = null;
		try {
			System.out.println("findSilenceAppealById call");
			prev = repository.findSilenceAppealByBroj(broj);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("silence appeal already exist");
			return;
		}
		System.out.println("found silence appeal = " + prev);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		repository.save(sw.toString(), broj + ".xml");
		
		metadataExtractor.extractMetadata(sw.toString(), MetadataExtractor.SILENCE_APPEAL_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.SILENCE_APPEAL_RDF_FILEPATH, FusekiWriter.SILENCE_APPEAL_METADATA_GRAPH_URI);
	}

	public void updateStateResolved(String broj) throws TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, IOException {
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		Document document = repository.findSilenceAppealByBroj(broj);
		NodeList nodeList = document.getElementsByTagName("zc:zalba_cutanje");
		Element sp = (Element) nodeList.item(0);
		sp.setAttribute("status", "resolved");
		String xmlString;
		
		xmlString = repository.getStringFromDocument(document);
		xmlString = xmlString.replace("answered", "resolved");
		System.out.println("updatedstate = " + xmlString);
		repository.deleteAppeal(broj);
		repository.save(xmlString, broj);
	
		//metadataExtractor.extractMetadata(xmlString, MetadataExtractor.SILENCE_APPEAL_RDF_FILE);
		//FusekiWriter.saveRDF(FusekiWriter.SILENCE_APPEAL_RDF_FILEPATH, FusekiWriter.SILENCE_APPEAL_METADATA_GRAPH_URI);
	}
}
