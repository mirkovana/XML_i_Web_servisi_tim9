package com.xml.project.repository;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import com.xml.project.database.DbManager;
import com.xml.project.model.report.Izvestaj;
import com.xml.project.model.reportListItem.ReportListResponse;
import com.xml.project.model.reportListItem.ReportListResponse.ReportItem;
import com.xml.project.parser.JAXParser;

import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

@Repository
public class ReportRepository {

	@Autowired
	private DbManager dbManager;
	
	private String collectionId = "/db/XmlProject/reports";
	private static String schemaPath = "src/main/resources/documents/izvestaj.xsd";
	private static String contextPath = "com.xml.project.model.report";
	
	public static final String X_QUERY_FIND_ALL_REPORT = "xquery version \"3.1\";\n" +
            "for $x in collection(\"/db/XmlProject/reports\")\n" +
            "return $x";
	
	public Document findReportById(String id) {
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

	public ReportListResponse getAll() throws XMLDBException, JAXBException, SAXException {
		System.out.println("get all");
        org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
        XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
        CompiledExpression compiledExpression = xQueryService.compile(X_QUERY_FIND_ALL_REPORT);
        ResourceSet resourceSet = xQueryService.execute(compiledExpression);
        ResourceIterator resourceIterator = resourceSet.getIterator();
        ReportListResponse response = new ReportListResponse();
        List<ReportItem> itemsList = new ArrayList<>();
        while (resourceIterator.hasMoreResources()){
        	XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
    		Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
    		Izvestaj izvestaj = (Izvestaj) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
    		
    		ReportItem item = new ReportItem();
    		item.setDatum(izvestaj.getDatum());
    		item.setId(izvestaj.getId());
    		
    		item.setZahtevBrojOdbacenih(izvestaj.getZahtevi().getBrojOdbacenih());
    		item.setZahtevBrojOdbijenih(izvestaj.getZahtevi().getBrojOdbijenih());
    		item.setZahtevBrojPodnetih(izvestaj.getZahtevi().getBrojPodnetih());
    		item.setZahtevBrojUsvojenih(izvestaj.getZahtevi().getBrojUsvojenih());
    		
    		item.setZalbaBrojPodnetih(izvestaj.getZalbe().getBrojPodnetih());
    		item.setZalbaBrojZbogNepostupanja(izvestaj.getZalbe().getBrojZbogNepostupanja());
    		item.setZalbaBrojZbogOdbijanja(izvestaj.getZalbe().getBrojZbogOdbijanja());
    		
    		itemsList.add(item); 
        }
        response.setReportItem(itemsList);
        System.out.println("find all response = " + response);
        return response;
	}
}
