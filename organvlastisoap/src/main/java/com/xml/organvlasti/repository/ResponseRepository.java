package com.xml.organvlasti.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import com.spring.soap.ws.hello.AuthenticationUtilities;
import com.xml.organvlasti.database.DbManager;

public class ResponseRepository {

	private DbManager dbManager;
	
	private String collectionId = "/db/OrganVlasti/responses";
	
	public ResponseRepository() {
		try {
			dbManager = new DbManager(AuthenticationUtilities.loadProperties());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String save(String xmlEntity, String broj)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("save in repository entity = " + xmlEntity + " broj = " + broj);
		dbManager.storeXMLFromText(collectionId, broj, xmlEntity);
		return "SAVED";
	}
	
	public Document findResponseByBroj(String broj) {
		Document document = null;
		System.out.println("in repository finding broj = " + broj);
		if (!broj.endsWith(".xml")) {
			broj= broj+ ".xml";
		}
		try {
			XMLResource xmlResource = dbManager.getDocument(collectionId, broj);
			document = (Document) xmlResource.getContentAsDOM();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	
}
