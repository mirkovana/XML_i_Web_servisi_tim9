package com.xml.organvlasti.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xmldb.api.base.XMLDBException;
import com.xml.organvlasti.database.DbManager;
import com.xml.organvlasti.parser.DOMParser;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.xml.organvlasti.dto.*;
@Repository
public class RequestRepository {

	@Autowired
	private DbManager dbManager;
	@Autowired
	private DOMParser domParser;
	
	private String collectionId = "/db/OrganVlasti/requests";
	
	public static final String X_QUERY_FIND_ALL_REQUEST = "xquery version \"3.1\";\n" +
            "declare default element namespace \"http://www.projekat.org/zahtev\";\n" +
            "for $x in collection(\"/db/OrganVlasti/requests\")\n" +
            "return $x";
	
	public String save(String xmlEntity, String broj)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("save in repository entity = " + xmlEntity + " broj = " + broj);
		dbManager.storeXMLFromText(collectionId, broj, xmlEntity);
		return "SAVED";
	}
	
	public ArrayList<RequestItem> getAllForUser(String username) throws XMLDBException, ParserConfigurationException, SAXException, IOException {
		System.out.println("get all");
        org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
        XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
        CompiledExpression compiledExpression = xQueryService.compile(X_QUERY_FIND_ALL_REQUEST);
        ResourceSet resourceSet = xQueryService.execute(compiledExpression);
        ResourceIterator resourceIterator = resourceSet.getIterator();
        //ArrayList<Document> documents = new ArrayList<Document>();
        ArrayList<RequestItem> items = new ArrayList<>();
        while (resourceIterator.hasMoreResources()){
            XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
            //System.out.println(xmlResource.getContent());
            String content = xmlResource.getContent().toString();
            //sb.append(content);
            Document document = domParser.getDocument(content);
            //documents.add(document);
    		NodeList nodeList = document.getElementsByTagName("za:zahtev");
    		Element sp = (Element) nodeList.item(0);
    		RequestItem item = new RequestItem();
    		item.broj = sp.getAttribute("broj");
    		item.datum = sp.getAttribute("datum");
    		item.institucija = sp.getAttribute("institucija");
    		item.username = sp.getAttribute("username");
    		item.time = sp.getAttribute("time");
    		item.status = sp.getAttribute("status");
    		if(username.contentEquals(item.username)) {
        		items.add(item);    			
    		}
        }
        return items;
	}
	public ArrayList<RequestItem> getAll() throws XMLDBException, ParserConfigurationException, SAXException, IOException {
		System.out.println("get all");
        org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
        XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
        CompiledExpression compiledExpression = xQueryService.compile(X_QUERY_FIND_ALL_REQUEST);
        ResourceSet resourceSet = xQueryService.execute(compiledExpression);
        ResourceIterator resourceIterator = resourceSet.getIterator();
        //ArrayList<Document> documents = new ArrayList<Document>();
        ArrayList<RequestItem> items = new ArrayList<>();
        while (resourceIterator.hasMoreResources()){
            XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
            //System.out.println(xmlResource.getContent());
            String content = xmlResource.getContent().toString();
            //sb.append(content);
            Document document = domParser.getDocument(content);
            //documents.add(document);
    		NodeList nodeList = document.getElementsByTagName("za:zahtev");
    		Element sp = (Element) nodeList.item(0);
    		RequestItem item = new RequestItem();
    		item.broj = sp.getAttribute("broj");
    		item.datum = sp.getAttribute("datum");
    		item.institucija = sp.getAttribute("institucija");
    		item.username = sp.getAttribute("username");
    		item.time = sp.getAttribute("time");
    		item.status = sp.getAttribute("status");
    		items.add(item);
        }
        return items;
	}
	
	public void deleteRequest(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		dbManager.deleteDocument(collectionId, broj);
	}
	
	public void denyRequest(String broj) {
		Document document = findRequestById(broj);
		NodeList nodeList = document.getElementsByTagName("za:zahtev");
		//sp.getAttribute("status")
		Element sp = (Element) nodeList.item(0);
		sp.setAttribute("status", "denied");
		System.out.println("broj = " + broj);
		System.out.println(sp.getAttribute("broj"));
		//dbManager.updateDocument(0, broj, broj, contextXPath, patch);
		try {
			deleteRequest(broj);
			save(document.getTextContent(), broj);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public Document findRequestById(String id) {
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
