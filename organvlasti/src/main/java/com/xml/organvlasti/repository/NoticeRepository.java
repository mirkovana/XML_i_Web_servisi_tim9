package com.xml.organvlasti.repository;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
import com.xml.organvlasti.model.notice.Obavestenje;
import com.xml.organvlasti.model.noticeResponse.NoticeListResponse;
import com.xml.organvlasti.model.noticeResponse.NoticeListResponse.NoticeItem;
import com.xml.organvlasti.model.request.Zahtev;
import com.xml.organvlasti.model.zahtevResponse.RequestListResponse;
import com.xml.organvlasti.model.zahtevResponse.RequestListResponse.RequestItem;
import com.xml.organvlasti.parser.JAXParser;

@Repository
public class NoticeRepository {
	@Autowired
	private DbManager dbManager;

	private String collectionId = "/db/OrganVlasti/notices";

	private static String schemaPath = "src/main/resources/documents/Obavestenje.xsd";
	private static String contextPath = "com.xml.organvlasti.model.notice";

	public static final String X_QUERY_FIND_ALL_NOTICE = "xquery version \"3.1\";\n"
			+ "declare default element namespace \"http://www.projekat.org/obavestenje\";\n"
			+ "for $x in collection(\"/db/OrganVlasti/notices\")\n" + "return $x";

	public static final String X_QUERY_FIND_ALL_BY_ORGANVLASTI_USERNAME = "xquery version \"3.1\";\n"
			+ "declare default element namespace \"http://www.projekat.org/obavestenje\";\n"
			+ "for $x in collection(\"/db/OrganVlasti/notices\")\n" + "where $x/obavestenje/@organVlastiUsername='%s'\n"
			+ "return $x\n";

	public static final String X_QUERY_FIND_ALL_BY_USER_USERNAME = "xquery version \"3.1\";\n"
			+ "declare default element namespace \"http://www.projekat.org/obavestenje\";\n"
			+ "for $x in collection(\"/db/OrganVlasti/notices\")\n" + "where $x/obavestenje/@username='%s'\n"
			+ "return $x\n";

	public static final String X_QUERY_FIND_ALL_BY_CONTENT = "xquery version \"3.1\";\n"
			+ "declare default element namespace \"http://www.projekat.org/obavestenje\";\n"
			+ "for $x in collection(\"/db/OrganVlasti/notices\")\n" + "where $x/obavestenje[%s]\n" + "return $x";

