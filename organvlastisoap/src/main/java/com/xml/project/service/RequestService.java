package com.xml.project.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.xml.organvlasti.parser.DOMParser;
import com.xml.organvlasti.repository.RequestRepository;

public class RequestService {

	private final String requestXSL = "src/main/resources/xsl/zahtev.xsl";
	private static String schemaPath = "src/main/resources/documents/zahtev.xsd";
	private static String contextPath = "com.xml.project.model.request";
	
	private RequestRepository repository;
	
	public RequestService() {
		repository = new RequestRepository();
	}
	
	public String getOne(String id) {
		System.out.println("service getOne id = " + id);
		Document document = repository.findRequestById(id);
		try {
			return repository.getStringFromDocument(document);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return "error";
	}
}
