package com.xml.project.service;

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

import com.xml.project.dto.SilenceAppealDTO;
import com.xml.project.model.decisionAppeal.ZalbaNaOdluku;
import com.xml.project.model.keywordSearch.KeywordSearch;
import com.xml.project.model.silenceAppeal.Trazlozi.Razlog;
import com.xml.project.model.silenceAppeal.ZalbaCutanje;
import com.xml.project.model.silenceAppealResponse.SAppealListResponse;
import com.xml.project.model.silenceAppealResponse.SAppealListResponse.SAppealItem;
import com.xml.project.parser.DOMParser;
import com.xml.project.parser.XSLTransformer;
import com.xml.project.rdf.FusekiReader;
import com.xml.project.rdf.FusekiWriter;
import com.xml.project.rdf.MetadataExtractor;
import com.xml.project.repository.RequestRepository;
import com.xml.project.repository.SilenceAppealRepository;

@Service()
public class SilenceAppealService {

	private final String requestXSL = "src/main/resources/xsl/zalbazbogcutanja.xsl";
	private static String schemaPath = "src/main/resources/documents/zalbazbogcutanja.xsd";

	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private SilenceAppealRepository repository;
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private MetadataExtractor metadataExtractor;

	public void save(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		domParser.setSchema(schemaPath);
		Document document = domParser.getDocument(dto);
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("zc:zalba_cutanje");
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		
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
	
	public SAppealListResponse getAllForUsername(String username) throws XMLDBException, JAXBException, SAXException {
		return repository.getAllForUsername(username);
	}
	
	public void deleteAppeal(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		repository.deleteAppeal(broj);
		requestRepository.deleteRequest(broj);
	}
	
	public void updateState(String broj) throws TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, IOException {
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		Document document = repository.findSilenceAppealByBroj(broj);
		NodeList nodeList = document.getElementsByTagName("zc:zalba_cutanje");
		Element sp = (Element) nodeList.item(0);
		sp.setAttribute("status", "pending");
		String xmlString;
		
		xmlString = repository.getStringFromDocument(document);
		xmlString = xmlString.replace("sent", "pending");
		System.out.println("updatedstate = " + xmlString);
		repository.deleteAppeal(broj);
		repository.save(xmlString, broj);
	
		metadataExtractor.extractMetadata(xmlString, MetadataExtractor.SILENCE_APPEAL_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.SILENCE_APPEAL_RDF_FILEPATH, FusekiWriter.SILENCE_APPEAL_METADATA_GRAPH_URI);
	}
	
	public void updateStateResolved(String broj) throws TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, IOException {
		if (!broj.endsWith(".xml")) {
			broj = broj + ".xml";
		}
		Document document = repository.findSilenceAppealByBroj(broj);
		NodeList nodeList = document.getElementsByTagName("zc:zalba_cutanje");
		Element sp = (Element) nodeList.item(0);
		sp.setAttribute("status", "resolved");
		String xmlString;
		
		xmlString = repository.getStringFromDocument(document);
		xmlString = xmlString.replace("answered", "resolved");
		System.out.println("updatedstate = " + xmlString);
		repository.deleteAppeal(broj);
		repository.save(xmlString, broj);
	
		metadataExtractor.extractMetadata(xmlString, MetadataExtractor.SILENCE_APPEAL_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.SILENCE_APPEAL_RDF_FILEPATH, FusekiWriter.SILENCE_APPEAL_METADATA_GRAPH_URI);
	}
	
	public String getAppealXml(String broj) throws TransformerException {
		Document document = repository.findSilenceAppealByBroj(broj);
		String xml = repository.getStringFromDocument(document);
		return xml;
	}
	
	public String getHTML(String id) {
		Document xml = repository.findSilenceAppealByBroj(id);
		return xslTransformer.getHTMLfromXML(requestXSL, xml);
	}

	public SAppealListResponse searchByMetadata(Map<String, String> params) throws IOException {
		System.out.println("service executeQuerry!");
        ArrayList<Map<String, String>> result = FusekiReader.executeQuery(params, FusekiReader.SILENCE_APPEAL_QUERY_FILEPATH);
        System.out.println("return result querry!");
        ArrayList<String> brojList = new ArrayList<>();
        for(Map<String, String> map : result) {
        	String zalbaNaCutanje = map.get("zalba_cutanje");
        	String[] split = zalbaNaCutanje.split("\\/");
        	brojList.add(split[split.length-1]);
        	System.out.println("map = ");
        	for(String key : map.keySet()) {
            	System.out.println("ket = " + key + " value = " + map.get(key));        		
        	}
        	System.out.println();
        }
        SAppealListResponse response = new SAppealListResponse();
        List<SAppealItem> itemsList = new ArrayList<>();
        for(String broj : brojList) {
        	ZalbaCutanje zalba = repository.findAppealByIdMarshall(broj);
    		
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

	public SAppealListResponse searchByKeywords(KeywordSearch s) throws XMLDBException, JAXBException, SAXException {
		return repository.searchByKeywords(s);
	}
}
