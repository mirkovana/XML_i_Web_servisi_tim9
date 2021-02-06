package com.xml.project.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xml.project.rdf.MetadataExtractor;
import com.xml.project.rdf.FusekiWriter;
import com.xml.project.rdf.FusekiReader;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.xml.project.dto.ResponseDTO;
import com.xml.project.model.email.EmailModel;
import com.xml.project.model.keywordSearch.KeywordSearch;
import com.xml.project.model.resenje.Zalba;
import com.xml.project.model.responseList.ResponseList;
import com.xml.project.model.responseList.ResponseList.ResponseItem;
import com.xml.project.parser.DOMParser;
import com.xml.project.parser.XSLTransformer;
import com.xml.project.repository.DecisionAppealRepository;
import com.xml.project.repository.ResponseRepository;
import com.xml.project.repository.SilenceAppealRepository;

@Service()
public class ResponseService {

	private final String responseXSL = "src/main/resources/xsl/resenje.xsl";
	private static String schemaPath = "src/main/resources/documents/resenje.xsd";

	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private ResponseRepository repository;
	@Autowired
	private DecisionAppealService dAppealService;
	@Autowired
	private SilenceAppealService sAppealService;
	@Autowired
	private UserService userService;
	@Autowired
	private MetadataExtractor metadataExtractor;

	public void save(String dto, String tip) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		domParser.setSchema(schemaPath);
		Document document = domParser.getDocument(dto);
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("res:zalba");
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		//sp.setAttribute("about", "http://www.projekat.org/resenje/" + broj);
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
	
		if(tip.contentEquals("decision")) {
			dAppealService.updateStateResolved(broj);
		}else if(tip.contentEquals("silence")) {
			sAppealService.updateStateResolved(broj);			
		}
		
		sendEmail(broj, sp.getAttribute("status"), sp.getAttribute("username"), sp.getAttribute("poverenikUsername"));
	}
	
	public ResponseList searchByMetadata(Map<String, String> params) throws IOException {
        System.out.println("service executeQuerry!");
        ArrayList<Map<String, String>> result = FusekiReader.executeQuery(params, FusekiReader.RESPONSE_QUERY_FILEPATH);
        System.out.println("return result querry!");
        ArrayList<String> brojList = new ArrayList<>();
        for(Map<String, String> map : result) {
        	String zalba = map.get("zalba");
        	String[] split = zalba.split("\\/");
        	brojList.add(split[split.length-1]);
        	System.out.println("map = ");
        	for(String key : map.keySet()) {
            	System.out.println("ket = " + key + " value = " + map.get(key));        		
        	}
        	System.out.println();
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

	public String getHTML(String broj) {
		Document xml = repository.findResponseByBroj(broj);
		return xslTransformer.getHTMLfromXML(responseXSL, xml);
	}

	public ResponseList getAll() throws XMLDBException, JAXBException, SAXException {
		return repository.getAll();
	}
	
	public ResponseList getAllForUsername(String username) throws XMLDBException, JAXBException, SAXException {
		return repository.getAllForUsername(username);
	}
	
	public byte[] getPdfBytes(String broj) throws IOException {
		String html = getHTML(broj);
        /* Setup Source and target I/O streams */
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8070");
        /* Call convert method */
        HtmlConverter.convertToPdf(html, target, converterProperties);  
        /* extract output as bytes */
        byte[] bytes = target.toByteArray();

        return bytes;
	}
	
	private void sendEmail(String broj, String status, String to, String from) {
		try {
			System.out.println("to = " + to + " from = " + from);
			String toEmail = userService.getUserEmailByUsername(to);
			String fromEmail = userService.getUserEmailByUsername(from);
			byte[] pdfBytes = getPdfBytes(broj);
			
			System.out.println("sendemailnoticeservice");
			String fooResourceUrl = "http://localhost:5000/email";
			RestTemplate restTemplate = new RestTemplate();
			EmailModel email = new EmailModel(); 
			email.setFrom(fromEmail);
			email.setPdf(Base64.getEncoder().encodeToString(pdfBytes));
			email.setSubject("Resenje zalbe na organa vlasti.");
			email.setText("Vasa zalba na organ vlasti br." + broj + " je: " + status + "\n"
					+ "html: http://localhost:8070/api/response/html/" + broj + " \n"
					+ "pdf: http://localhost:8070/api/response/pdf/" + broj + " \n");
			email.setTo(toEmail);
			System.out.println("emailmodel = " + email);
			HttpEntity<EmailModel> request = new HttpEntity<>(email);
			restTemplate.postForObject(fooResourceUrl, request, EmailModel.class);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
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
