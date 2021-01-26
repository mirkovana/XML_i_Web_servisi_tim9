package com.xml.organvlasti.repository;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

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

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.xml.organvlasti.database.DbManager;
import com.xml.organvlasti.model.resenje.Zalba;
import com.xml.organvlasti.model.responseList.ResponseList;
import com.xml.organvlasti.model.responseList.ResponseList.ResponseItem;
import com.xml.organvlasti.parser.JAXParser;


@Repository
public class ResponseRepository {

	@Autowired
	private DbManager dbManager;
	
	private String collectionId = "/db/OrganVlasti/responses";
	private static String schemaPath = "src/main/resources/documents/resenje.xsd";
	private static String contextPath = "com.xml.organvlasti.model.resenje";
	
	public static final String X_QUERY_FIND_ALL_RESPONSES= "xquery version \"3.1\";\n" +
            "declare default element namespace \"http://www.projekat.org/resenje\";\n" +
            "for $x in collection(\"/db/OrganVlasti/responses\")\n" +
            "return $x";			
	
	public static final String X_QUERY_FIND_ALL_BY_USERNAME = "xquery version \"3.1\";\n" + 
			"declare default element namespace \"http://www.projekat.org/resenje\";\n" + 
			"for $x in collection(\"/db/OrganVlasti/responses\")\n" + 
			"where $x/zalba/@username='%s'\n" + 
			"return $x\n";
	
	public String save(String xmlEntity, String broj)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("save in repository entity = " + xmlEntity + " broj = " + broj);
		dbManager.storeXMLFromText(collectionId, broj, xmlEntity);
		return "SAVED";
	}
	
	public ResponseList getAllForUsername(String username) throws XMLDBException, JAXBException, SAXException {
		System.out.println("getallforunsername = " + username);
		org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
        XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
        String xqueryString = String.format(X_QUERY_FIND_ALL_BY_USERNAME, username);
        System.out.println("xquery string = " + xqueryString);
        
        CompiledExpression compiledExpression = xQueryService.compile(xqueryString);
        ResourceSet resourceSet = xQueryService.execute(compiledExpression);
        ResourceIterator resourceIterator = resourceSet.getIterator();
        ResponseList response = new ResponseList();
        List<ResponseItem> itemsList = new ArrayList<>();
        while (resourceIterator.hasMoreResources()){
        	XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
    		Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
    		Zalba zalba = (Zalba) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
    		
    		ResponseItem item = new ResponseItem();
    		item.setBroj(zalba.getBroj());
    		item.setDatum(zalba.getDatum());
    		item.setPodnosiocUsername(zalba.getUsername());
    		item.setPoverenikIme(zalba.getSadrzaj().getPoverenik().getIme().getValue());
    		item.setPoverenikPrezime(zalba.getSadrzaj().getPoverenik().getPrezime().getValue());
    		item.setStatus(zalba.getStatus());
    		
    		itemsList.add(item);
        }
        response.setResponseItem(itemsList);
        System.out.println("find all responses by username = " + response);
        return response;
	}
	
	public ResponseList getAll() throws XMLDBException, JAXBException, SAXException {
		System.out.println("get all");
        org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
        XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
        CompiledExpression compiledExpression = xQueryService.compile(X_QUERY_FIND_ALL_RESPONSES);
        ResourceSet resourceSet = xQueryService.execute(compiledExpression);
        ResourceIterator resourceIterator = resourceSet.getIterator();
        ResponseList response = new ResponseList();
        List<ResponseItem> itemsList = new ArrayList<>();
        while (resourceIterator.hasMoreResources()){
        	XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
    		Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
    		Zalba zalba = (Zalba) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
    		
    		ResponseItem item = new ResponseItem();
    		item.setBroj(zalba.getBroj());
    		item.setDatum(zalba.getDatum());
    		item.setPodnosiocUsername(zalba.getUsername());
    		item.setPoverenikIme(zalba.getSadrzaj().getPoverenik().getIme().getValue());
    		item.setPoverenikPrezime(zalba.getSadrzaj().getPoverenik().getPrezime().getValue());
    		item.setStatus(zalba.getStatus());
    		
    		itemsList.add(item);
        }
        response.setResponseItem(itemsList);
        System.out.println("find all responses = " + response);
        return response;
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
