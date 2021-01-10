package com.xml.project.service;

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

import com.xml.project.dto.NoticeDTO;
import com.xml.project.dto.RequestDTO;
import com.xml.project.parser.DOMParser;
import com.xml.project.parser.XSLTransformer;
import com.xml.project.repository.RequestRepository;

@Service
public class NoticeService {

	private final String requestXSL = "src/main/resources/xsl/obavestenje.xsl";
	
	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private RequestRepository repository;
	
	public void save(NoticeDTO dto) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
//		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto.getText());
//		System.out.println("got document = " + document);

		NodeList br_predmeta = document.getElementsByTagName("ob:broj_predmeta");

		Element el = (Element) br_predmeta.item(0);

		String broj = el.getTextContent(); // broj_predmeta

		Document prev = null;
		try {
			System.out.println("findResponseByBroj call");
			prev = repository.findRequestById(broj);
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
	}
	
	public String getHTML(String broj) {
		System.out.println("USAO DJE TREBA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Document xml = repository.findRequestById(broj);
		return xslTransformer.getHTMLfromXML(requestXSL, xml);
	}
}
