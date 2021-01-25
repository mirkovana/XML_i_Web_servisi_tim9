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

import com.xml.project.parser.DOMParser;
//import com.xml.project.parser.XSLTransformer;
import com.xml.project.rdf.FusekiWriter;
import com.xml.project.rdf.MetadataExtractor;
import com.xml.project.repository.DecisionAppealRepository;
import com.xml.project.repository.ExplanationRepository;

public class ExplanationService {

	//private final String explanationXSL = "src/main/resources/xsl/explanation.xsl";

	private DOMParser domParser;
	/*@Autowired
	private XSLTransformer xslTransformer;*/

	private ExplanationRepository repository;
	private DecisionAppealService dAppealService;
	private SilenceAppealService sAppealService;
	
	public ExplanationService() {
		domParser = new DOMParser();
		repository = new ExplanationRepository();
		dAppealService = new DecisionAppealService();
		sAppealService = new SilenceAppealService();
	}
	
	public void save(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto);
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("ex:explanation");		
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		System.out.println("node broj = " + broj);
		String tipZalbe = sp.getAttribute("tipZalbe");
		System.out.println("node tipZalbe = " + tipZalbe);
		
		Document prev = null;
		try {
			System.out.println("findResponseByBroj call");
			prev = repository.findExplanationByBroj(broj);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("explanation already exist");
			return;
		}
		System.out.println("found explanation = " + prev);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		repository.save(sw.toString(), broj + ".xml");
		//update appeal state to answered
		if(tipZalbe.contentEquals("decision")) {
			dAppealService.updateState(broj);			
		}else if (tipZalbe.contentEquals("silence")){
			sAppealService.updateState(broj);			
		}
		//metadataExtractor.extractMetadata(sw.toString(), MetadataExtractor.RESPONSE_RDF_FILE);
		//FusekiWriter.saveRDF(FusekiWriter.RESPONSE_RDF_FILEPATH, FusekiWriter.RESPONSE_METADATA_GRAPH_URI);
	}
	
	/*public String getHTML(String broj) {
		Document xml = repository.findExplanationByBroj(broj);
		return xslTransformer.getHTMLfromXML(explanationXSL, xml);
	}*/
}
