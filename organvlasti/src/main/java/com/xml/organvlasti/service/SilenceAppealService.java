package com.xml.organvlasti.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

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

import com.xml.organvlasti.model.keywordSearch.KeywordSearch;
import com.xml.organvlasti.model.silenceAppealResponse.SAppealListResponse;
import com.xml.organvlasti.parser.DOMParser;
import com.xml.organvlasti.parser.XSLTransformer;
import com.xml.organvlasti.rdf.FusekiReader;
import com.xml.organvlasti.rdf.FusekiWriter;
import com.xml.organvlasti.rdf.MetadataExtractor;
import com.xml.organvlasti.repository.SilenceAppealRepository;

@Service()
public class SilenceAppealService {

	private final String requestXSL = "src/main/resources/xsl/zalbazbogcutanja.xsl";

	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private SilenceAppealRepository repository;
	@Autowired
	private MetadataExtractor metadataExtractor;

	public void save(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto);
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("zc:zalba_cutanje");
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		sp.setAttribute("about", "http://www.projekat.org/zalbazbogcutanja/" + broj);
		System.out.println("node broj = " + broj);
		//broj = broj.replace("/", "_");
		
		//String id = sp.getAttribute("id");
		System.out.println("node broj = " + broj);
		Document prev = null;
		try {
			System.out.println("findSilenceAppealById call");
			prev = repository.findSilenceAppealByBroj(broj);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("silence appeal already exist");
			return;
		}
		System.out.println("found silence appeal = " + prev);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		repository.save(sw.toString(), broj + ".xml");
		
		metadataExtractor.extractMetadata(sw.toString(), MetadataExtractor.SILENCE_APPEAL_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.SILENCE_APPEAL_RDF_FILEPATH, FusekiWriter.SILENCE_APPEAL_METADATA_GRAPH_URI);
	}
	
	public SAppealListResponse getAll() throws XMLDBException, JAXBException, SAXException {
		return repository.getAll();
	}
	
	public String getHTML(String id) {
		Document xml = repository.findSilenceAppealByBroj(id);
		return xslTransformer.getHTMLfromXML(requestXSL, xml);
	}

	public SAppealListResponse searchByKeywords(KeywordSearch s) throws XMLDBException, JAXBException, SAXException {
		return repository.searchByKeywords(s);
	}
	
	public void updateState(String broj) throws TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("updatestate = " + broj);
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		Document document = repository.findSilenceAppealByBroj(broj);
		NodeList nodeList = document.getElementsByTagName("zc:zalba_cutanje");
		Element sp = (Element) nodeList.item(0);
		sp.setAttribute("status", "answered");
		String xmlString;
		
		xmlString = repository.getStringFromDocument(document);
		xmlString = xmlString.replace("sent", "answered");
		xmlString = xmlString.replace("pending", "answered");
		repository.deleteAppeal(broj);
		System.out.println("updatedfile = " + xmlString);
		repository.save(xmlString, broj);		
	}
	
	public  ArrayList<Map<String, String>> search(Map<String, String> params) throws IOException {
//		 Map<String, String> params = new HashMap();
//	        params.put("ime", "asd");
//	        params.put("prezime", "asd");
		return FusekiReader.executeQuery(params, "src/main/resources/rdf/silenceAppealQuery.rq");
	}
}
