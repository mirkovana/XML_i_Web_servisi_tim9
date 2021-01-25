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

import com.xml.project.parser.XSLTransformer;
import com.xml.project.repository.ExplanationRepository;

@Service()
public class ExplanationService {

	private final String explanationXSL = "src/main/resources/xsl/explanation.xsl";

	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private ExplanationRepository repository;
	
	
	public String getHTML(String broj) {
		Document xml = repository.findExplanationByBroj(broj);
		return xslTransformer.getHTMLfromXML(explanationXSL, xml);
	}
}
