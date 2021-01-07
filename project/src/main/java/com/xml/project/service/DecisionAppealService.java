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

import com.xml.project.dto.DecisionAppealDTO;
import com.xml.project.parser.DOMParser;
import com.xml.project.parser.XSLTransformer;
import com.xml.project.repository.DecisionAppealRepository;

@Service()
public class DecisionAppealService {

	private final String responseXSL = "src/main/resources/xsl/zalbanaodluku.xsl";

	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private DecisionAppealRepository repository;
	
	public void save(DecisionAppealDTO dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto.getText());
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("zo:zalba_na_odluku");
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		System.out.println("node broj = " + broj);
		broj = broj.replace("/", "_");
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
		
	}
	
	public String getHTML(String id) {
		Document xml = repository.findDecisionAppealByBroj(id);
		return xslTransformer.getHTMLfromXML(responseXSL, xml);
	}
}
