package com.xml.organvlasti.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xml.organvlasti.rdf.MetadataExtractor;
import com.xml.organvlasti.rdf.FusekiWriter;
import com.xml.organvlasti.rdf.FusekiReader;
import com.xml.organvlasti.model.keywordSearch.KeywordSearch;
import com.xml.organvlasti.model.resenje.Zalba;
import com.xml.organvlasti.model.responseList.ResponseList;
import com.xml.organvlasti.model.responseList.ResponseList.ResponseItem;
import com.xml.organvlasti.parser.DOMParser;
import com.xml.organvlasti.parser.XSLTransformer;
import com.xml.organvlasti.repository.ResponseRepository;

@Service()
public class ResponseService {

	private final String responseXSL = "src/main/resources/xsl/resenje.xsl";

	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private ResponseRepository repository;
	@Autowired
	private MetadataExtractor metadataExtractor;

	public void save(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto);
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("res:zalba");
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		sp.setAttribute("about", "http://www.projekat.org/resenje/" + broj);
		System.out.println("node broj = " + broj);
		//broj = broj.replace("/", "_");
		Document prev = null;
		try {
			System.out.println("findResponseByBroj call");
			prev = repository.findResponseByBroj(broj);
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
		
		metadataExtractor.extractMetadata(sw.toString(), MetadataExtractor.RESPONSE_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.RESPONSE_RDF_FILEPATH, FusekiWriter.RESPONSE_METADATA_GRAPH_URI);
	}
	
	public ResponseList searchByMetadata(Map<String, String> params) throws IOException {
        System.out.println("service executeQuerry!");
        ArrayList<Map<String, String>> result = FusekiReader.executeQuery(params, FusekiReader.RESPONSE_QUERY_FILEPATH);
        System.out.println("return result querry!");
        ArrayList<String> brojList = new ArrayList<>();
        for(Map<String, String> map : result) {
        	String zalba = map.get("resenje");
        	String[] split = zalba.split("\\/");
        	if(!brojList.contains(split[split.length-1])) {
        		brojList.add(split[split.length-1]);
        	}
        	/*System.out.println("map = ");
        	for(String key : map.keySet()) {
            	System.out.println("ket = " + key + " value = " + map.get(key));        		
        	}
        	System.out.println();*/
        }
        ResponseList response = new ResponseList();
        List<ResponseItem> itemsList = new ArrayList<>();
        
        for(String broj : brojList) {
        	Zalba zalba = repository.findResponseByIdMarshall(broj);

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

	public ResponseList getAll() throws XMLDBException, JAXBException, SAXException {
		return repository.getAll();
	}
	
	public ResponseList getAllForUsername(String username) throws XMLDBException, JAXBException, SAXException {
		return repository.getAllForUsername(username);
	}
	
	public String getHTML(String broj) {
		Document xml = repository.findResponseByBroj(broj);
		return xslTransformer.getHTMLfromXML(responseXSL, xml);
	}

	public ResponseList searchByKeywords(KeywordSearch s) throws XMLDBException, JAXBException, SAXException {
		return repository.searchByKeywords(s);
	}

	public void generateResponseJSON(String broj) throws IOException {
		FusekiReader.generateResponseJSON(broj);		
	}
	
	public void generateResponseRDF(String broj, String rdfPath) throws TransformerException, FileNotFoundException {
		Document document = repository.findResponseByBroj(broj);
		String xmlString = getStringFromDocument(document);
		metadataExtractor.extractMetadata(xmlString, rdfPath);
	}

	private String getStringFromDocument(Document document) throws TransformerException {
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
	
}
