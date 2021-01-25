package com.xml.organvlasti.service;

import java.io.IOException;
import java.io.StringWriter;

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
import org.xmldb.api.base.XMLDBException;

import com.xml.organvlasti.dto.DecisionAppealDTO;
import com.xml.organvlasti.model.decisionAppealResponse.DAppealListResponse;
import com.xml.organvlasti.parser.DOMParser;
import com.xml.organvlasti.parser.XSLTransformer;
import com.xml.organvlasti.rdf.FusekiWriter;
import com.xml.organvlasti.rdf.MetadataExtractor;
import com.xml.organvlasti.repository.DecisionAppealRepository;

@Service()
public class DecisionAppealService {

	private final String responseXSL = "src/main/resources/xsl/zalbanaodluku.xsl";

	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private DecisionAppealRepository repository;
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	public void save(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto);
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("zo:zalba_na_odluku");
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		System.out.println("node broj = " + broj);
		//broj = broj.replace("/", "_");
		Document prev = null;
		try {
			System.out.println("findDecisionAppealById call");
			prev = repository.findDecisionAppealByBroj(broj);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("decision already exist");
			return;
		}
		System.out.println("found decision = " + prev);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		repository.save(sw.toString(), broj + ".xml");
		
		metadataExtractor.extractMetadata(sw.toString(), MetadataExtractor.DECISION_APPEAL_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.DECISION_APPEAL_RDF_FILEPATH, FusekiWriter.DECISION_APPEAL_METADATA_GRAPH_URI);
	}
	
	public DAppealListResponse getAll() throws XMLDBException, JAXBException, SAXException {
		return repository.getAll();
	}
	
	public void updateState(String broj) throws TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("updatestate = " + broj);
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		Document document = repository.findDecisionAppealByBroj(broj);
		NodeList nodeList = document.getElementsByTagName("zo:zalba_na_odluku");
		Element sp = (Element) nodeList.item(0);
		sp.setAttribute("status", "answered");
		String xmlString;
		
		xmlString = repository.getStringFromDocument(document);
		xmlString = xmlString.replace("sent", "answered");
		xmlString = xmlString.replace("pending", "answered");
		System.out.println("updatedstate = " + xmlString);
		repository.deleteAppeal(broj);
		System.out.println("updatedfile = " + xmlString);
		repository.save(xmlString, broj);
	}
	
	public String getHTML(String id) {
		Document xml = repository.findDecisionAppealByBroj(id);
		return xslTransformer.getHTMLfromXML(responseXSL, xml);
	}
}
