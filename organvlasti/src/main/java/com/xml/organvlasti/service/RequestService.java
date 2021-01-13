package com.xml.organvlasti.service;

import java.io.IOException;
import java.io.StringWriter;

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

import com.xml.organvlasti.dto.RequestDTO;
import com.xml.organvlasti.parser.DOMParser;
import com.xml.organvlasti.parser.XSLTransformer;
import com.xml.organvlasti.rdf.FusekiWriter;
import com.xml.organvlasti.rdf.MetadataExtractor;
import com.xml.organvlasti.repository.RequestRepository;

@Service()
public class RequestService {

	private final String requestXSL = "src/main/resources/xsl/zahtev.xsl";

	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private RequestRepository repository;
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	public void save(RequestDTO dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto.getText());
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("za:zahtev");
		Element sp = (Element) nodeList.item(0);
		String id = sp.getAttribute("id");
		System.out.println("node id = " + id);
		Document prev = null;
		try {
			System.out.println("findRequestById call");
			prev = repository.findRequestById(id);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("request already exist");
			return;
		}
		System.out.println("found request = " + prev);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		repository.save(sw.toString(), id + ".xml");
		
		metadataExtractor.extractMetadata(sw.toString(), MetadataExtractor.REQUEST_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.REQUEST_RDF_FILEPATH, FusekiWriter.REQUEST_METADATA_GRAPH_URI);

	}
	
	public String getHTML(String broj) {
		Document xml = repository.findRequestById(broj);
		return xslTransformer.getHTMLfromXML(requestXSL, xml);
	}
	
}
