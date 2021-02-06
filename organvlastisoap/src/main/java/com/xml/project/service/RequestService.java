package com.xml.project.service;

import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.XMLDBException;
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

	public void updateStateResolved(String broj, String status) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		Document document = repository.findRequestById(broj);
		NodeList nodeList = document.getElementsByTagName("za:zahtev");
		Element sp = (Element) nodeList.item(0);
		sp.setAttribute("status", status);
		String xmlString;
		
		xmlString = repository.getStringFromDocument(document);
		xmlString = xmlString.replace("expired", status);
		xmlString = xmlString.replace("denied", status);

		System.out.println("updatedstate = " + xmlString);
		repository.deleteRequest(broj);
		repository.save(xmlString, broj);		
	}
}
