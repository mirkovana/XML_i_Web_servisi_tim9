package com.xml.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xml.project.database.DbManager;

@Repository
public class ExplanationRepository {

	@Autowired
	private DbManager dbManager;
	
	private String collectionId = "/db/XmlProject/explanation";
	
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
