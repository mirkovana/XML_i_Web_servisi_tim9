package com.xml.project.parser;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import static org.apache.xerces.jaxp.JAXPConstants.*;

@Component
public class DOMParser {

	private DocumentBuilderFactory factory;

	private Document document;

	public DOMParser() {
		factory = DocumentBuilderFactory.newInstance();

		factory.setValidating(false);
		factory.setNamespaceAware(true);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
	}

	public Document buildDocument(String filePath) throws SAXException, IOException, ParserConfigurationException {

		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse(new File(filePath));
		if (document != null)
			System.out.println("[INFO] File parsed with no errors.");
		else
			System.out.println("[WARN] Document is null.");
		return document;

	}
	public Document getDocument(String xmlText) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(xmlText)));
		return document;
	}

	public Document buildDocumentFromText(String fileText) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(fileText)));
		return document;
	}
}
