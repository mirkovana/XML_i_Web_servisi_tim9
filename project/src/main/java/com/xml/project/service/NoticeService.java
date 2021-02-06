package com.xml.project.service;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.xml.project.dto.NoticeDTO;
import com.xml.project.model.noticeResponse.NoticeListResponse;
import com.xml.project.parser.DOMParser;
import com.xml.project.parser.XSLTransformer;
import com.xml.project.rdf.FusekiWriter;
import com.xml.project.rdf.MetadataExtractor;
import com.xml.project.repository.NoticeRepository;
import com.xml.project.repository.RequestRepository;

@Service()
public class NoticeService {

	private final String requestXSL = "src/main/resources/xsl/obavestenje.xsl";
	
	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private NoticeRepository repository;
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	public void save(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
//		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto);
//		System.out.println("got document = " + document);

		NodeList br_predmeta = document.getElementsByTagName("ob:broj_predmeta");
		Element el = (Element) br_predmeta.item(0);
		String broj = el.getTextContent(); // broj_predmeta

		Document prev = null;
		try {
			System.out.println("findNoticeById call");
			prev = repository.findNoticeById(broj);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("response already exist");
			return;
		}
		System.out.println("found response = " + prev);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		System.out.println("broj after = " + broj);
		repository.save(sw.toString(), broj + ".xml");
		
		requestService.acceptRequest(broj);
		
		metadataExtractor.extractMetadata(sw.toString(), MetadataExtractor.NOTICE_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.NOTICE_RDF_FILEPATH, FusekiWriter.NOTICE_METADATA_GRAPH_URI);
	}
	
	public NoticeListResponse getAll() throws XMLDBException, JAXBException, SAXException {
		return repository.getAll();
	}
	
	public NoticeListResponse getAllForOrganVlastiUsername(String username) throws XMLDBException, JAXBException, SAXException {
		return repository.getAllForUsername(username, NoticeRepository.X_QUERY_FIND_ALL_BY_ORGANVLASTI_USERNAME);
	}

	public NoticeListResponse getAllForUserUsername(String username) throws XMLDBException, JAXBException, SAXException {
		return repository.getAllForUsername(username, NoticeRepository.X_QUERY_FIND_ALL_BY_USER_USERNAME);
	}
	
	public void deleteNotice(String broj) {
		try {
			repository.deleteRequest(broj);
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
	
	public String getHTML(String broj) {
		Document xml = repository.findNoticeById(broj);
		return xslTransformer.getHTMLfromXML(requestXSL, xml);
	}
}