	public String save(String xmlEntity, String broj)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("save in repository entity = " + xmlEntity + " broj = " + broj);
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		dbManager.storeXMLFromText(collectionId, broj, xmlEntity);
		return "SAVED";
	}

	public NoticeListResponse getAllForUsername(String username, String xquery)
			throws XMLDBException, JAXBException, SAXException {
		System.out.println("get all for username = " + username);
		org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
		XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");

		String xqueryString = String.format(xquery, username);
		System.out.println("xquery string = " + xqueryString);

		CompiledExpression compiledExpression = xQueryService.compile(xqueryString);
		ResourceSet resourceSet = xQueryService.execute(compiledExpression);
		ResourceIterator resourceIterator = resourceSet.getIterator();
		NoticeListResponse response = new NoticeListResponse();
		List<NoticeItem> itemsList = new ArrayList<>();
		while (resourceIterator.hasMoreResources()) {
			XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
			Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
			Obavestenje notice = (Obavestenje) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
			System.out.println("notice = " + notice);

			NoticeItem item = new NoticeItem();
			item.setBroj(notice.getBroj());
			item.setDatum(notice.getDatum());
			item.setImePodnosioca(notice.getOpsteInformacije().getPodaciOPodnosiocu().getIme().getValue());
			item.setPrezimePodnosioca(notice.getOpsteInformacije().getPodaciOPodnosiocu().getPrezime().getValue());
			item.setIznos(Double.toString(notice.getTelo().getIznos()));
			item.setNazivOrgana(notice.getOpsteInformacije().getPodaciOOrganu().getNaziv().getValue());
			item.setOrganVlastiUsername(notice.getOrganVlastiUsername());
			item.setSedisteOrgana(notice.getOpsteInformacije().getPodaciOOrganu().getSediste().getValue());
			item.setUsername(notice.getUsername());
			itemsList.add(item);
		}
		response.setNoticeItem(itemsList);
		System.out.println("find all for organvlastiusername response = " + response);
		return response;
	}

	public NoticeListResponse getAll() throws XMLDBException, JAXBException, SAXException {
		System.out.println("get all notice");
		org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
		XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");

		CompiledExpression compiledExpression = xQueryService.compile(X_QUERY_FIND_ALL_NOTICE);
		ResourceSet resourceSet = xQueryService.execute(compiledExpression);
		ResourceIterator resourceIterator = resourceSet.getIterator();
		NoticeListResponse response = new NoticeListResponse();
		List<NoticeItem> itemsList = new ArrayList<>();
		while (resourceIterator.hasMoreResources()) {
			XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
			Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
			Obavestenje notice = (Obavestenje) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
			System.out.println("notice = " + notice);

			NoticeItem item = new NoticeItem();
			item.setBroj(notice.getBroj());
			item.setDatum(notice.getDatum());
			item.setImePodnosioca(notice.getOpsteInformacije().getPodaciOPodnosiocu().getIme().getValue());
			item.setPrezimePodnosioca(notice.getOpsteInformacije().getPodaciOPodnosiocu().getPrezime().getValue());
			item.setIznos(Double.toString(notice.getTelo().getIznos()));
			item.setNazivOrgana(notice.getOpsteInformacije().getPodaciOOrganu().getNaziv().getValue());
			item.setOrganVlastiUsername(notice.getOrganVlastiUsername());
			item.setSedisteOrgana(notice.getOpsteInformacije().getPodaciOOrganu().getSediste().getValue());
			item.setUsername(notice.getUsername());
			itemsList.add(item);
		}
		response.setNoticeItem(itemsList);
		System.out.println("find all notice response = " + response);
		return response;
	}

	public void deleteRequest(String broj)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		dbManager.deleteDocument(collectionId, broj);
	}

	public Document findNoticeById(String id) {
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

	public Obavestenje findNoticeByIdMarshall(String id) {
		Obavestenje notice = null;
		if (!id.endsWith(".xml")) {
			id = id + ".xml";
		}
		try {
			XMLResource xmlResource = dbManager.getDocument(collectionId, id);
			Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
			notice = (Obavestenje) unmarshaller.unmarshal(xmlResource.getContentAsDOM());
		} catch (Exception e) {
			System.out.println("OVDE PUCA");
			e.printStackTrace();
		}
		return notice;
	}

	public NoticeListResponse searchByKeywords(KeywordSearch s) throws XMLDBException, JAXBException, SAXException {
		NoticeListResponse ret = new NoticeListResponse();
		
		String[] keywords = s.getKeywords().split(" ");
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < keywords.length - 1; i++) {
			sb.append("contains(.,'" + keywords[i] + "') and ");
		}
		
		sb.append("contains(.,'" + keywords[keywords.length - 1] + "')");

		// String QUERY = X_QUERY_FIND_ALL_BY_INFROMATION.replace("_", sb.toString());
		String QUERY = String.format(X_QUERY_FIND_ALL_BY_CONTENT, sb.toString());

		org.xmldb.api.base.Collection collection = dbManager.getCollection(collectionId);
		XQueryService xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");

		CompiledExpression compiledExpression = xQueryService.compile(QUERY);
		ResourceSet resourceSet = xQueryService.execute(compiledExpression);
		ResourceIterator resourceIterator = resourceSet.getIterator();
		
		List<NoticeItem> itemsList = new ArrayList<>();
		while (resourceIterator.hasMoreResources()) {
			XMLResource xmlResource = (XMLResource) resourceIterator.nextResource();
			Unmarshaller unmarshaller = JAXParser.createUnmarshaller(contextPath, schemaPath);
			Obavestenje notice = (Obavestenje) unmarshaller.unmarshal(xmlResource.getContentAsDOM());

			NoticeItem item = new NoticeItem();
			item.setBroj(notice.getBroj());
			item.setDatum(notice.getDatum());
			item.setImePodnosioca(notice.getOpsteInformacije().getPodaciOPodnosiocu().getIme().getValue());
			item.setPrezimePodnosioca(notice.getOpsteInformacije().getPodaciOPodnosiocu().getPrezime().getValue());
			item.setIznos(Double.toString(notice.getTelo().getIznos()));
			item.setNazivOrgana(notice.getOpsteInformacije().getPodaciOOrganu().getNaziv().getValue());
			item.setOrganVlastiUsername(notice.getOrganVlastiUsername());
			item.setSedisteOrgana(notice.getOpsteInformacije().getPodaciOOrganu().getSediste().getValue());
			item.setUsername(notice.getUsername());
			itemsList.add(item);
		}
		ret.setNoticeItem(itemsList);
		return ret;
	}
}
