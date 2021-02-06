package com.xml.project.repository;

import java.io.IOException;

import org.xmldb.api.base.XMLDBException;

import com.spring.soap.ws.hello.AuthenticationUtilities;
import com.xml.project.database.DbManager;

public class ReportRepository {

	private DbManager dbManager;
	
	private String collectionId = "/db/XmlProject/reports";
	
	public ReportRepository() {
		try {
			dbManager = new DbManager(AuthenticationUtilities.loadProperties());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String save(String xmlEntity, String id)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("save in repository entity = " + xmlEntity + " id= " + id);
		if (!id.endsWith(".xml")) {
			id = id + ".xml";
		}
		dbManager.storeXMLFromText(collectionId, id, xmlEntity);
		return "SAVED";
	}

}
