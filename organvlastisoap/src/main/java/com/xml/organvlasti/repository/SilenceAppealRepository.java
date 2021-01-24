package com.xml.organvlasti.repository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.spring.soap.ws.hello.AuthenticationUtilities;
import com.xml.organvlasti.database.DbManager;
import com.xml.organvlasti.parser.DOMParser;

public class SilenceAppealRepository {

	private DbManager dbManager;
	
	private String collectionId = "/db/OrganVlasti/silenceAppeals";
	
	public SilenceAppealRepository() {
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
	
	public Document findSilenceAppealByBroj(String id) {
		Document document = null;
		System.out.println("in repository finding id = " + id);
		if (!id.endsWith(".xml")) {
			id = id + ".xml";
		}
		try {
			XMLResource xmlResource = dbManager.getDocument(collectionId, id);
			document = (Document) xmlResource.getContentAsDOM();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
}
