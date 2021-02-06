package com.xml.organvlasti.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xml.organvlasti.database.DbManager;

@Repository
public class ExplanationRepository {

	@Autowired
	private DbManager dbManager;
	
	private String collectionId = "/db/OrganVlasti/explanation";
	
	public String save(String xmlEntity, String broj)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("save in repository entity = " + xmlEntity + " broj = " + broj);
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		dbManager.storeXMLFromText(collectionId, broj, xmlEntity);
		return "SAVED";
	}
	
	public Document findExplanationByBroj(String broj) {
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
