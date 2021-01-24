package com.xml.organvlasti.repository;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.exist.xquery.functions.util.SystemTime;
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

import com.spring.soap.ws.hello.AuthenticationUtilities;
import com.xml.organvlasti.database.DbManager;
import com.xml.organvlasti.parser.DOMParser;
import com.xml.organvlasti.parser.JAXParser;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RequestRepository {

	private DbManager dbManager;
	private DOMParser domParser;
	
	private String collectionId = "/db/OrganVlasti/requests";
	
	public RequestRepository() {
		try {
			dbManager = new DbManager(AuthenticationUtilities.loadProperties());
		} catch (IOException e) {
			e.printStackTrace();
		}
		domParser = new DOMParser();
	}
	
	private static String schemaPath = "src/main/resources/documents/zahtev.xsd";
	private static String contextPath = "com.xml.organvlasti.model.request";
	
	public static final String X_QUERY_FIND_ALL_REQUEST = "xquery version \"3.1\";\n" +
            "declare default element namespace \"http://www.projekat.org/zahtev\";\n" +
            "for $x in collection(\"/db/OrganVlasti/requests\")\n" +
           "return $x";
	
	public static final String X_QUERY_FIND_ALL_BY_USERNAME = "xquery version \"3.1\";\n" + 
			"declare default element namespace \"http://www.projekat.org/zahtev\";\n" + 
			"for $x in collection(\"/db/OrganVlasti/requests\")\n" + 
			"where $x/zahtev/@username='%s'\n" + 
			"return $x\n";

	public static final String X_UPDATE_UPDATE_REQUEST_BY_ID_EXPRESSION = "xquery version \"3.1\";\n" +
	            "xmldb:update(\"/db/sample/zalbe_na_odluku\",\n" +
	            "    <xu:modifications version=\"1.0\"\n" +
	            "    \txmlns:xu=\"http://www.xmldb.org/xupdate\"\n" +
	            "    \txmlns=\"http://www.projekat.org/zahtev\"\n" +
	            "    \t<xu:update select=\"doc('/db/OrganVlasti/requests/%s')/zahtev\">\n" +
	            "    \t%s" +
	            "    \t</xu:update>\n" +
	            "    </xu:modifications>)\n";
	
	
	
	public String getStringFromDocument(Document document) throws TransformerException {
		System.out.println("getstringfromdocument");
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
	
	public Document findRequestById(String id) {
		Document document = null;
		System.out.println("in repository finding id = " + id);
		if (!id.endsWith(".xml")) {
			id = id + ".xml";
		}
		System.out.println("id = " + id);
		try {
			XMLResource xmlResource = dbManager.getDocument(collectionId, id);
			document = (Document) xmlResource.getContentAsDOM();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
}
