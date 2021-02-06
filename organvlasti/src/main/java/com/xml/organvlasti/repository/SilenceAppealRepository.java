package com.xml.organvlasti.repository;


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

import com.xml.organvlasti.database.DbManager;
import com.xml.organvlasti.model.keywordSearch.KeywordSearch;
import com.xml.organvlasti.model.silenceAppeal.Trazlozi.Razlog;
import com.xml.organvlasti.model.silenceAppeal.ZalbaCutanje;
import com.xml.organvlasti.model.silenceAppealResponse.SAppealListResponse;
import com.xml.organvlasti.model.silenceAppealResponse.SAppealListResponse.SAppealItem;
import com.xml.organvlasti.parser.JAXParser;

@Repository
public class SilenceAppealRepository {

	@Autowired
	private DbManager dbManager;
	
	private static String schemaPath = "src/main/resources/documents/zalbazbogcutanja.xsd";
	private static String contextPath = "com.xml.organvlasti.model.silenceAppeal";
	
	private String collectionId = "/db/OrganVlasti/silenceAppeals";
	
	public static final String X_QUERY_FIND_ALL_SILENCE_APPEALS = "xquery version \"3.1\";\n" +
            "declare default element namespace \"http://www.projekat.org/zalbazbogcutanja\";\n" +
            "for $x in collection(\"/db/OrganVlasti/silenceAppeals\")\n" +
            "return $x";	
	
	public static final String X_QUERY_FIND_ALL_BY_CONTENT = "xquery version \"3.1\";\n" + 
			"declare default element namespace \"http://www.projekat.org/zalbazbogcutanja\";\n" + 
            "for $x in collection(\"/db/OrganVlasti/silenceAppeals\")\n" +
			"where $x/zalba_cutanje[%s]\n" + 
			"return $x";
	
	public String save(String xmlEntity, String broj)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("save in repository entity = " + xmlEntity + " broj = " + broj);
		dbManager.storeXMLFromText(collectionId, broj, xmlEntity);
		return "SAVED";
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

	public void deleteAppeal(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		dbManager.deleteDocument(collectionId, broj);
	}
	
	public SAppealListResponse searchByKeywords(KeywordSearch keys) throws XMLDBException, JAXBException, SAXException {
		String[] keywords = keys.getKeywords().split(" ");
		//contains(.,'inform')
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < keywords.length-1; i++) {
			sb.append("contains(.,'"+keywords[i]+"') and ");
		}
		sb.append("contains(.,'"+keywords[keywords.length-1]+"')");
		System.out.println("keywords = " + sb.toString());
		//String QUERY = X_QUERY_FIND_ALL_BY_INFROMATION.replace("_", sb.toString());
		String QUERY = String.format(X_QUERY_FIND_ALL_BY_CONTENT, sb.toString());
        System.out.println("xquery string = " + QUERY);
        
        org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
        XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
        CompiledExpression compiledExpression = xQueryService.compile(QUERY);
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
}
