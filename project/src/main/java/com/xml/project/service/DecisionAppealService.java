package com.xml.project.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
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
import org.xmldb.api.base.XMLDBException;

import com.xml.project.dto.DecisionAppealDTO;
import com.xml.project.model.decisionAppeal.ZalbaNaOdluku;
import com.xml.project.model.decisionAppealResponse.DAppealListResponse;
import com.xml.project.model.decisionAppealResponse.DAppealListResponse.DAppealItem;
import com.xml.project.model.keywordSearch.KeywordSearch;
import com.xml.project.model.responseList.ResponseList;
import com.xml.project.model.responseList.ResponseList.ResponseItem;
import com.xml.project.model.zahtevResponse.RequestListResponse;
import com.xml.project.parser.DOMParser;
import com.xml.project.parser.XSLTransformer;
import com.xml.project.rdf.FusekiReader;
import com.xml.project.rdf.FusekiWriter;
import com.xml.project.rdf.MetadataExtractor;
import com.xml.project.repository.DecisionAppealRepository;
import com.xml.project.repository.RequestRepository;

@Service()
public class DecisionAppealService {

	private final String responseXSL = "src/main/resources/xsl/zalbanaodluku.xsl";

	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private DecisionAppealRepository repository;
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	public void save(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto);
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("zo:zalba_na_odluku");
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		System.out.println("node broj = " + broj);
		//broj = broj.replace("/", "_");
		Document prev = null;
		try {
			System.out.println("findDecisionAppealById call");
			prev = repository.findDecisionAppealByBroj(broj);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("decision already exist");
			return;
		}
		System.out.println("found decision = " + prev);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		repository.save(sw.toString(), broj + ".xml");
		
		metadataExtractor.extractMetadata(sw.toString(), MetadataExtractor.DECISION_APPEAL_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.DECISION_APPEAL_RDF_FILEPATH, FusekiWriter.DECISION_APPEAL_METADATA_GRAPH_URI);
	}
	
	public DAppealListResponse getAll() throws XMLDBException, JAXBException, SAXException {
		return repository.getAll();
	}
	
	public DAppealListResponse getAllForUsername(String username) throws XMLDBException, JAXBException, SAXException {
		return repository.getAllForUsername(username);
	}
	
	public void updateState(String broj) throws TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, IOException {
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		Document document = repository.findDecisionAppealByBroj(broj);
		NodeList nodeList = document.getElementsByTagName("zo:zalba_na_odluku");
		Element sp = (Element) nodeList.item(0);
		sp.setAttribute("status", "pending");
		String xmlString;
		
		xmlString = repository.getStringFromDocument(document);
		xmlString = xmlString.replace("sent", "pending");
		System.out.println("updatedstate = " + xmlString);
		repository.deleteAppeal(broj);
		repository.save(xmlString, broj);
		metadataExtractor.extractMetadata(xmlString, MetadataExtractor.DECISION_APPEAL_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.DECISION_APPEAL_RDF_FILEPATH, FusekiWriter.DECISION_APPEAL_METADATA_GRAPH_URI);
	}
	
	public void updateStateResolved(String broj) throws TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, IOException {
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		Document document = repository.findDecisionAppealByBroj(broj);
		NodeList nodeList = document.getElementsByTagName("zo:zalba_na_odluku");
		Element sp = (Element) nodeList.item(0);
		sp.setAttribute("status", "resolved");
		String xmlString;
		
		xmlString = repository.getStringFromDocument(document);
		xmlString = xmlString.replace("answered", "resolved");
		System.out.println("updatedstate = " + xmlString);
		repository.deleteAppeal(broj);
		repository.save(xmlString, broj);
		metadataExtractor.extractMetadata(xmlString, MetadataExtractor.DECISION_APPEAL_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.DECISION_APPEAL_RDF_FILEPATH, FusekiWriter.DECISION_APPEAL_METADATA_GRAPH_URI);
	}
	
	public void deleteAppeal(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		repository.deleteAppeal(broj);
		requestRepository.deleteRequest(broj);
	}
	
	public String getAppealXml(String broj) throws TransformerException {
		Document document = repository.findDecisionAppealByBroj(broj);
		String xml = repository.getStringFromDocument(document);
		return xml;
	}
	
	public String getHTML(String id) {
		Document xml = repository.findDecisionAppealByBroj(id);
		return xslTransformer.getHTMLfromXML(responseXSL, xml);
	}

	public DAppealListResponse searchByMetadata(Map<String, String> params) throws IOException {
		System.out.println("service executeQuerry!");
        ArrayList<Map<String, String>> result = FusekiReader.executeQuery(params, FusekiReader.DECISION_APPEAL_QUERY_FILEPATH);
        System.out.println("return result querry!");
        ArrayList<String> brojList = new ArrayList<>();
        for(Map<String, String> map : result) {
        	String zalbaNaOdluku = map.get("zalba_na_odluku");
        	String[] split = zalbaNaOdluku.split("\\/");
        	brojList.add(split[split.length-1]);
        	System.out.println("map = ");
        	for(String key : map.keySet()) {
            	System.out.println("ket = " + key + " value = " + map.get(key));        		
        	}
        	System.out.println();
        }
        DAppealListResponse response = new DAppealListResponse();
        List<DAppealItem> itemsList = new ArrayList<>();
        for(String broj : brojList) {
        	ZalbaNaOdluku zalba = repository.findAppealByIdMarshall(broj);
    		
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

	public DAppealListResponse searchByKeywords(KeywordSearch s) throws XMLDBException, JAXBException, SAXException {
		return repository.searchByKeywords(s);
	}
}
