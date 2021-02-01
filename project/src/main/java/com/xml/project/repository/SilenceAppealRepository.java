package com.xml.project.repository;

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
import org.xml.sax.SAXException;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import com.xml.project.database.DbManager;
import com.xml.project.model.silenceAppeal.Trazlozi.Razlog;
import com.xml.project.model.decisionAppeal.ZalbaNaOdluku;
import com.xml.project.model.silenceAppeal.ZalbaCutanje;
import com.xml.project.model.silenceAppealResponse.SAppealListResponse;
import com.xml.project.model.silenceAppealResponse.SAppealListResponse.SAppealItem;
import com.xml.project.parser.JAXParser;

@Repository
public class SilenceAppealRepository {

	@Autowired
	private DbManager dbManager;
	
	private String collectionId = "/db/XmlProject/silenceAppeals";

	private static String schemaPath = "src/main/resources/documents/zalbazbogcutanja.xsd";
	private static String contextPath = "com.xml.project.model.silenceAppeal";
										
	public static final String X_QUERY_FIND_ALL_SILENCE_APPEALS = "xquery version \"3.1\";\n" +
            "declare default element namespace \"http://www.projekat.org/zalbazbogcutanja\";\n" +
            "for $x in collection(\"/db/XmlProject/silenceAppeals\")\n" +
            "return $x";			
	
	public static final String X_QUERY_FIND_ALL_BY_USERNAME = "xquery version \"3.1\";\n" + 
			"declare default element namespace \"http://www.projekat.org/zalbazbogcutanja\";\n" + 
			"for $x in collection(\"/db/XmlProject/silenceAppeals\")\n" + 
			"where $x/zalba_cutanje/@username='%s'\n" + 
			"return $x\n";
	
	public String save(String xmlEntity, String broj)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("save in repository entity = " + xmlEntity + " broj = " + broj);
		dbManager.storeXMLFromText(collectionId, broj, xmlEntity);
		return "SAVED";
	}
	
	public SAppealListResponse getAllForUsername(String username) throws XMLDBException, JAXBException, SAXException {
		System.out.println("getallforunsername = " + username);
		org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
        XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
        String xqueryString = String.format(X_QUERY_FIND_ALL_BY_USERNAME, username);
        System.out.println("xquery string = " + xqueryString);
        
        CompiledExpression compiledExpression = xQueryService.compile(xqueryString);
        ResourceSet resourceSet = xQueryService.execute(compiledExpression);
        ResourceIterator resourceIterator = resourceSet.getIterator();
        SAppealListResponse response = new SAppealListResponse();
        List<SAppealItem> itemsList = new ArrayList<>();
        while (resourceIterator.hasMoreResources()){
        	XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
    		Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
    		ZalbaCutanje zalba = (ZalbaCutanje) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
    		
    		SAppealItem item = new SAppealItem();
    		item.setBroj(zalba.getBroj());
    		item.setDatumSlanja(zalba.getPodaciOMestuIDatumuPodnosenjaZalbe().getDatum().getValue());
    		item.setMestoSlanja(zalba.getPodaciOMestuIDatumuPodnosenjaZalbe().getMesto().getValue());
    		item.setOrganVlasti(zalba.getTeloZalbe().getNazivOrgana().getValue());
    		item.setPodnosiocGrad(zalba.getPodaciOPodnosiocuZalbe().getAdresa().getGrad());
    		item.setPodnosiocIme(zalba.getPodaciOPodnosiocuZalbe().getIme().getValue());
    		item.setPodnosiocPrezime(zalba.getPodaciOPodnosiocuZalbe().getPrezime().getValue());
    		item.setPodnosiocUlica(zalba.getPodaciOPodnosiocuZalbe().getAdresa().getUlica());
    		item.setPodnosiocUsername(zalba.getUsername());
    		item.setPoverenikUsername(zalba.getPoverenikUsername());
    		String razlog = "";
    		for(Razlog s : zalba.getTeloZalbe().getRazlozi().getRazlog()) {
    			if(s.isPodvuceno()) {
    				razlog = s.getValue();
    			}
    		}
    		item.setRazlog(razlog);
    		item.setStatus(zalba.getZalbaStatus().getValue());
    		itemsList.add(item);
        }
        response.setsAppealItem(itemsList);
        System.out.println("find all silenceappeals byusername = " + response);
        return response;
	}
	
	public SAppealListResponse getAll() throws XMLDBException, JAXBException, SAXException {
		System.out.println("get all");
        org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
        XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
        CompiledExpression compiledExpression = xQueryService.compile(X_QUERY_FIND_ALL_SILENCE_APPEALS);
        ResourceSet resourceSet = xQueryService.execute(compiledExpression);
        ResourceIterator resourceIterator = resourceSet.getIterator();
        SAppealListResponse response = new SAppealListResponse();
        List<SAppealItem> itemsList = new ArrayList<>();
        while (resourceIterator.hasMoreResources()){
        	XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
    		Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
    		ZalbaCutanje zalba = (ZalbaCutanje) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
    		
    		SAppealItem item = new SAppealItem();
    		item.setBroj(zalba.getBroj());
    		item.setDatumSlanja(zalba.getPodaciOMestuIDatumuPodnosenjaZalbe().getDatum().getValue());
    		item.setMestoSlanja(zalba.getPodaciOMestuIDatumuPodnosenjaZalbe().getMesto().getValue());
    		item.setOrganVlasti(zalba.getTeloZalbe().getNazivOrgana().getValue());
    		item.setPodnosiocGrad(zalba.getPodaciOPodnosiocuZalbe().getAdresa().getGrad());
    		item.setPodnosiocIme(zalba.getPodaciOPodnosiocuZalbe().getIme().getValue());
    		item.setPodnosiocPrezime(zalba.getPodaciOPodnosiocuZalbe().getPrezime().getValue());
    		item.setPodnosiocUlica(zalba.getPodaciOPodnosiocuZalbe().getAdresa().getUlica());
    		item.setPodnosiocUsername(zalba.getUsername());
    		item.setPoverenikUsername(zalba.getPoverenikUsername());
    		String razlog = "";
    		for(Razlog s : zalba.getTeloZalbe().getRazlozi().getRazlog()) {
    			if(s.isPodvuceno()) {
    				razlog = s.getValue();
    			}
    		}
    		item.setRazlog(razlog);
    		item.setStatus(zalba.getZalbaStatus().getValue());
    		itemsList.add(item);
        }
        response.setsAppealItem(itemsList);
        System.out.println("find all silenceappeals = " + response);
        return response;
	}
	
	public void deleteAppeal(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		dbManager.deleteDocument(collectionId, broj);
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

	public ZalbaCutanje findAppealByIdMarshall(String id) {
		ZalbaCutanje zalba = null;
		if (!id.endsWith(".xml")) {
			id = id + ".xml";
		}
		try {
			XMLResource xmlResource = dbManager.getDocument(collectionId, id);
			Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
			zalba = (ZalbaCutanje) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return zalba;
	}
}
