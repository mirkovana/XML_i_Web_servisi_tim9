package com.xml.project.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import com.xml.project.database.DbManager;
import com.xml.project.model.decisionAppeal.ZalbaNaOdluku;
import com.xml.project.model.decisionAppealResponse.DAppealListResponse;
import com.xml.project.model.decisionAppealResponse.DAppealListResponse.DAppealItem;
import com.xml.project.model.request.Zahtev;
import com.xml.project.model.resenje.Zalba;
import com.xml.project.parser.JAXParser;
import com.xml.project.rdf.FusekiWriter;
import com.xml.project.rdf.MetadataExtractor;

@Repository
public class DecisionAppealRepository {
	
	@Autowired
	private DbManager dbManager;
	
	private String collectionId = "/db/XmlProject/decisionAppeals";
	
	private static String schemaPath = "src/main/resources/documents/zalbanaodluku.xsd";
	private static String contextPath = "com.xml.project.model.decisionAppeal";
										
	public static final String X_QUERY_FIND_ALL_DECISION_APPEALS = "xquery version \"3.1\";\n" +
            "declare default element namespace \"http://www.projekat.org/zalbanaodluku\";\n" +
            "for $x in collection(\"/db/XmlProject/decisionAppeals\")\n" +
            "return $x";			
	
	public static final String X_QUERY_FIND_ALL_BY_USERNAME = "xquery version \"3.1\";\n" + 
			"declare default element namespace \"http://www.projekat.org/zalbanaodluku\";\n" + 
			"for $x in collection(\"/db/XmlProject/decisionAppeals\")\n" + 
			"where $x/zalba_na_odluku/@username='%s'\n" + 
			"return $x\n";
	
	public String save(String xmlEntity, String broj)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("save in repository entity = " + xmlEntity + " broj = " + broj);
		dbManager.storeXMLFromText(collectionId, broj, xmlEntity);
		return "SAVED";
	}
	
	public DAppealListResponse getAllForUsername(String username) throws XMLDBException, JAXBException, SAXException {
		System.out.println("getallforunsername = " + username);
		org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
        XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
        String xqueryString = String.format(X_QUERY_FIND_ALL_BY_USERNAME, username);
        System.out.println("xquery string = " + xqueryString);
        
        CompiledExpression compiledExpression = xQueryService.compile(xqueryString);
        ResourceSet resourceSet = xQueryService.execute(compiledExpression);
        ResourceIterator resourceIterator = resourceSet.getIterator();
        DAppealListResponse response = new DAppealListResponse();
        List<DAppealItem> itemsList = new ArrayList<>();
        while (resourceIterator.hasMoreResources()){
        	XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
    		Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
    		ZalbaNaOdluku zalba = (ZalbaNaOdluku) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
    		
    		DAppealItem item = new DAppealItem();
    		item.setBroj(zalba.getBroj());
    		item.setDatumSlanja(zalba.getPodaciOMestuIDatumuPodnosenjaZalbe().getDatum().getValue());
    		item.setMestoSlanja(zalba.getPodaciOMestuIDatumuPodnosenjaZalbe().getMesto().getValue());
    		item.setOrganVlasti(zalba.getPodaciOZalbi().getOrganKojiJeDoneoOdluku().getValue());
    		item.setPodnosiocGrad(zalba.getPodaciOZalbi().getPodnosilacZalbe().getAdresa().getGrad());
    		item.setPodnosiocIme(zalba.getPodaciOZalbi().getPodnosilacZalbe().getIme().getValue());
    		item.setPodnosiocPrezime(zalba.getPodaciOZalbi().getPodnosilacZalbe().getPrezime().getValue());
    		item.setPodnosiocUlica(zalba.getPodaciOZalbi().getPodnosilacZalbe().getAdresa().getUlica());
    		item.setPodnosiocUsername(zalba.getUsername());
    		item.setPoverenikUsername(zalba.getPoverenikUsername());
    		item.setStatus(zalba.getZalbaStatus().getValue());
    		itemsList.add(item);
        }
        response.setdAppealItem(itemsList);
        System.out.println("find all decsisionappeals byusername = " + response);
        return response;
	}
	
	public DAppealListResponse getAll() throws XMLDBException, JAXBException, SAXException {
		System.out.println("get all");
        org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
        XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
        CompiledExpression compiledExpression = xQueryService.compile(X_QUERY_FIND_ALL_DECISION_APPEALS);
        ResourceSet resourceSet = xQueryService.execute(compiledExpression);
        ResourceIterator resourceIterator = resourceSet.getIterator();
        DAppealListResponse response = new DAppealListResponse();
        List<DAppealItem> itemsList = new ArrayList<>();
        while (resourceIterator.hasMoreResources()){
        	XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
    		Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
    		ZalbaNaOdluku zalba = (ZalbaNaOdluku) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
    		
    		DAppealItem item = new DAppealItem();
    		item.setBroj(zalba.getBroj());
    		item.setDatumSlanja(zalba.getPodaciOMestuIDatumuPodnosenjaZalbe().getDatum().getValue());
    		item.setMestoSlanja(zalba.getPodaciOMestuIDatumuPodnosenjaZalbe().getMesto().getValue());
    		item.setOrganVlasti(zalba.getPodaciOZalbi().getOrganKojiJeDoneoOdluku().getValue());
    		item.setPodnosiocGrad(zalba.getPodaciOZalbi().getPodnosilacZalbe().getAdresa().getGrad());
    		item.setPodnosiocIme(zalba.getPodaciOZalbi().getPodnosilacZalbe().getIme().getValue());
    		item.setPodnosiocPrezime(zalba.getPodaciOZalbi().getPodnosilacZalbe().getPrezime().getValue());
    		item.setPodnosiocUlica(zalba.getPodaciOZalbi().getPodnosilacZalbe().getAdresa().getUlica());
    		item.setPodnosiocUsername(zalba.getUsername());
    		item.setPoverenikUsername(zalba.getPoverenikUsername());
    		item.setStatus(zalba.getZalbaStatus().getValue());
    		itemsList.add(item);
        }
        response.setdAppealItem(itemsList);
        System.out.println("find all decsisionappeals = " + response);
        return response;
	}
	
	public void deleteAppeal(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		dbManager.deleteDocument(collectionId, broj);
	}
	
	public Document findDecisionAppealByBroj(String id) {
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
	
	public String getStringFromDocument(Document document) throws TransformerException {
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));	
		return sw.toString();
	}

	public ZalbaNaOdluku findAppealByIdMarshall(String id) {
		ZalbaNaOdluku zalba = null;
		if (!id.endsWith(".xml")) {
			id = id + ".xml";
		}
		try {
			XMLResource xmlResource = dbManager.getDocument(collectionId, id);
			Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
			zalba = (ZalbaNaOdluku) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return zalba;
	}
}
