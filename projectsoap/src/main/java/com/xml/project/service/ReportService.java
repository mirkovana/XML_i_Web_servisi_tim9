package com.xml.project.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.xml.project.model.report.Izvestaj;
import com.xml.project.parser.DOMParser;
import com.xml.project.parser.JAXParser;
import com.xml.project.repository.ReportRepository;

public class ReportService {

	//private static String schemaPath = "src/main/resources/documents/izvestaj.xsd";
	//private static String contextPath = "com.xml.project.model.report";
	private ReportRepository repository;
	private DOMParser domParser;
	
	public ReportService() {
		repository = new ReportRepository();
		domParser = new DOMParser();
	}
	
	public void saveReport(String izvestaj) throws JAXBException, SAXException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, ParserConfigurationException, IOException {
		System.out.println("save service = " + izvestaj);
		Document document = domParser.getDocument(izvestaj);
		NodeList nodeList = document.getElementsByTagName("izvestaj");		
		Element reportElement = (Element) nodeList.item(0);
		String id = reportElement.getAttribute("id");
		repository.save(izvestaj, id);
	}

}
